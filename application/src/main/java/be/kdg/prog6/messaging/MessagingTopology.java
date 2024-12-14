package be.kdg.prog6.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingTopology {
    private static final String WAREHOUSE_EXCHANGE = "warehouse_events";
    private static final String WATERSIDE_EXCHANGE = "waterside_events";
    private static final String INVOICE_EXCHANGE = "invoice_events";
    private static final String CREATED_ACTIVITIES_QUEUE = "capacity_changes";
    private static final String RECEIVE_WAREHOUSES_QUEUE = "warehouse_receives";
    private static final String PAYLOAD_SUBMITTED_QUEUE = "payload_submitted";
    private static final String SHIPMENT_MADE_QUEUE = "shipment_made";
    private static final String SHIPMENT_READY_WATERSIDE_QUEUE = "shipment_ready_waterside";
    private static final String SHIPMENT_READY_INVOICE_QUEUE = "shipment_ready_invoice";

    @Bean
    Queue createdWarehouseActivitiesQueue() {
        return new Queue(CREATED_ACTIVITIES_QUEUE, false);
    }

    @Bean
    Queue createdWarehouseQueue() {
        return new Queue(RECEIVE_WAREHOUSES_QUEUE, false);
    }

    @Bean
    Queue payloadSubmittedQueue() {
        return new Queue(PAYLOAD_SUBMITTED_QUEUE, false);
    }

    @Bean
    Queue shipmentMadeQueue() {
        return new Queue(SHIPMENT_MADE_QUEUE, false);
    }

    @Bean
    Queue shipmentReadyWatersideQueue() {
        return new Queue(SHIPMENT_READY_WATERSIDE_QUEUE, false);
    }

    @Bean
    Queue shipmentReadyInvoiceQueue() {
        return new Queue(SHIPMENT_READY_INVOICE_QUEUE, false);
    }

    @Bean
    TopicExchange warehouseExchange() {
        return new TopicExchange(WAREHOUSE_EXCHANGE);
    }

    @Bean
    TopicExchange watersideExchange() {
        return new TopicExchange(WATERSIDE_EXCHANGE);
    }

    @Bean
    TopicExchange invoiceExchange() {
        return new TopicExchange(INVOICE_EXCHANGE);
    }

    @Bean
    Binding warehouseActivityBinding(Queue createdWarehouseActivitiesQueue, TopicExchange warehouseExchange) {
        return BindingBuilder
                .bind(createdWarehouseActivitiesQueue)
                .to(warehouseExchange)
                .with("warehouse.#.activity.#.created");
    }

    @Bean
    Binding warehouseCreatedBinding(Queue createdWarehouseQueue, TopicExchange warehouseExchange) {
        return BindingBuilder
                .bind(createdWarehouseQueue)
                .to(warehouseExchange)
                .with("warehouse.#.created");
    }

    @Bean
    Binding payloadSubmittedBinding(Queue payloadSubmittedQueue, TopicExchange warehouseExchange) {
        return BindingBuilder
                .bind(payloadSubmittedQueue)
                .to(warehouseExchange)
                .with("payload.submitted");
    }

    @Bean
    Binding shipmentMadeBinding(Queue shipmentMadeQueue, TopicExchange watersideExchange) {
        return BindingBuilder
                .bind(shipmentMadeQueue)
                .to(watersideExchange)
                .with("waterside.shipment.made");
    }

    @Bean
    Binding shipmentReadyWatersideBinding(Queue shipmentReadyWatersideQueue, TopicExchange warehouseExchange) {
        return BindingBuilder
                .bind(shipmentReadyWatersideQueue)
                .to(warehouseExchange)
                .with("invoice.#.shipment.ready");
    }

    @Bean
    Binding shipmentReadyInvoiceBinding(Queue shipmentReadyInvoiceQueue, TopicExchange warehouseExchange) {
        return BindingBuilder
                .bind(shipmentReadyInvoiceQueue)
                .to(warehouseExchange)
                .with("invoice.#.shipment.ready");
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}