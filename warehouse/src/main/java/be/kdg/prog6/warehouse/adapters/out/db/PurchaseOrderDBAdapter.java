package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.warehouse.domain.Party;
import be.kdg.prog6.warehouse.domain.PurchaseOrder;
import be.kdg.prog6.warehouse.domain.PurchaseOrderLine;
import be.kdg.prog6.warehouse.ports.out.PurchaseOrderLoadPort;
import be.kdg.prog6.warehouse.ports.out.PurchaseOrderUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PurchaseOrderDBAdapter implements PurchaseOrderUpdatePort, PurchaseOrderLoadPort {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderDBAdapter(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public Optional<PurchaseOrder> loadPurchaseOrderByReferenceUUID(UUID referenceUUID) {
        Optional<PurchaseOrderJpaEntity> purchaseOrderJpaEntityOptional = purchaseOrderRepository.findByReferenceUUID(referenceUUID);

        if (purchaseOrderJpaEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        PurchaseOrder purchaseOrder = mapToPurchaseOrder(purchaseOrderJpaEntityOptional.get());

        return Optional.of(purchaseOrder);
    }

    @Override
    public void updatePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrderJpaEntity purchaseOrderJpaEntity = new PurchaseOrderJpaEntity(purchaseOrder.getReferenceUUID().uuid());
        purchaseOrderJpaEntity.setPoNumber(purchaseOrder.getPoNumber());

        PurchaseOrderPartyJpaEntity purchaseOrderPartyJpaEntityCustomer = new PurchaseOrderPartyJpaEntity();
        purchaseOrderPartyJpaEntityCustomer.setPartyUUID(purchaseOrder.getCustomerParty().getUuid());
        purchaseOrderPartyJpaEntityCustomer.setName(purchaseOrder.getCustomerParty().getName());
        purchaseOrderPartyJpaEntityCustomer.setAddress(purchaseOrder.getCustomerParty().getAddress());
        purchaseOrderJpaEntity.setCustomerParty(purchaseOrderPartyJpaEntityCustomer);

        PurchaseOrderPartyJpaEntity purchaseOrderPartyJpaEntitySeller = new PurchaseOrderPartyJpaEntity();
        purchaseOrderPartyJpaEntitySeller.setPartyUUID(purchaseOrder.getSellerParty().getUuid());
        purchaseOrderPartyJpaEntitySeller.setName(purchaseOrder.getSellerParty().getName());
        purchaseOrderPartyJpaEntitySeller.setAddress(purchaseOrder.getSellerParty().getAddress());
        purchaseOrderJpaEntity.setSellerParty(purchaseOrderPartyJpaEntitySeller);

        purchaseOrderJpaEntity.setVesselNumber(purchaseOrder.getVesselNumber());
        List<PurchaseOrderLineJpaEntity> orderLineJpaEntities = new ArrayList<>();

        for (PurchaseOrderLine purchaseOrderLine : purchaseOrder.getPurchaseOrderLines()) {
            PurchaseOrderLineJpaEntity orderLineJpaEntity = new PurchaseOrderLineJpaEntity();
            orderLineJpaEntity.setLineNumber(purchaseOrderLine.getLineNumber());
            orderLineJpaEntity.setMaterialType(purchaseOrderLine.getMaterialType());
            orderLineJpaEntity.setDescription(purchaseOrderLine.getDescription());
            orderLineJpaEntity.setQuantity(purchaseOrderLine.getQuantity());
            orderLineJpaEntity.setUom(purchaseOrderLine.getUom());
            orderLineJpaEntities.add(orderLineJpaEntity);
        }

        purchaseOrderJpaEntity.setOrderLines(orderLineJpaEntities);
        purchaseOrderJpaEntity.setArrived(purchaseOrder.isArrived());
        purchaseOrderJpaEntity.setTotalAmount(purchaseOrder.getTotalAmount());

        purchaseOrderRepository.save(purchaseOrderJpaEntity);
    }

    private PurchaseOrder mapToPurchaseOrder(PurchaseOrderJpaEntity purchaseOrderJpaEntity) {
        PurchaseOrder purchaseOrder = new PurchaseOrder(new ReferenceUUID(purchaseOrderJpaEntity.getReferenceUUID()));
        purchaseOrder.setPoNumber(purchaseOrderJpaEntity.getPoNumber());
        purchaseOrder.setVesselNumber(purchaseOrderJpaEntity.getVesselNumber());

        Party customer = mapToParty(purchaseOrderJpaEntity.getCustomerParty());
        purchaseOrder.setCustomerParty(customer);

        Party seller = mapToParty(purchaseOrderJpaEntity.getSellerParty());
        purchaseOrder.setSellerParty(seller);

        List<PurchaseOrderLine> purchaseOrderLines = getPurchaseOrderLines(purchaseOrderJpaEntity);
        purchaseOrder.setPurchaseOrderLines(purchaseOrderLines);
        purchaseOrder.setArrived(purchaseOrderJpaEntity.isArrived());
        purchaseOrder.setTotalAmount(purchaseOrderJpaEntity.getTotalAmount());

        return purchaseOrder;
    }

    private Party mapToParty(PurchaseOrderPartyJpaEntity purchaseOrderPartyJpaEntity) {
        return new Party(purchaseOrderPartyJpaEntity.getPartyUUID(), purchaseOrderPartyJpaEntity.getName(), purchaseOrderPartyJpaEntity.getAddress()
        );
    }

    private List<PurchaseOrderLine> getPurchaseOrderLines(PurchaseOrderJpaEntity purchaseOrderJpaEntity) {
        List<PurchaseOrderLineJpaEntity> purchaseOrderLineJpaEntities = purchaseOrderJpaEntity.getOrderLines();
        List<PurchaseOrderLine> purchaseOrderLines = new ArrayList<>();

        for (PurchaseOrderLineJpaEntity purchaseOrderLineJpaEntity : purchaseOrderLineJpaEntities) {
            PurchaseOrderLine purchaseOrderLine = new PurchaseOrderLine(
                    purchaseOrderLineJpaEntity.getLineNumber(),
                    purchaseOrderLineJpaEntity.getMaterialType(),
                    purchaseOrderLineJpaEntity.getDescription(),
                    purchaseOrderLineJpaEntity.getQuantity(),
                    purchaseOrderLineJpaEntity.getUom()
            );

            purchaseOrderLines.add(purchaseOrderLine);
        }

        return purchaseOrderLines;
    }

    @Override
    public Optional<List<PurchaseOrder>> loadPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll().stream()
                .map(this::mapToPurchaseOrder)
                .toList();

        return Optional.of(purchaseOrders);
    }
}
