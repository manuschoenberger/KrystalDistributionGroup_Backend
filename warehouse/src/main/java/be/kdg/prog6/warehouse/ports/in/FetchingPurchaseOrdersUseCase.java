package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.warehouse.domain.PurchaseOrder;

import java.util.List;
import java.util.UUID;

public interface FetchingPurchaseOrdersUseCase {
    void getPurchaseOrder(SendPurchaseOrderCommand sendPurchaseOrderCommand);
    PurchaseOrder getPurchaseOrderByUUID(UUID referenceUUID);

    List<PurchaseOrder> getPurchaseOrders();
}
