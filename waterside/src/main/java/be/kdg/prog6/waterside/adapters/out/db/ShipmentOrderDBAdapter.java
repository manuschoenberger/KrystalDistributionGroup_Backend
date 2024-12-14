package be.kdg.prog6.waterside.adapters.out.db;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.waterside.domain.uuid.ShipmentOrderUUID;
import be.kdg.prog6.waterside.domain.ShipmentOrder;
import be.kdg.prog6.waterside.ports.out.ShipmentOrderLoadPort;
import be.kdg.prog6.waterside.ports.out.ShipmentOrderUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShipmentOrderDBAdapter implements ShipmentOrderLoadPort, ShipmentOrderUpdatePort {

    private final ShipmentOrderRepository shipmentOrderRepository;

    public ShipmentOrderDBAdapter(ShipmentOrderRepository shipmentOrderRepository) {
        this.shipmentOrderRepository = shipmentOrderRepository;
    }

    @Override
    public Optional<ShipmentOrder> loadShipmentOrderByReferenceUUID(ReferenceUUID referenceUUID) {
        Optional<ShipmentOrderJpaEntity> shipmentOrderJpaEntityOptional = shipmentOrderRepository.findByReferenceUUID(referenceUUID.uuid());

        if (shipmentOrderJpaEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        ShipmentOrderJpaEntity shipmentOrderJpaEntity = shipmentOrderJpaEntityOptional.get();
        ShipmentOrder shipmentOrder = new ShipmentOrder(new ShipmentOrderUUID(shipmentOrderJpaEntity.getShipmentOrderUUID()));
        shipmentOrder.setReferenceUUID(new ReferenceUUID(shipmentOrderJpaEntity.getReferenceUUID()));
        shipmentOrder.setSellerUUID(new SellerUUID(shipmentOrderJpaEntity.getSellerUUID()));
        shipmentOrder.setShipmentStatus(shipmentOrderJpaEntity.getShipmentStatus());
        shipmentOrder.setVesselNumber(shipmentOrderJpaEntity.getVesselNumber());
        shipmentOrder.setEstimatedArrivalTime(shipmentOrderJpaEntity.getEstimatedArrivalTime());
        shipmentOrder.setEstimatedDepartureTime(shipmentOrderJpaEntity.getEstimatedDepartureTime());
        shipmentOrder.setBunkeringOperationTime(shipmentOrderJpaEntity.getBunkeringOperationTime());
        shipmentOrder.setInspectionOperationTime(shipmentOrderJpaEntity.getInspectionOperationTime());
        shipmentOrder.setInspectionSignature(shipmentOrderJpaEntity.getInspectionSignature());

        return Optional.of(shipmentOrder);
    }

    @Override
    public void updateShipmentOrder(ShipmentOrder shipmentOrder) {
        ShipmentOrderJpaEntity shipmentOrderJpaEntity = new ShipmentOrderJpaEntity(shipmentOrder.getShipmentOrderUUID().uuid());
        shipmentOrderJpaEntity.setReferenceUUID(shipmentOrder.getReferenceUUID().uuid());
        shipmentOrderJpaEntity.setSellerUUID(shipmentOrder.getSellerUUID().uuid());
        shipmentOrderJpaEntity.setShipmentStatus(shipmentOrder.getShipmentStatus());
        shipmentOrderJpaEntity.setVesselNumber(shipmentOrder.getVesselNumber());
        shipmentOrderJpaEntity.setEstimatedArrivalTime(shipmentOrder.getEstimatedArrivalTime());
        shipmentOrderJpaEntity.setEstimatedDepartureTime(shipmentOrder.getEstimatedDepartureTime());
        shipmentOrderJpaEntity.setBunkeringOperationTime(shipmentOrder.getBunkeringOperation().bunkeringOperationTime());
        shipmentOrderJpaEntity.setInspectionOperationTime(shipmentOrder.getInspectionOperation().inspectionOperationTime());
        shipmentOrderJpaEntity.setInspectionSignature(shipmentOrder.getInspectionOperation().inspectionSignature());


        shipmentOrderRepository.save(shipmentOrderJpaEntity);
    }
}
