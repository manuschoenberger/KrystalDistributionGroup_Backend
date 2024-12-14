package be.kdg.prog6.warehouse;

import be.kdg.prog6.common.events.WarehouseActivityData;
import be.kdg.prog6.warehouse.adapters.out.db.*;
import be.kdg.prog6.warehouse.adapters.out.messaging.WarehouseUpdatePublisherDelivery;
import be.kdg.prog6.warehouse.core.DefaultDeliveringRawMaterialUseCase;
import be.kdg.prog6.warehouse.ports.in.DeliverRawMaterialCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DefaultDeliveringRawMaterialIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private DefaultDeliveringRawMaterialUseCase defaultDeliveringRawMaterialUseCase;

    @Autowired
    private WarehouseActivityRepository warehouseActivityRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @MockBean
    private WarehouseUpdatePublisherDelivery warehouseUpdatePublisherDelivery;

    @Test
    void testDeliverRawMaterial() {
        //Arrange
        WarehouseJpaEntity warehouseJpaEntity = new WarehouseJpaEntity(DataAndUUIDsStub.WAREHOUSE_UUID.uuid());

        warehouseJpaEntity.setWarehouseNumber(1);
        warehouseJpaEntity.setSellerUUID(DataAndUUIDsStub.SELLER_UUID.uuid());
        warehouseJpaEntity.setRawMaterialData(DataAndUUIDsStub.RAW_MATERIAL_DATA);

        warehouseRepository.save(warehouseJpaEntity);

        RawMaterialJpaEntity rawMaterialJpaEntity = new RawMaterialJpaEntity(UUID.randomUUID());

        rawMaterialJpaEntity.setRawMaterialData(DataAndUUIDsStub.RAW_MATERIAL_DATA);
        rawMaterialJpaEntity.setDescription("Test Description");
        rawMaterialJpaEntity.setStoragePricePerTonPerDay(1);
        rawMaterialJpaEntity.setPricePerTon(13);

        rawMaterialRepository.save(rawMaterialJpaEntity);

        // Act
        DeliverRawMaterialCommand command = new DeliverRawMaterialCommand(
                10,
                DataAndUUIDsStub.SELLER_UUID,
                DataAndUUIDsStub.RAW_MATERIAL_DATA
        );

        defaultDeliveringRawMaterialUseCase.deliverRawMaterial(command);

        // Assert
        final List<WarehouseActivityJpaEntity> warehouseActivityJpaEntities = warehouseActivityRepository.findByWarehouseUUID(DataAndUUIDsStub.WAREHOUSE_UUID.uuid());

        assertEquals(warehouseActivityJpaEntities.size(), 1);
        assertEquals(warehouseActivityJpaEntities.get(0).getWarehouseActivityData(), WarehouseActivityData.DELIVERY);
        assertEquals(warehouseActivityJpaEntities.get(0).getAmount(), 10);
        assertEquals(warehouseActivityJpaEntities.get(0).getRawMaterialData(), DataAndUUIDsStub.RAW_MATERIAL_DATA);
        assertEquals(warehouseActivityJpaEntities.get(0).getWarehouseUUID(), DataAndUUIDsStub.WAREHOUSE_UUID.uuid());
    }


}
