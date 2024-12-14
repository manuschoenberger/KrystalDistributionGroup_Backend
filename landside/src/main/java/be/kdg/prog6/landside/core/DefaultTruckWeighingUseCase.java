package be.kdg.prog6.landside.core;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.*;
import be.kdg.prog6.landside.ports.in.WeighTruckCommand;
import be.kdg.prog6.landside.ports.in.WeighingTruckUseCase;
import be.kdg.prog6.landside.ports.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultTruckWeighingUseCase implements WeighingTruckUseCase {

    private final WeighingBridgeTicketLoadPort weighingBridgeTicketLoadPort;
    private final WeighingBridgeTicketUpdatePort weighingBridgeTicketUpdatePort;
    private final DaySchedulerLoadPort dayScheduleLoadPort;
    private final DaySchedulerUpdatePort daySchedulerUpdatePort;
    private final PayloadSubmitPort payloadSubmitPort;
    private final TruckLoadPort truckLoadPort;
    private final TruckUpdatePort truckUpdatePort;

    public DefaultTruckWeighingUseCase(WeighingBridgeTicketLoadPort weighingBridgeTicketLoadPort,
                                       WeighingBridgeTicketUpdatePort weighingBridgeTicketUpdatePort,
                                       DaySchedulerLoadPort dayScheduleLoadPort,
                                       DaySchedulerUpdatePort daySchedulerUpdatePort,
                                       PayloadSubmitPort payloadSubmitPort,
                                       TruckLoadPort truckLoadPort,
                                       TruckUpdatePort truckUpdatePort)
    {
        this.weighingBridgeTicketLoadPort = weighingBridgeTicketLoadPort;
        this.weighingBridgeTicketUpdatePort = weighingBridgeTicketUpdatePort;
        this.dayScheduleLoadPort = dayScheduleLoadPort;
        this.daySchedulerUpdatePort = daySchedulerUpdatePort;
        this.payloadSubmitPort = payloadSubmitPort;
        this.truckLoadPort = truckLoadPort;
        this.truckUpdatePort = truckUpdatePort;
    }

    @Override
    @Transactional
    public String weighTruck(WeighTruckCommand weighTruckCommand) {
        WeighingBridgeTicket weighingBridgeTicket = null;

        String returnString = "";

        if (weighTruckCommand.truckStatus().equals(TruckStatus.WEIGHING_BRIDGE_ENTRY)) {
            weighingBridgeTicket = new WeighingBridgeTicket(
                    weighTruckCommand.licensePlate(),
                    weighTruckCommand.weight(),
                    LocalDateTime.now()
            );

            updateTruckStatus(weighTruckCommand.licensePlate(), TruckStatus.CONVEYOR_BELT_LOADING);

            returnString = "Weighing bridge ticket created: " + weighingBridgeTicket.getWeighingBridgeTicketUUID().uuid() +
                    " for license plate: " + weighingBridgeTicket.getLicensePlate().licensePlate();
        } else {
            weighingBridgeTicket = getWeighingBridgeTicket(weighTruckCommand.licensePlate());
            compileWeighingBridgeTicket(weighingBridgeTicket, weighTruckCommand.weight());

            updateTruckStatus(weighTruckCommand.licensePlate(), TruckStatus.NOT_ON_SITE);

            DayScheduler dayScheduler = dayScheduleLoadPort.loadSchedulerByDate(LocalDate.now())
                    .orElse(new DayScheduler(LocalDate.now()));

            DeliveryAppointment deliveryAppointment = getDeliveryAppointment(weighingBridgeTicket, dayScheduler);
            SellerUUID sellerUUID = deliveryAppointment.getSellerUUID();
            RawMaterialData payloadData = deliveryAppointment.getPayloadData();

            payloadSubmitPort.payloadSubmitted(sellerUUID, payloadData, weighingBridgeTicket.getNetWeight());
            daySchedulerUpdatePort.updateDayScheduler(dayScheduler);

            returnString = "Weighing bridge ticket updated: " + weighingBridgeTicket.getWeighingBridgeTicketUUID().uuid() +
                    " for license plate: " + weighingBridgeTicket.getLicensePlate().licensePlate();
        }

        weighingBridgeTicketUpdatePort.updateWeighingBridgeTicket(weighingBridgeTicket);
        return returnString;
    }

    private void compileWeighingBridgeTicket(WeighingBridgeTicket weighingBridgeTicket, double weight) {
        weighingBridgeTicket.setTareWeight(weight);
        weighingBridgeTicket.calculateNetWeight();
        weighingBridgeTicket.setExitTimestamp(LocalDateTime.now());
    }

    private WeighingBridgeTicket getWeighingBridgeTicket(LicensePlate licensePlate) {
        return weighingBridgeTicketLoadPort.loadWeighingBridgeTicketByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No weighing bridge ticket found for the given license plate: " + licensePlate.licensePlate()
                ));
    }

    private DeliveryAppointment getDeliveryAppointment(WeighingBridgeTicket weighingBridgeTicket, DayScheduler dayScheduler) {
        List<DeliveryAppointment> deliveryAppointments =
                dayScheduler.getDeliveryAppointmentByLicensePlate(weighingBridgeTicket.getLicensePlate())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No delivery appointments found for the given license plate: " + weighingBridgeTicket.getLicensePlate().licensePlate()
                        ));

        for (DeliveryAppointment deliveryAppointment : deliveryAppointments) {
            if (deliveryAppointment.getArrivalWindowStart().isBefore(weighingBridgeTicket.getArrivalTimestamp()) &&
                    deliveryAppointment.getArrivalWindowStart().plusHours(1)
                            .isAfter(weighingBridgeTicket.getArrivalTimestamp())) {
                return deliveryAppointment;
            }
        }

        throw new IllegalArgumentException(
                "No valid delivery appointment found within the arrival window for the given license plate: " + weighingBridgeTicket.getLicensePlate().licensePlate()
        );
    }

    private void updateTruckStatus(LicensePlate licensePlate, TruckStatus truckStatus) {
        Truck truck = truckLoadPort.loadTruckByLicensePlate(licensePlate)
                .orElseThrow(() -> new IllegalArgumentException("No truck found for the given license plate: " + licensePlate.licensePlate()));
        truck.setTruckStatus(truckStatus);

        truckUpdatePort.updateTruck(truck);
    }
}
