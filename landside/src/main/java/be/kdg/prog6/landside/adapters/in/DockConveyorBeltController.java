package be.kdg.prog6.landside.adapters.in;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.ports.in.DockConveyorBeltCommand;
import be.kdg.prog6.landside.ports.in.DockingConveyorBeltUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class DockConveyorBeltController {

    private final DockingConveyorBeltUseCase dockingConveyorBeltUseCase;

    public DockConveyorBeltController(DockingConveyorBeltUseCase dockingConveyorBeltUseCase) {
        this.dockingConveyorBeltUseCase = dockingConveyorBeltUseCase;
    }

    @PostMapping("/conveyor-belt")
    public String dockConveyorBelt(@RequestBody DockConveyorBeltRequest request) {
        return dockingConveyorBeltUseCase.dockConveyorBelt(new DockConveyorBeltCommand(new SellerUUID(request.sellerUUID()), RawMaterialData.valueOf(request.payloadData().toUpperCase()), new LicensePlate(request.licensePlate())));
    }

    public record DockConveyorBeltRequest(UUID sellerUUID, String payloadData, String licensePlate) {
    }
}
