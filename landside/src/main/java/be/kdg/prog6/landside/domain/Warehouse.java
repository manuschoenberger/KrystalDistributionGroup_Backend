package be.kdg.prog6.landside.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.domain.uuid.WarehouseUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;
import be.kdg.prog6.common.exceptions.InvalidOperationException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warehouse {
    private static final double CAPACITY = 500000;
    private static final double CAPACITY_THRESHOLD = 0.80;

    private final WarehouseUUID warehouseUUID;
    private final SellerUUID sellerUUID;
    private int warehouseNumber;
    private final RawMaterialData rawMaterialData;

    private double currentCapacity;

    public Warehouse(WarehouseUUID warehouseUUID, SellerUUID sellerUUID, int warehouseNumber, RawMaterialData rawMaterialData, double currentCapacity) {
        this.warehouseUUID = warehouseUUID;
        this.sellerUUID = sellerUUID;
        this.warehouseNumber = warehouseNumber;
        this.rawMaterialData = rawMaterialData;
        this.currentCapacity = currentCapacity;
    }

    public void modifyWarehouseCapacity(WarehouseActivityData action, double amount) {
        if (amount < 0) {
            throw new InvalidOperationException("Amount must be greater than zero.");
        }
        switch (action) {
            case DELIVERY -> currentCapacity += amount;
            case SHIPMENT -> currentCapacity -= amount;
        }
    }

    public boolean checkWarehouseCapacity() {
        return currentCapacity < CAPACITY * CAPACITY_THRESHOLD;
    }
}
