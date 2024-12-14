package be.kdg.prog6.landside.adapters.in;

import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.landside.ports.in.ScanningLicensePlateUseCase;
import be.kdg.prog6.landside.ports.in.ScanLicensePlateCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScanLicensePlateController {

    private final ScanningLicensePlateUseCase scanningLicensePlateUseCase;

    public ScanLicensePlateController(final ScanningLicensePlateUseCase scanningLicensePlateUseCase) {
        this.scanningLicensePlateUseCase = scanningLicensePlateUseCase;
    }

    @GetMapping("/gate/{licensePlate}")
    public String scanLicensePlate(@PathVariable String licensePlate) {
        return scanningLicensePlateUseCase.scanLicensePlate(
                new ScanLicensePlateCommand(new LicensePlate(licensePlate))
        );
    }
}
