package be.kdg.prog6.warehouse.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.warehouse.domain.uuid.WarehouseActivityUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;
import be.kdg.prog6.common.exceptions.InvalidOperationException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public final class WarehouseActivity {
    private WarehouseActivityUUID warehouseActivityUUID;
    private WarehouseActivityData warehouseActivityData;
    private double amount;
    private LocalDateTime time;
    private double amountShipped;
    private RawMaterialData rawMaterialData;

    public WarehouseActivity(WarehouseActivityUUID warehouseActivityUUID, WarehouseActivityData warehouseActivityData, double amount, LocalDateTime time, double amountShipped, RawMaterialData rawMaterialData) {

        if (amount <= 0) {
            throw new InvalidOperationException("Amount must be greater than zero.");
        }

        this.warehouseActivityUUID = warehouseActivityUUID;
        this.warehouseActivityData = warehouseActivityData;
        this.amount = amount;
        this.time = time;
        this.amountShipped = amountShipped;
        this.rawMaterialData = rawMaterialData;
    }
}
