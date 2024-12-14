package be.kdg.prog6.warehouse;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.warehouse.core.DefaultDeliveringRawMaterialUseCase;
import be.kdg.prog6.warehouse.domain.ActivityWindow;
import be.kdg.prog6.warehouse.domain.RawMaterial;
import be.kdg.prog6.warehouse.domain.Warehouse;
import be.kdg.prog6.warehouse.domain.WarehouseActivity;
import be.kdg.prog6.warehouse.ports.in.DeliverRawMaterialCommand;
import be.kdg.prog6.warehouse.ports.out.RawMaterialLoadPort;
import be.kdg.prog6.warehouse.ports.out.WarehouseActivityDeliveryCreatePort;
import be.kdg.prog6.warehouse.ports.out.WarehouseLoadPort;
import be.kdg.prog6.warehouse.ports.out.WarehouseUpdatePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultDeliveringRawMaterialMockTest {

    @Mock
    private WarehouseUpdatePort warehouseUpdatePort;

    @Mock
    private WarehouseActivityDeliveryCreatePort warehouseActivityDeliveryCreatePort;

    @Mock
    private WarehouseLoadPort warehouseLoadPort;

    @Mock
    private RawMaterialLoadPort rawMaterialLoadPort;

    @InjectMocks
    private DefaultDeliveringRawMaterialUseCase deliveringRawMaterialUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        List<WarehouseActivityDeliveryCreatePort> warehouseActivityDeliveryCreatePorts = List.of(warehouseActivityDeliveryCreatePort);

        deliveringRawMaterialUseCase = new DefaultDeliveringRawMaterialUseCase(
                warehouseUpdatePort,
                warehouseActivityDeliveryCreatePorts,
                warehouseLoadPort,
                rawMaterialLoadPort
        );
    }

    @Test
    void testDeliverRawMaterial_WhenWarehouseExists() {
        // Arrange
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                10,
                DataAndUUIDsStub.SELLER_UUID,
                DataAndUUIDsStub.RAW_MATERIAL_DATA
        );

        RawMaterial mockRawMaterial = new RawMaterial(DataAndUUIDsStub.RAW_MATERIAL_DATA, "Test Description", 1, 13);
        Warehouse mockWarehouse = new Warehouse(DataAndUUIDsStub.WAREHOUSE_UUID, 1, DataAndUUIDsStub.RAW_MATERIAL_DATA, new ActivityWindow(), DataAndUUIDsStub.SELLER_UUID);

        when(rawMaterialLoadPort.loadRawMaterialByRawMaterialData(DataAndUUIDsStub.RAW_MATERIAL_DATA))
                .thenReturn(Optional.of(mockRawMaterial));
        when(warehouseLoadPort.loadWarehouseBySellerAndRawMaterialData(DataAndUUIDsStub.SELLER_UUID, DataAndUUIDsStub.RAW_MATERIAL_DATA))
                .thenReturn(Optional.of(mockWarehouse));

        // Act
        deliveringRawMaterialUseCase.deliverRawMaterial(command);

        // Assert
        verify(warehouseActivityDeliveryCreatePort, times(1)).createWarehouseActivity(
                any(UUID.class),
                eq(DataAndUUIDsStub.WAREHOUSE_UUID),
                eq(mockWarehouse.getWarehouseNumber()),
                eq(DataAndUUIDsStub.SELLER_UUID),
                eq(DataAndUUIDsStub.RAW_MATERIAL_DATA),
                any(WarehouseActivity.class)
        );
    }

    @Test
    void testDeliverRawMaterial_WhenWarehouseDoesNotExist() {
        // Arrange
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                15,
                new SellerUUID(UUID.randomUUID()),
                DataAndUUIDsStub.RAW_MATERIAL_DATA
        );

        RawMaterial mockRawMaterial = new RawMaterial(DataAndUUIDsStub.RAW_MATERIAL_DATA, "Test Description", 1, 13);
        when(rawMaterialLoadPort.loadRawMaterialByRawMaterialData(DataAndUUIDsStub.RAW_MATERIAL_DATA))
                .thenReturn(Optional.of(mockRawMaterial));
        when(warehouseLoadPort.loadWarehouseBySellerAndRawMaterialData(command.sellerUUID(), DataAndUUIDsStub.RAW_MATERIAL_DATA))
                .thenReturn(Optional.empty());

        // Capture the new warehouse creation
        ArgumentCaptor<Warehouse> warehouseCaptor = ArgumentCaptor.forClass(Warehouse.class);

        // Act
        deliveringRawMaterialUseCase.deliverRawMaterial(command);

        // Assert
        verify(warehouseUpdatePort).updateWarehouse(warehouseCaptor.capture());
        assertEquals(command.sellerUUID(), warehouseCaptor.getValue().getSellerUUID(), "Seller UUID should match the command's UUID.");
        assertEquals(DataAndUUIDsStub.RAW_MATERIAL_DATA, warehouseCaptor.getValue().getRawMaterialData(), "Raw material data should match.");

        verify(warehouseActivityDeliveryCreatePort, times(1)).createWarehouseActivity(
                any(UUID.class),
                eq(warehouseCaptor.getValue().getWarehouseUUID()),
                eq(warehouseCaptor.getValue().getWarehouseNumber()),
                eq(command.sellerUUID()),
                eq(DataAndUUIDsStub.RAW_MATERIAL_DATA),
                any(WarehouseActivity.class)
        );
    }

    @Test
    void testDeliverRawMaterial_WhenRawMaterialNotFound() {
        // Arrange
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                10,
                DataAndUUIDsStub.SELLER_UUID,
                DataAndUUIDsStub.RAW_MATERIAL_DATA
        );

        when(rawMaterialLoadPort.loadRawMaterialByRawMaterialData(DataAndUUIDsStub.RAW_MATERIAL_DATA))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () ->
                deliveringRawMaterialUseCase.deliverRawMaterial(command), "Should throw exception when raw material is not found."
        );
    }
}
