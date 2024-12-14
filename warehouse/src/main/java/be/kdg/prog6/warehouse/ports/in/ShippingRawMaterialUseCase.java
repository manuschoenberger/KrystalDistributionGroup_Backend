package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;

public interface ShippingRawMaterialUseCase {
    void shipRawMaterial(ReferenceUUID referenceUUID);
}
