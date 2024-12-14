package be.kdg.prog6.landside.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryAppointmentRepository extends JpaRepository<DeliveryAppointmentJpaEntity, Long> {
    Optional<DeliveryAppointmentJpaEntity> findByDeliveryAppointmentUUID(UUID deliveryAppointmentUUID);
}
