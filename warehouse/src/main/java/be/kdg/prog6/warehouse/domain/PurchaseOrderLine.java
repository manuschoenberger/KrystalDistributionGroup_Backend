package be.kdg.prog6.warehouse.domain;

import be.kdg.prog6.common.domain.RawMaterialData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderLine {
    private int lineNumber;
    private RawMaterialData materialType;
    private String description;
    private int quantity;
    private String uom;

    public PurchaseOrderLine(int lineNumber, RawMaterialData materialType, String description, int quantity, String uom) {
        this.lineNumber = lineNumber;
        this.materialType = materialType;
        this.description = description;
        this.quantity = quantity;
        this.uom = uom;
    }

    public PurchaseOrderLine() {
    }
}
