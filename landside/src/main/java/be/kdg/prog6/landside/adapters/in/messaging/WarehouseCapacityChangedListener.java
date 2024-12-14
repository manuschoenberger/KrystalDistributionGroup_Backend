package be.kdg.prog6.landside.adapters.in.messaging;

import be.kdg.prog6.common.events.WarehouseActivityCreatedEvent;
import be.kdg.prog6.landside.ports.in.WarehouseCapacityProjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WarehouseCapacityChangedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseCapacityChangedListener.class);
    private static final String CREATED_ACTIVITIES_QUEUE = "capacity_changes";

    private final WarehouseCapacityProjector warehouseCapacityProjector;

    public WarehouseCapacityChangedListener(WarehouseCapacityProjector warehouseCapacityProjector) {
        this.warehouseCapacityProjector = warehouseCapacityProjector;
    }

    @RabbitListener(queues = CREATED_ACTIVITIES_QUEUE)
    public void activityCreated(final WarehouseActivityCreatedEvent warehouseActivityCreatedEvent) {
        LOGGER.info(
                "Activity got created on warehouse with warehouse-Id: {}, with an amount of {} tons of {} at {}. Activity Data: {}",
                warehouseActivityCreatedEvent.warehouseUUID(),
                warehouseActivityCreatedEvent.amount(),
                warehouseActivityCreatedEvent.rawMaterialData(),
                warehouseActivityCreatedEvent.time(),
                warehouseActivityCreatedEvent.warehouseActivityData()
        );

        warehouseCapacityProjector.projectWarehouseCapacity(
                warehouseActivityCreatedEvent.warehouseUUID(),
                warehouseActivityCreatedEvent.sellerUUID(),
                warehouseActivityCreatedEvent.warehouseNumber(),
                warehouseActivityCreatedEvent.rawMaterialData(),
                warehouseActivityCreatedEvent.warehouseActivityData(),
                warehouseActivityCreatedEvent.amount(),
                warehouseActivityCreatedEvent.time()
        );
    }
}
