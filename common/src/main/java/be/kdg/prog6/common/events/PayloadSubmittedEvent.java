package be.kdg.prog6.common.events;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;

public record PayloadSubmittedEvent(SellerUUID sellerUUID, RawMaterialData rawMaterialData, double amount) {
}
