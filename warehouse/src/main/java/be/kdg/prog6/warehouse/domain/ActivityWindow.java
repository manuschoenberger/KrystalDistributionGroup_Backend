package be.kdg.prog6.warehouse.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static be.kdg.prog6.common.events.WarehouseActivityData.DELIVERY;
import static be.kdg.prog6.common.events.WarehouseActivityData.SHIPMENT;

@Getter
@Setter
public class ActivityWindow {

    private final List<WarehouseActivity> activities = new ArrayList<>();

    public ActivityWindow() {
    }

    public boolean add(WarehouseActivity warehouseActivity) {
        return activities.add(warehouseActivity);
    }

    public double computeCapacity() {
        double capacity = 0;
        for (WarehouseActivity activity : activities) {
            if (activity.getWarehouseActivityData() == DELIVERY) {
                capacity += activity.getAmount();
            } else if (activity.getWarehouseActivityData() == SHIPMENT) {
                capacity -= activity.getAmount();
            }
        }

        return capacity;
    }
}
