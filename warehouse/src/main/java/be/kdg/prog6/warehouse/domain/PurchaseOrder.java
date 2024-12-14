package be.kdg.prog6.warehouse.domain;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseOrder {
    private String poNumber;
    private ReferenceUUID referenceUUID;
    private Party customerParty;
    private Party sellerParty;
    private String vesselNumber;
    private List<PurchaseOrderLine> purchaseOrderLines;
    private boolean arrived;
    private double totalAmount;

    public PurchaseOrder(ReferenceUUID referenceUUID) {
        this.referenceUUID = referenceUUID;
    }

    public void calculateTotalAmount(List<RawMaterial> rawMaterials) {
        this.totalAmount = purchaseOrderLines.stream()
                .flatMap(purchaseOrderLine -> rawMaterials.stream()
                        .filter(rawMaterial -> purchaseOrderLine.getMaterialType().equals(rawMaterial.rawMaterialData()))
                        .map(rawMaterial -> purchaseOrderLine.getQuantity() * rawMaterial.pricePerTon()))
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}

