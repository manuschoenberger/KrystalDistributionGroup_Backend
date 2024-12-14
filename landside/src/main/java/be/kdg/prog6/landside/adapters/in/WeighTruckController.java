package be.kdg.prog6.landside.adapters.in;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.domain.TruckStatus;
import be.kdg.prog6.landside.ports.in.WeighTruckCommand;
import be.kdg.prog6.landside.ports.in.WeighingTruckUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeighTruckController {

    private final WeighingTruckUseCase weighingTruckUseCase;

    public WeighTruckController(WeighingTruckUseCase weighingTruckUseCase) {
        this.weighingTruckUseCase = weighingTruckUseCase;
    }

    @PostMapping("/weighing-bridge")
    public String weighTruck(@RequestBody WeighTruckRequest request) {
        return weighingTruckUseCase.weighTruck(
                new WeighTruckCommand(
                        new LicensePlate(request.licensePlate()),
                        TruckStatus.valueOf(request.positionStatus().toUpperCase()),
                        request.weight()
                )
        );
    }

    public record WeighTruckRequest(String licensePlate, String positionStatus, double weight) {
    }
}
