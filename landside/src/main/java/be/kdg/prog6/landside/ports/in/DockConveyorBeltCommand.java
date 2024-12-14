package be.kdg.prog6.landside.ports.in;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.LicensePlate;

public record DockConveyorBeltCommand(SellerUUID sellerUUID, RawMaterialData payloadData, LicensePlate licensePlate) {
}
