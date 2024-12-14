package be.kdg.prog6.warehouse;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.warehouse.domain.RawMaterial;
import be.kdg.prog6.warehouse.ports.out.RawMaterialLoadPort;

import java.util.Optional;

public class RawMaterialLoadPortStub implements RawMaterialLoadPort {

    @Override
    public Optional<RawMaterial> loadRawMaterialByRawMaterialData(RawMaterialData rawMaterialData) {
        if (DataAndUUIDsStub.RAW_MATERIAL_DATA.equals(rawMaterialData)) {

            return Optional.of(
                    new RawMaterial(
                            DataAndUUIDsStub.RAW_MATERIAL_DATA,
                            "Test Description",
                            1,
                            13
                    )
            );
        }

        return Optional.empty();
    }
}
