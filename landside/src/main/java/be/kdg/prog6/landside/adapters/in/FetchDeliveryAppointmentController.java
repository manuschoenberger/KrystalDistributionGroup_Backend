package be.kdg.prog6.landside.adapters.in;

import be.kdg.prog6.landside.domain.DeliveryAppointment;
import be.kdg.prog6.landside.ports.in.FetchDeliveryAppointmentsCommand;
import be.kdg.prog6.landside.ports.in.FetchingDeliveryAppointmentsUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/delivery-appointment")
public class FetchDeliveryAppointmentController {

    private final FetchingDeliveryAppointmentsUseCase fetchingDeliveryAppointmentsUseCase;

    public FetchDeliveryAppointmentController(final FetchingDeliveryAppointmentsUseCase fetchingDeliveryAppointmentsUseCase) {
        this.fetchingDeliveryAppointmentsUseCase = fetchingDeliveryAppointmentsUseCase;
    }

    @GetMapping()
    public List<DeliveryAppointment> fetchDeliveryAppointment() {
        return fetchingDeliveryAppointmentsUseCase.fetchDeliveryAppointments();
    }

    @GetMapping("/{deliveryAppointmentUUID}")
    public DeliveryAppointment fetchDeliveryAppointmentByUUID(@PathVariable String deliveryAppointmentUUID) {
        return fetchingDeliveryAppointmentsUseCase.fetchDeliveryAppointmentByUUID(new FetchDeliveryAppointmentsCommand(UUID.fromString(deliveryAppointmentUUID)));
    }
}
