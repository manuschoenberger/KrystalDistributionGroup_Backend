package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.landside.domain.DeliveryAppointment;
import be.kdg.prog6.landside.domain.uuid.DeliveryAppointmentUUID;

import java.util.Optional;

public interface DeliveryAppointmentLoadPort {
    Optional<DeliveryAppointment> loadDeliveryAppointmentByUUID(DeliveryAppointmentUUID deliveryAppointmentUUID);
}
