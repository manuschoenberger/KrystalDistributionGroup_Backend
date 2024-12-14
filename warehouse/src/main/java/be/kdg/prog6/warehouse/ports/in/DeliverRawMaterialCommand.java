package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;

public record DeliverRawMaterialCommand(double amount, SellerUUID sellerUUID, RawMaterialData rawMaterialData) {
}
