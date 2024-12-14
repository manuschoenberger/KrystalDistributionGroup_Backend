package be.kdg.prog6.landside.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class DeliveryAppointmentHourTimeslot {
    private static final int DELIVERY_APPOINTMENTS_HOUR_THRESHOLD = 40;

    private DeliveryAppointmentHourTimeslotUUID deliveryAppointmentHourTimeslotUUID;
    private LocalTime startTime;
    private List<DeliveryAppointment> deliveryAppointments;

    public record DeliveryAppointmentHourTimeslotUUID(UUID uuid) { }

    public DeliveryAppointmentHourTimeslot(DeliveryAppointmentHourTimeslotUUID deliveryAppointmentHourTimeslotUUID, LocalTime startTime, List<DeliveryAppointment> deliveryAppointments) {
        this.deliveryAppointmentHourTimeslotUUID = deliveryAppointmentHourTimeslotUUID;
        this.startTime = startTime;
        this.deliveryAppointments = deliveryAppointments;
    }

    public DeliveryAppointmentHourTimeslot(LocalTime startTime) {
        this(new DeliveryAppointmentHourTimeslotUUID(UUID.randomUUID()), startTime, new ArrayList<>());
    }

    public DeliveryAppointmentHourTimeslot(DeliveryAppointmentHourTimeslotUUID deliveryAppointmentHourTimeslotUUID, LocalTime startTime) {
        this(deliveryAppointmentHourTimeslotUUID, startTime, new ArrayList<>());
    }

    public boolean isFull() {
        return deliveryAppointments.size() < DELIVERY_APPOINTMENTS_HOUR_THRESHOLD;
    }

    public void addDeliveryAppointment(DeliveryAppointment appointment) {
        if (isFull()) {
            deliveryAppointments.add(appointment);
        }
    }

    public Optional<List<DeliveryAppointment>> getDeliveryAppointmentByLicensePlate(LicensePlate licensePlate) {
        return deliveryAppointments.stream()
                .filter(deliveryAppointment -> deliveryAppointment.getLicensePlate().equals(licensePlate))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of));
    }
}