package be.kdg.prog6.landside.core;

import be.kdg.prog6.landside.domain.*;
import be.kdg.prog6.landside.ports.in.ScanningLicensePlateUseCase;
import be.kdg.prog6.landside.ports.in.ScanLicensePlateCommand;
import be.kdg.prog6.landside.ports.out.DaySchedulerLoadPort;
import be.kdg.prog6.landside.ports.out.DaySchedulerUpdatePort;
import be.kdg.prog6.landside.ports.out.GateControlPort;
import be.kdg.prog6.landside.ports.out.TruckUpdatePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class DefaultLicensePlateScanUseCase implements ScanningLicensePlateUseCase {

    private final GateControlPort gateControlPort;
    private final DaySchedulerLoadPort daySchedulerLoadPort;
    private final DaySchedulerUpdatePort daySchedulerUpdatePort;
    private final TruckUpdatePort truckUpdatePort;
    private final Random random;

    public DefaultLicensePlateScanUseCase(GateControlPort gateControlPort, DaySchedulerLoadPort daySchedulerLoadPort, DaySchedulerUpdatePort daySchedulerUpdatePort, TruckUpdatePort truckUpdatePort) {
        this.gateControlPort = gateControlPort;
        this.daySchedulerLoadPort = daySchedulerLoadPort;
        this.daySchedulerUpdatePort = daySchedulerUpdatePort;
        this.truckUpdatePort = truckUpdatePort;
        this.random = new Random();
    }

    @Override
    @Transactional
    public String scanLicensePlate(ScanLicensePlateCommand scanLicensePlateCommand) {
        LicensePlate licensePlate = scanLicensePlateCommand.licensePlate();

        DayScheduler dayScheduler = daySchedulerLoadPort.loadSchedulerByDate(LocalDate.now())
                .orElse(new DayScheduler(LocalDate.now()));
        List<DeliveryAppointment> deliveryAppointments = dayScheduler.getDeliveryAppointmentByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalStateException("No delivery appointments found for license plate: " + licensePlate.licensePlate()));

        for (DeliveryAppointment deliveryAppointment : deliveryAppointments) {
            if (deliveryAppointment.isTruckOnTime(LocalDateTime.now()) && !deliveryAppointment.isArrived()) {
                dayScheduler.setDeliveryAppointmentAsArrived(deliveryAppointment);
                daySchedulerUpdatePort.updateDayScheduler(dayScheduler);

                Truck truck = new Truck(licensePlate, TruckStatus.WEIGHING_BRIDGE_ENTRY);
                truckUpdatePort.updateTruck(truck);

                int weighingBridgeNumber = random.nextInt(5) + 1;

                return "Gate opened! " + deliveryAppointment.getDeliveryAppointmentUUID().uuid() + ". Go to weighing bridge with number: " + (weighingBridgeNumber);
            }
        }

        gateControlPort.controlGate();

        return "Gate closed! No delivery appointments found for license plate: " + licensePlate.licensePlate();
    }
}
