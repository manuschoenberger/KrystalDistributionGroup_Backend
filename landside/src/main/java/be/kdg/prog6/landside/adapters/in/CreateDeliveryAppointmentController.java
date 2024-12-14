package be.kdg.prog6.landside.adapters.in;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.landside.ports.in.CreateDeliveryAppointmentCommand;
import be.kdg.prog6.landside.ports.in.CreatingDeliveryAppointmentUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class CreateDeliveryAppointmentController {

    private final CreatingDeliveryAppointmentUseCase creatingDeliveryAppointmentUseCase;

    public CreateDeliveryAppointmentController(CreatingDeliveryAppointmentUseCase creatingDeliveryAppointmentUseCase) {
        this.creatingDeliveryAppointmentUseCase = creatingDeliveryAppointmentUseCase;
    }

    @PostMapping("/delivery-appointments")
    public String createDeliveryAppointment(@RequestBody DeliveryAppointmentRequest request) {
        SellerUUID sellerUUID = new SellerUUID(request.sellerUUID());
        LicensePlate licensePlate = new LicensePlate(request.licensePlate());
        RawMaterialData rawMaterialData = RawMaterialData.valueOf(request.payload().toUpperCase());
        LocalDateTime start = request.arrivalWindowStart();

        return creatingDeliveryAppointmentUseCase.createDeliveryAppointment(
                new CreateDeliveryAppointmentCommand(sellerUUID, licensePlate, rawMaterialData, start)
        );
    }

    public record DeliveryAppointmentRequest(UUID sellerUUID, String licensePlate, String payload, LocalDateTime arrivalWindowStart) {
    }
}
