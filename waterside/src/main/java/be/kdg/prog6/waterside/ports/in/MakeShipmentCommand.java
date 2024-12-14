package be.kdg.prog6.waterside.ports.in;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.domain.uuid.SellerUUID;

public record MakeShipmentCommand(SellerUUID sellerUUID, ReferenceUUID referenceUUID, String vesselNumber) {
}
