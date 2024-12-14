package be.kdg.prog6.warehouse.ports.out;

import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseActivityLoadPort {

    Optional<List<WarehouseActivity>> loadAllWarehouseActivities();
}
