package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.LicensePlate;
import be.kdg.prog6.common.domain.RawMaterialData;

import java.time.LocalDateTime;

public record CreateDeliveryAppointmentCommand(SellerUUID sellerUUID, LicensePlate licensePlate, RawMaterialData rawMaterialData, LocalDateTime arrivalWindowStart) {
}
