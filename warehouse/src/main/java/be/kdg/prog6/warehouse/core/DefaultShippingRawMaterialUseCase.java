package be.kdg.prog6.warehouse.core;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;
import be.kdg.prog6.warehouse.domain.*;
import be.kdg.prog6.warehouse.ports.in.ShippingRawMaterialUseCase;
import be.kdg.prog6.warehouse.ports.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultShippingRawMaterialUseCase implements ShippingRawMaterialUseCase {
    private final WarehouseLoadPort warehouseLoadPort;
    private final WarehouseActivityLoadPort warehouseActivityLoadPort;
    private final List<WarehouseActivityDeliveryCreatePort> warehouseActivityDeliveryCreatePorts;
    private final WarehouseActivityShipmentCreatePort warehouseActivityShipmentCreatePort;
    private final RawMaterialLoadPort rawMaterialLoadPort;
    private final PurchaseOrderLoadPort purchaseOrderLoadPort;
    private final PurchaseOrderUpdatePort purchaseOrderUpdatePort;
    private final ShipmentReadyPort shipmentReadyPort;

    public DefaultShippingRawMaterialUseCase(WarehouseLoadPort warehouseLoadPort, WarehouseActivityLoadPort warehouseActivityLoadPort, List<WarehouseActivityDeliveryCreatePort> warehouseActivityDeliveryCreatePorts, WarehouseActivityShipmentCreatePort warehouseActivityShipmentCreatePort, RawMaterialLoadPort rawMaterialLoadPort, PurchaseOrderLoadPort purchaseOrderLoadPort, PurchaseOrderUpdatePort purchaseOrderUpdatePort, ShipmentReadyPort shipmentReadyPort) {
        this.warehouseLoadPort = warehouseLoadPort;
        this.warehouseActivityLoadPort = warehouseActivityLoadPort;
        this.warehouseActivityDeliveryCreatePorts = warehouseActivityDeliveryCreatePorts;
        this.warehouseActivityShipmentCreatePort = warehouseActivityShipmentCreatePort;
        this.rawMaterialLoadPort = rawMaterialLoadPort;
        this.purchaseOrderLoadPort = purchaseOrderLoadPort;
        this.purchaseOrderUpdatePort = purchaseOrderUpdatePort;
        this.shipmentReadyPort = shipmentReadyPort;
    }

    @Override
    @Transactional
    public void shipRawMaterial(ReferenceUUID referenceUUID) {
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderLoadPort.loadPurchaseOrderByReferenceUUID(referenceUUID.uuid());

        if (purchaseOrderOptional.isEmpty()) {
            throw new IllegalArgumentException("Purchase order not found");
        }

        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();

        for (PurchaseOrderLine purchaseOrderLine : purchaseOrder.getPurchaseOrderLines()) {

            Optional<RawMaterial> rawMaterialOptional = rawMaterialLoadPort.loadRawMaterialByRawMaterialData(purchaseOrderLine.getMaterialType());

            if (rawMaterialOptional.isEmpty()) {
                throw new IllegalArgumentException("Raw material not found");
            }

            RawMaterial rawMaterial = rawMaterialOptional.get();
            System.out.println("Searching for warehouse for seller: " + purchaseOrder.getSellerParty().getUuid() + " and raw material: " + rawMaterial.rawMaterialData());
            Optional<Warehouse> warehouseOptional = warehouseLoadPort.loadWarehouseBySellerAndRawMaterialData(new SellerUUID(purchaseOrder.getSellerParty().getUuid()), rawMaterial.rawMaterialData());

            if (warehouseOptional.isEmpty()) {
                throw new IllegalArgumentException("Warehouse not found");
            };

            Warehouse warehouse = warehouseOptional.get();
            WarehouseActivity warehouseActivity = warehouse.shipRawMaterial(purchaseOrderLine.getQuantity(), rawMaterial.rawMaterialData());

            warehouseActivityDeliveryCreatePorts.forEach(warehouseActivityUpdatePort ->
                    warehouseActivityUpdatePort.createWarehouseActivity(
                            UUID.randomUUID(),
                            warehouse.getWarehouseUUID(),
                            warehouse.getWarehouseNumber(),
                            warehouse.getSellerUUID(),
                            warehouse.getRawMaterialData(),
                            warehouseActivity
                    )
            );
        }

        double totalMaterialCostsForShipment = computeTotalMaterialCosts(purchaseOrder.getPurchaseOrderLines());
        purchaseOrder.setArrived(true);
        purchaseOrderUpdatePort.updatePurchaseOrder(purchaseOrder);
        shipmentReadyPort.shipmentReady(referenceUUID, totalMaterialCostsForShipment);
    }

    private double computeTotalMaterialCosts(List<PurchaseOrderLine> purchaseOrderLines) {
        double totalMaterialCosts = 0;
        List<WarehouseActivity> warehouseActivities = loadDeliveryActivities();

        for (PurchaseOrderLine purchaseOrderLine : purchaseOrderLines) {
            double remainingAmount = purchaseOrderLine.getQuantity();
            Optional<RawMaterial> rawMaterialOptional = rawMaterialLoadPort.loadRawMaterialByRawMaterialData(purchaseOrderLine.getMaterialType());

            if (rawMaterialOptional.isEmpty()) {
                throw new IllegalArgumentException("Raw material not found");
            }

            RawMaterial rawMaterial = rawMaterialOptional.get();

            totalMaterialCosts += processShipment(purchaseOrderLine, warehouseActivities, rawMaterial, remainingAmount);
        }

        return totalMaterialCosts;
    }

    private double processShipment(PurchaseOrderLine purchaseOrderLine, List<WarehouseActivity> warehouseActivities, RawMaterial rawMaterial, double remainingAmount) {
        double totalShipmentCost = 0;

        List<WarehouseActivity> deliveryActivitiesForRawMaterial = warehouseActivities.stream()
                .filter(activity -> activity.getRawMaterialData() == rawMaterial.rawMaterialData())
                .toList();

        for (WarehouseActivity activity : deliveryActivitiesForRawMaterial) {
            if (remainingAmount == 0) break;
            if (activity.getAmountShipped() >= activity.getAmount()) continue;

            double availableAmount = activity.getAmount() - activity.getAmountShipped();
            double shippedAmount = Math.min(remainingAmount, availableAmount);

            updateTonsShipped(activity, shippedAmount);
            totalShipmentCost += computeMaterialCosts(rawMaterial, shippedAmount);

            remainingAmount -= shippedAmount;
        }

        if (remainingAmount > 0) {
            throw new IllegalStateException("Insufficient warehouse capacity to ship total amount for the purchase order line: " + purchaseOrderLine);
        }

        return totalShipmentCost;
    }


    private void updateTonsShipped(WarehouseActivity activity, double amountShipped) {
        activity.setAmountShipped(activity.getAmountShipped() + amountShipped);

        warehouseActivityShipmentCreatePort.createWarehouseActivity(activity.getWarehouseActivityUUID().uuid(), amountShipped);
    }

    private List<WarehouseActivity> loadDeliveryActivities() {
        return warehouseActivityLoadPort.loadAllWarehouseActivities()
                .orElseThrow(() -> new IllegalArgumentException("No warehouse activities found"))
                .stream()
                .filter(activity -> activity.getWarehouseActivityData() == WarehouseActivityData.DELIVERY)
                .sorted(Comparator.comparing(WarehouseActivity::getTime))
                .collect(Collectors.toList());
    }

    private double computeMaterialCosts(RawMaterial rawMaterial, double amount) {
        return amount * rawMaterial.pricePerTon();
    }
}
