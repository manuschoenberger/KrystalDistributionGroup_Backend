package be.kdg.prog6.waterside.adapters.in;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.waterside.ports.in.MakeShipmentCommand;
import be.kdg.prog6.waterside.ports.in.MakingShipmentUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MakeShipmentController {
    private final MakingShipmentUseCase makingShipmentUseCase;

    public MakeShipmentController(MakingShipmentUseCase makingShipmentUseCase) {
        this.makingShipmentUseCase = makingShipmentUseCase;
    }

    @PostMapping("/shipment")
    public String makeShipment(@RequestBody ShipmentRequest request) {
        return makingShipmentUseCase.makeShipment(new MakeShipmentCommand(
                new SellerUUID(request.sellerUUID),
                new ReferenceUUID(request.referenceUUID()),
                request.vesselNumber())
        );
    }

    public record ShipmentRequest(UUID sellerUUID, UUID referenceUUID, String vesselNumber) {
    }
}
