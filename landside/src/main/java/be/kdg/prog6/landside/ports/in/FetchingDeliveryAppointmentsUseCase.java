package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.landside.domain.DeliveryAppointment;

import java.util.List;

public interface FetchingDeliveryAppointmentsUseCase {
    List<DeliveryAppointment> fetchDeliveryAppointments();

    DeliveryAppointment fetchDeliveryAppointmentByUUID(FetchDeliveryAppointmentsCommand fetchDeliveryAppointmentsCommand);
}
