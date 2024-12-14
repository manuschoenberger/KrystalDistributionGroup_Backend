package be.kdg.prog6.waterside.domain;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.waterside.domain.uuid.ShipmentOrderUUID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ShipmentOrder {
    private ShipmentOrderUUID shipmentOrderUUID;
    private ReferenceUUID referenceUUID;
    private SellerUUID sellerUUID;
    private ShipmentStatus shipmentStatus;
    private String vesselNumber;
    private LocalDateTime estimatedArrivalTime;
    private LocalDateTime estimatedDepartureTime;
    private BunkeringOperation bunkeringOperation;
    private InspectionOperation inspectionOperation;

    public ShipmentOrder(ShipmentOrderUUID shipmentOrderUUID, ReferenceUUID referenceUUID, SellerUUID sellerUUID, ShipmentStatus shipmentStatus, String vesselNumber, LocalDateTime estimatedArrivalTime, LocalDateTime estimatedDepartureTime, LocalDateTime bunkeringOperationTime, LocalDateTime inspectionOperationTime, String inspectionSignature) {
        this.shipmentOrderUUID = shipmentOrderUUID;
        this.referenceUUID = referenceUUID;
        this.sellerUUID = sellerUUID;
        this.shipmentStatus = shipmentStatus;
        this.vesselNumber = vesselNumber;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.estimatedDepartureTime = estimatedDepartureTime;
        this.bunkeringOperation = new BunkeringOperation(bunkeringOperationTime);
        this.inspectionOperation = new InspectionOperation(inspectionOperationTime, inspectionSignature);
    }

    public ShipmentOrder(SellerUUID sellerUUID, ReferenceUUID referenceUUID, String vesselNumber) {
        this(
                new ShipmentOrderUUID(UUID.randomUUID()),
                referenceUUID,
                sellerUUID,
                ShipmentStatus.PLANNED,
                vesselNumber,
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(3),
                ""
        );
    }

    public record BunkeringOperation(LocalDateTime bunkeringOperationTime) {
    }

    public record InspectionOperation(LocalDateTime inspectionOperationTime, String inspectionSignature) {
    }

    public ShipmentOrder(ShipmentOrderUUID shipmentOrderUUID) {
        this.shipmentOrderUUID = shipmentOrderUUID;
    }

    public void setBunkeringOperationTime(LocalDateTime bunkeringOperationTime) {
        this.bunkeringOperation = new BunkeringOperation(bunkeringOperationTime);
    }

    public void setInspectionOperationTime(LocalDateTime inspectionOperationTime) {
        if (this.inspectionOperation == null) {
            this.inspectionOperation = new InspectionOperation(inspectionOperationTime, "");
        } else {
            this.inspectionOperation = new InspectionOperation(inspectionOperationTime, this.inspectionOperation.inspectionSignature());
        }
    }

    public void setInspectionSignature(String inspectionSignature) {
        if (this.inspectionOperation == null) {
            this.inspectionOperation = new InspectionOperation(LocalDateTime.now(), inspectionSignature);
        } else {
            this.inspectionOperation = new InspectionOperation(this.inspectionOperation.inspectionOperationTime(), inspectionSignature);
        }
    }
}
