package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;

public interface PayloadSubmitPort {
    void payloadSubmitted(SellerUUID sellerUUID, RawMaterialData payloadData, double amount);
}
