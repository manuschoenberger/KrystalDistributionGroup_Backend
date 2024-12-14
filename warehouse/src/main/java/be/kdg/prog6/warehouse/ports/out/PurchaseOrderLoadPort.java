package be.kdg.prog6.warehouse.ports.out;


import be.kdg.prog6.warehouse.domain.PurchaseOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrderLoadPort {
    Optional<PurchaseOrder> loadPurchaseOrderByReferenceUUID(UUID referenceUUID);

    Optional<List<PurchaseOrder>> loadPurchaseOrders();
}
