package be.kdg.prog6.warehouse.domain;

import be.kdg.prog6.common.domain.RawMaterialData;

public record RawMaterial(RawMaterialData rawMaterialData, String description, double storagePricePerTonPerDay, double pricePerTon) {
}
