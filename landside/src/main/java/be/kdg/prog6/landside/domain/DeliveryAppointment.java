package be.kdg.prog6.landside.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.uuid.DeliveryAppointmentUUID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class DeliveryAppointment {
    private final DeliveryAppointmentUUID deliveryAppointmentUUID;
    private SellerUUID sellerUUID;
    private LicensePlate licensePlate;
    private RawMaterialData payloadData;
    private LocalDateTime arrivalWindowStart;
    private DeliveryAppointmentStatus deliveryAppointmentStatus;

    public DeliveryAppointment(DeliveryAppointmentUUID deliveryAppointmentUUID, SellerUUID sellerUUID, LicensePlate licensePlate, RawMaterialData payloadData, LocalDateTime arrivalWindowStart, DeliveryAppointmentStatus deliveryAppointmentStatus) {
        this.deliveryAppointmentUUID = deliveryAppointmentUUID;
        this.sellerUUID = sellerUUID;
        this.licensePlate = licensePlate;
        this.payloadData = payloadData;
        this.arrivalWindowStart = arrivalWindowStart;
        this.deliveryAppointmentStatus = deliveryAppointmentStatus;
    }

    public DeliveryAppointment(SellerUUID sellerUUID, LicensePlate licensePlate, RawMaterialData payloadData, LocalDateTime arrivalWindowStart) {
        this.deliveryAppointmentUUID = new DeliveryAppointmentUUID(UUID.randomUUID());
        this.sellerUUID = sellerUUID;
        this.licensePlate = licensePlate;
        this.payloadData = payloadData;
        this.arrivalWindowStart = arrivalWindowStart;
        this.deliveryAppointmentStatus = DeliveryAppointmentStatus.PLANNED;
    }

    public DeliveryAppointment(DeliveryAppointmentUUID deliveryAppointmentUUID) {
        this.deliveryAppointmentUUID = deliveryAppointmentUUID;
    }

    public boolean isTruckOnTime(LocalDateTime time) {
        return !time.isBefore(arrivalWindowStart) && !time.isAfter(arrivalWindowStart.plusHours(1));
    }

    public boolean isArrived() {
        return this.deliveryAppointmentStatus.equals(DeliveryAppointmentStatus.ARRIVED);
    }

    public void setAsArrived() {
        this.deliveryAppointmentStatus = DeliveryAppointmentStatus.ARRIVED;
    }
}
