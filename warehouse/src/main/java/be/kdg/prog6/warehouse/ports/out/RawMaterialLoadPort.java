package be.kdg.prog6.warehouse.ports.out;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.warehouse.domain.RawMaterial;

import java.util.Optional;

public interface RawMaterialLoadPort {
    Optional<RawMaterial> loadRawMaterialByRawMaterialData(RawMaterialData rawMaterialData);
}
