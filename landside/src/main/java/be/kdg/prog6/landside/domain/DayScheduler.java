package be.kdg.prog6.landside.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.uuid.DaySchedulerUUID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class DayScheduler {
    private DaySchedulerUUID daySchedulerUUID;
    private LocalDate date;
    private List<DeliveryAppointmentHourTimeslot> deliveryAppointmentHourTimeslots;

    public DayScheduler(DaySchedulerUUID daySchedulerUUID, LocalDate date, List<DeliveryAppointmentHourTimeslot> deliveryAppointmentHourTimeslots) {
        this.daySchedulerUUID = daySchedulerUUID;
        this.date = date;
        this.deliveryAppointmentHourTimeslots = deliveryAppointmentHourTimeslots;
    }

    public DayScheduler(LocalDate date) {
        this(new DaySchedulerUUID(UUID.randomUUID()), date, new ArrayList<>());
    }

    public DayScheduler(DaySchedulerUUID daySchedulerUUID, LocalDate date) {
        this(daySchedulerUUID, date, new ArrayList<>());
    }

    public Optional<DeliveryAppointment> scheduleDeliveryAppointment(SellerUUID sellerUUID, LicensePlate licensePlate, RawMaterialData rawMaterialData, LocalDateTime deliveryAppointmentWindowStart) {
        LocalTime deliveryAppointmentHourTimeslotStart = deliveryAppointmentWindowStart.toLocalTime();
        DeliveryAppointmentHourTimeslot deliveryAppointmentHourTimeslot = getTimeslotByTime(deliveryAppointmentHourTimeslotStart);

        if (deliveryAppointmentHourTimeslot.isFull()) {
            DeliveryAppointment deliveryAppointment = new DeliveryAppointment(sellerUUID, licensePlate, rawMaterialData, deliveryAppointmentWindowStart);
            deliveryAppointmentHourTimeslot.addDeliveryAppointment(deliveryAppointment);
            return Optional.of(deliveryAppointment);
        }

        return Optional.empty();
    }

    public DeliveryAppointmentHourTimeslot getTimeslotByTime(LocalTime time) {
        return deliveryAppointmentHourTimeslots.stream()
                .filter(deliveryAppointmentHourTimeslot -> deliveryAppointmentHourTimeslot.getStartTime().equals(time))
                .findFirst()
                .orElseGet(() -> {
                    DeliveryAppointmentHourTimeslot newSlot = new DeliveryAppointmentHourTimeslot(time);
                    deliveryAppointmentHourTimeslots.add(newSlot);

                    return newSlot;
                });
    }

    public Optional<List<DeliveryAppointment>> getDeliveryAppointmentByLicensePlate(LicensePlate licensePlate) {
        for (DeliveryAppointmentHourTimeslot deliveryAppointmentHourTimeslot : deliveryAppointmentHourTimeslots) {

            Optional<List<DeliveryAppointment>> deliveryAppointments = deliveryAppointmentHourTimeslot.getDeliveryAppointmentByLicensePlate(licensePlate);

            if (deliveryAppointments.isPresent()) {
                return deliveryAppointments;
            } else {
                System.out.println("No appointments found");
            }
        }

        return Optional.empty();
    }

    public void setDeliveryAppointmentAsArrived(DeliveryAppointment deliveryAppointment) {
        deliveryAppointment.setAsArrived();
    }

    public List<DeliveryAppointment> fetchAllDeliveryAppointments() {
        return deliveryAppointmentHourTimeslots.stream()
                .flatMap(timeslot -> timeslot.getDeliveryAppointments().stream())
                .collect(Collectors.toList());
    }
}
