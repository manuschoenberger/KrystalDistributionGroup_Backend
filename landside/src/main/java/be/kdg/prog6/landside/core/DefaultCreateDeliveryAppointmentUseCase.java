package be.kdg.prog6.landside.core;

import be.kdg.prog6.landside.domain.DayScheduler;
import be.kdg.prog6.landside.domain.DeliveryAppointment;
import be.kdg.prog6.landside.domain.Warehouse;
import be.kdg.prog6.landside.ports.in.CreateDeliveryAppointmentCommand;
import be.kdg.prog6.landside.ports.in.CreatingDeliveryAppointmentUseCase;
import be.kdg.prog6.landside.ports.out.DaySchedulerLoadPort;
import be.kdg.prog6.landside.ports.out.DaySchedulerUpdatePort;
import be.kdg.prog6.landside.ports.out.WarehouseLoadPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DefaultCreateDeliveryAppointmentUseCase implements CreatingDeliveryAppointmentUseCase {

    private final DaySchedulerLoadPort daySchedulerLoadPort;
    private final DaySchedulerUpdatePort daySchedulerUpdatePort;
    private final WarehouseLoadPort warehouseLoadPort;

    public DefaultCreateDeliveryAppointmentUseCase(
            DaySchedulerLoadPort daySchedulerLoadPort,
            DaySchedulerUpdatePort daySchedulerUpdatePort,
            WarehouseLoadPort warehouseLoadPort
    ) {
        this.daySchedulerLoadPort = daySchedulerLoadPort;
        this.daySchedulerUpdatePort = daySchedulerUpdatePort;
        this.warehouseLoadPort = warehouseLoadPort;
    }

    @Override
    @Transactional
    public String createDeliveryAppointment(CreateDeliveryAppointmentCommand createDeliveryAppointmentCommand) {
        LocalDate date = createDeliveryAppointmentCommand.arrivalWindowStart().toLocalDate();
        DayScheduler dayScheduler = daySchedulerLoadPort
                .loadSchedulerByDate(date)
                .orElse(new DayScheduler(date));

        Optional<DeliveryAppointment> deliveryAppointment = dayScheduler.scheduleDeliveryAppointment(
                createDeliveryAppointmentCommand.sellerUUID(),
                createDeliveryAppointmentCommand.licensePlate(),
                createDeliveryAppointmentCommand.rawMaterialData(),
                createDeliveryAppointmentCommand.arrivalWindowStart()
        );

        if (deliveryAppointment.isEmpty()) {
            throw new IllegalStateException("Unable to schedule delivery appointment: timeslot is full.");
        }

        System.out.println("Warehouse UUID: " + deliveryAppointment.get().getSellerUUID()
                + " Payload UUID: " + deliveryAppointment.get().getPayloadData());

        Optional<Warehouse> warehouseOptional = warehouseLoadPort
                .loadWarehouseBySellerIdAndPayloadData(
                        deliveryAppointment.get().getSellerUUID(),
                        deliveryAppointment.get().getPayloadData()
                );

        if (warehouseOptional.isPresent()) {
            Warehouse warehouse = warehouseOptional.get();
            if (!warehouse.checkWarehouseCapacity()) {
                throw new IllegalStateException("Warehouse capacity exceeded.");
            }
        } else {
            throw new IllegalStateException("Warehouse not found for the given seller and payload IDs");
        }

        daySchedulerUpdatePort.updateDayScheduler(dayScheduler);

        return "Created delivery appointment for "
                + createDeliveryAppointmentCommand.licensePlate().licensePlate() + " at "
                + createDeliveryAppointmentCommand.arrivalWindowStart()
                + " with payload " + createDeliveryAppointmentCommand.rawMaterialData() + ".";
    }
}
