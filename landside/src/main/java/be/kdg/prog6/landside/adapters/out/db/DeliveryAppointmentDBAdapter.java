package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.DeliveryAppointment;
import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.uuid.DeliveryAppointmentUUID;
import be.kdg.prog6.landside.ports.out.DeliveryAppointmentLoadPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DeliveryAppointmentDBAdapter implements DeliveryAppointmentLoadPort {

    private final DeliveryAppointmentRepository deliveryAppointmentRepository;

    public DeliveryAppointmentDBAdapter(DeliveryAppointmentRepository deliveryAppointmentRepository) {
        this.deliveryAppointmentRepository = deliveryAppointmentRepository;
    }

    @Override
    public Optional<DeliveryAppointment> loadDeliveryAppointmentByUUID(DeliveryAppointmentUUID deliveryAppointmentUUID) {
        Optional<DeliveryAppointmentJpaEntity> deliveryAppointmentJpaEntityOptional = deliveryAppointmentRepository.findByDeliveryAppointmentUUID(deliveryAppointmentUUID.uuid());

        if (deliveryAppointmentJpaEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        DeliveryAppointmentJpaEntity deliveryAppointmentJpaEntity = deliveryAppointmentJpaEntityOptional.get();
        DeliveryAppointment deliveryAppointment = getDeliveryAppointment(deliveryAppointmentJpaEntity);

        return Optional.of(deliveryAppointment);
    }

    private static DeliveryAppointment getDeliveryAppointment(DeliveryAppointmentJpaEntity deliveryAppointmentJpaEntity) {
        DeliveryAppointment deliveryAppointment = new DeliveryAppointment(new DeliveryAppointmentUUID(deliveryAppointmentJpaEntity.getDeliveryAppointmentUUID()));
        deliveryAppointment.setSellerUUID(new SellerUUID(deliveryAppointmentJpaEntity.getSellerUUID()));
        deliveryAppointment.setLicensePlate(new LicensePlate(deliveryAppointmentJpaEntity.getLicensePlate()));
        deliveryAppointment.setArrivalWindowStart(deliveryAppointmentJpaEntity.getDeliveryAppointmentWindowStart());
        deliveryAppointment.setPayloadData(deliveryAppointmentJpaEntity.getPayloadData());
        deliveryAppointment.setDeliveryAppointmentStatus(deliveryAppointmentJpaEntity.getDeliveryAppointmentStatus());
        return deliveryAppointment;
    }

}
