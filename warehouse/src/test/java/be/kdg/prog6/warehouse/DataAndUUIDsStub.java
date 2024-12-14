package be.kdg.prog6.warehouse;

import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;


import java.util.UUID;

public class DataAndUUIDsStub {

    public static final WarehouseUUID WAREHOUSE_UUID = new WarehouseUUID(
            UUID.fromString(
                    "ef01c728-ce36-46b5-a110-84f53fdd9668"
            )
    );

    public static final SellerUUID SELLER_UUID = new SellerUUID(
            UUID.fromString(
                    "ef01c728-ce36-46b5-a110-84f53fdd9668"
            )
    );

    public static final RawMaterialData RAW_MATERIAL_DATA = RawMaterialData.GYPSUM;
}
