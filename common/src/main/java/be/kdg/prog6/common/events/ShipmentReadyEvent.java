package be.kdg.prog6.common.events;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;

public record ShipmentReadyEvent(ReferenceUUID referenceUUID, double materialCosts) {
}
