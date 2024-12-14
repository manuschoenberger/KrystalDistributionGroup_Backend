package be.kdg.prog6.warehouse.ports.in;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.warehouse.domain.Party;
import be.kdg.prog6.warehouse.domain.PurchaseOrderLine;

import java.util.List;

public record SendPurchaseOrderCommand(
        String poNumber,
        ReferenceUUID referenceUUID,
        Party customerParty,
        Party sellerParty,
        String vesselNumber,
        List<PurchaseOrderLine> purchasePurchaseOrderLines
) {
}
