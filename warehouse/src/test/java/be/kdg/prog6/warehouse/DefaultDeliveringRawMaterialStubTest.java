package be.kdg.prog6.warehouse;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.common.events.WarehouseActivityData;
import be.kdg.prog6.warehouse.core.DefaultDeliveringRawMaterialUseCase;
import be.kdg.prog6.warehouse.ports.in.DeliverRawMaterialCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultDeliveringRawMaterialStubTest {
    private DefaultDeliveringRawMaterialUseCase defaultDeliveringRawMaterialUseCase;
    private WarehouseActivityDeliveryCreateStub warehouseActivityDeliveryCreateStub;

    @BeforeEach
    public void setUp() {
        WarehouseUpdatePortStub warehouseUpdatePortStub = new WarehouseUpdatePortStub();
        warehouseActivityDeliveryCreateStub = new WarehouseActivityDeliveryCreateStub();
        WarehouseLoadPortStub warehouseLoadPortStub = new WarehouseLoadPortStub();
        RawMaterialLoadPortStub rawMaterialLoadPortStub = new RawMaterialLoadPortStub();

        defaultDeliveringRawMaterialUseCase = new DefaultDeliveringRawMaterialUseCase(
                warehouseUpdatePortStub,
                List.of(warehouseActivityDeliveryCreateStub),
                warehouseLoadPortStub,
                rawMaterialLoadPortStub
        );
    }

    @Test
    void testDeliverRawMaterial() {
        // Arrange
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                10,
                DataAndUUIDsStub.SELLER_UUID,
                DataAndUUIDsStub.RAW_MATERIAL_DATA
                );

        // Act
        defaultDeliveringRawMaterialUseCase.deliverRawMaterial(command);

        // Assert
        assertEquals(warehouseActivityDeliveryCreateStub.getWarehouseActivity().getAmount(), 10.0);
        assertEquals(warehouseActivityDeliveryCreateStub.getRawMaterialData(), DataAndUUIDsStub.RAW_MATERIAL_DATA);
        assertEquals(warehouseActivityDeliveryCreateStub.getWarehouseActivity().getWarehouseActivityData(), WarehouseActivityData.DELIVERY);
    }

    @Test
    void testDeliverRawMaterial_WhenWarehouseExists() {
        // Arrange
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                10,
                DataAndUUIDsStub.SELLER_UUID,
                DataAndUUIDsStub.RAW_MATERIAL_DATA
        );

        // Act
        defaultDeliveringRawMaterialUseCase.deliverRawMaterial(command);

        // Assert
        assertEquals(warehouseActivityDeliveryCreateStub.getRawMaterialData(), DataAndUUIDsStub.RAW_MATERIAL_DATA, "Raw material data should match the expected raw material.");
        assertEquals(warehouseActivityDeliveryCreateStub.getWarehouseActivity().getAmount(), 10.0, "Delivered amount should be 10.");
    }

    @Test
    void testDeliverRawMaterial_WhenWarehouseDoesNotExist() {
        // Arrange
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                15,
                new SellerUUID(UUID.randomUUID()),
                DataAndUUIDsStub.RAW_MATERIAL_DATA
        );

        // Act
        defaultDeliveringRawMaterialUseCase.deliverRawMaterial(command);

        // Assert
        assertNotNull(warehouseActivityDeliveryCreateStub.getWarehouseActivity().getWarehouseActivityUUID(), "A new warehouse should be created.");
        assertEquals(warehouseActivityDeliveryCreateStub.getRawMaterialData(), DataAndUUIDsStub.RAW_MATERIAL_DATA, "Raw material data should match the expected raw material.");
        assertEquals(warehouseActivityDeliveryCreateStub.getWarehouseActivity().getAmount(), 15.0, "Delivered amount should be 15.");
    }

}
