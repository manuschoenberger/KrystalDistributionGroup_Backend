package be.kdg.prog6.warehouse.ports.out;

import be.kdg.prog6.warehouse.domain.PurchaseOrder;

public interface PurchaseOrderUpdatePort {
    void updatePurchaseOrder(PurchaseOrder purchaseOrder);
}
