package be.kdg.prog6.landside.core;

import be.kdg.prog6.landside.domain.DayScheduler;
import be.kdg.prog6.landside.domain.DeliveryAppointment;
import be.kdg.prog6.landside.domain.uuid.DeliveryAppointmentUUID;
import be.kdg.prog6.landside.ports.in.FetchDeliveryAppointmentsCommand;
import be.kdg.prog6.landside.ports.in.FetchingDeliveryAppointmentsUseCase;
import be.kdg.prog6.landside.ports.out.DaySchedulerLoadPort;
import be.kdg.prog6.landside.ports.out.DeliveryAppointmentLoadPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultFetchingDeliveryAppointmentsUseCase implements FetchingDeliveryAppointmentsUseCase {
    private final DaySchedulerLoadPort daySchedulerLoadPort;
    private final DeliveryAppointmentLoadPort deliveryAppointmentLoadPort;

    public DefaultFetchingDeliveryAppointmentsUseCase(DaySchedulerLoadPort daySchedulerLoadPort, DeliveryAppointmentLoadPort deliveryAppointmentLoadPort) {
        this.daySchedulerLoadPort = daySchedulerLoadPort;
        this.deliveryAppointmentLoadPort = deliveryAppointmentLoadPort;
    }


    @Override
    public List<DeliveryAppointment> fetchDeliveryAppointments() {
        Optional<List<DayScheduler>> dayScheduler = daySchedulerLoadPort
                .loadAllDayScheduler();

        return dayScheduler.map(daySchedulers -> daySchedulers.stream()
                .map(DayScheduler::fetchAllDeliveryAppointments)
                .flatMap(List::stream)
                .toList()).orElseGet(List::of);
    }

    @Override
    public DeliveryAppointment fetchDeliveryAppointmentByUUID(FetchDeliveryAppointmentsCommand fetchDeliveryAppointmentsCommand) {
        Optional<DeliveryAppointment> deliveryAppointmentOptional = deliveryAppointmentLoadPort
                .loadDeliveryAppointmentByUUID(new DeliveryAppointmentUUID(fetchDeliveryAppointmentsCommand.uuid()));

        if (deliveryAppointmentOptional.isEmpty()) {
            throw new IllegalArgumentException("DeliveryAppointment with UUID " + fetchDeliveryAppointmentsCommand.uuid() + " not found");
        } else {
            return deliveryAppointmentOptional.get();
        }
    }
}
