package be.kdg.prog6.warehouse;

import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.ports.out.WarehouseUpdatePort;

public class WarehouseUpdatePortStub implements WarehouseUpdatePort {
    private Warehouse warehouse;

    @Override
    public void updateWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }
}
