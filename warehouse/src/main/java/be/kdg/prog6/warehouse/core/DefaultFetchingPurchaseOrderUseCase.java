package be.kdg.prog6.warehouse.core;

import be.kdg.prog6.warehouse.domain.Party;
import be.kdg.prog6.warehouse.domain.PurchaseOrder;
import be.kdg.prog6.warehouse.domain.PurchaseOrderLine;
import be.kdg.prog6.warehouse.domain.RawMaterial;
import be.kdg.prog6.warehouse.ports.in.FetchingPurchaseOrdersUseCase;
import be.kdg.prog6.warehouse.ports.in.SendPurchaseOrderCommand;
import be.kdg.prog6.warehouse.ports.out.PurchaseOrderLoadPort;
import be.kdg.prog6.warehouse.ports.out.PurchaseOrderUpdatePort;
import be.kdg.prog6.warehouse.ports.out.RawMaterialLoadPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultFetchingPurchaseOrderUseCase implements FetchingPurchaseOrdersUseCase {
    private final PurchaseOrderUpdatePort purchaseOrderUpdatePort;
    private final RawMaterialLoadPort rawMaterialLoadPort;
    private final PurchaseOrderLoadPort purchaseOrderLoadPort;

    public DefaultFetchingPurchaseOrderUseCase(PurchaseOrderUpdatePort purchaseOrderUpdatePort, RawMaterialLoadPort rawMaterialLoadPort, PurchaseOrderLoadPort purchaseOrderLoadPort) {
        this.purchaseOrderUpdatePort = purchaseOrderUpdatePort;
        this.rawMaterialLoadPort = rawMaterialLoadPort;
        this.purchaseOrderLoadPort = purchaseOrderLoadPort;
    }

    @Override
    @Transactional
    public void getPurchaseOrder(SendPurchaseOrderCommand sendPurchaseOrderCommand) {
        PurchaseOrder purchaseOrder = new PurchaseOrder(sendPurchaseOrderCommand.referenceUUID());
        mapPurchaseOrder(sendPurchaseOrderCommand, purchaseOrder);

        List<RawMaterial> rawMaterials = new ArrayList<>();

        for (PurchaseOrderLine purchaseOrderLine : purchaseOrder.getPurchaseOrderLines()) {
            Optional<RawMaterial> rawMaterialOptional = rawMaterialLoadPort.loadRawMaterialByRawMaterialData(purchaseOrderLine.getMaterialType());

            RawMaterial rawMaterial = rawMaterialOptional.get();

            if (rawMaterialOptional.isPresent() && !rawMaterials.contains(rawMaterial)) {
                rawMaterials.add(rawMaterial);
            }
        }

        purchaseOrder.calculateTotalAmount(rawMaterials);

        purchaseOrderUpdatePort.updatePurchaseOrder(purchaseOrder);
    }

    @Override
    public PurchaseOrder getPurchaseOrderByUUID(UUID referenceUUID) {
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseOrderLoadPort.loadPurchaseOrderByReferenceUUID(referenceUUID);

        return optionalPurchaseOrder.orElseThrow();
    }

    private void mapPurchaseOrder(SendPurchaseOrderCommand sendPurchaseOrderCommand, PurchaseOrder purchaseOrder) {
        purchaseOrder.setPoNumber(sendPurchaseOrderCommand.poNumber());
        purchaseOrder.setReferenceUUID(sendPurchaseOrderCommand.referenceUUID());
        purchaseOrder.setCustomerParty(new Party(sendPurchaseOrderCommand.customerParty().getUuid(), sendPurchaseOrderCommand.customerParty().getName(), sendPurchaseOrderCommand.customerParty().getAddress()));
        purchaseOrder.setSellerParty(new Party(sendPurchaseOrderCommand.sellerParty().getUuid(), sendPurchaseOrderCommand.sellerParty().getName(), sendPurchaseOrderCommand.sellerParty().getAddress()));
        purchaseOrder.setVesselNumber(sendPurchaseOrderCommand.vesselNumber());
        purchaseOrder.setPurchaseOrderLines(sendPurchaseOrderCommand.purchasePurchaseOrderLines().stream().map(orderLineCommand -> {
            PurchaseOrderLine purchaseOrderLine = new PurchaseOrderLine();
            purchaseOrderLine.setLineNumber(orderLineCommand.getLineNumber());
            purchaseOrderLine.setMaterialType(orderLineCommand.getMaterialType());
            purchaseOrderLine.setDescription(orderLineCommand.getDescription());
            purchaseOrderLine.setQuantity(orderLineCommand.getQuantity());
            purchaseOrderLine.setUom(orderLineCommand.getUom());

            return purchaseOrderLine;
        }).toList());

        purchaseOrder.setArrived(false);
        purchaseOrder.setTotalAmount(0);
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrders() {
        Optional<List<PurchaseOrder>> optionalPurchaseOrders = purchaseOrderLoadPort.loadPurchaseOrders();

        return optionalPurchaseOrders.orElseGet(ArrayList::new);
    }
}
