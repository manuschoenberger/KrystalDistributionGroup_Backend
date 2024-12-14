package be.kdg.prog6.warehouse.adapters.in.web;

import be.kdg.prog6.common.domain.uuid.ReferenceUUID;
import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.warehouse.domain.Party;
import be.kdg.prog6.warehouse.domain.PurchaseOrder;
import be.kdg.prog6.warehouse.domain.PurchaseOrderLine;
import be.kdg.prog6.warehouse.ports.in.FetchingPurchaseOrdersUseCase;
import be.kdg.prog6.warehouse.ports.in.SendPurchaseOrderCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class FetchPurchaseOrderController {

    private final ObjectMapper objectMapper;
    private final FetchingPurchaseOrdersUseCase fetchingPurchaseOrdersUseCase;

    public FetchPurchaseOrderController(ObjectMapper objectMapper, FetchingPurchaseOrdersUseCase fetchingPurchaseOrdersUseCase) {
        this.objectMapper = objectMapper;
        this.fetchingPurchaseOrdersUseCase = fetchingPurchaseOrdersUseCase;
    }

    @PostMapping("/purchase-order")
    public String getPurchaseOrder(@RequestBody PurchaseOrderRequest request) throws JsonProcessingException {
        SendPurchaseOrderCommand sendPurchaseOrderCommand = new SendPurchaseOrderCommand(
                request.poNumber(),
                new ReferenceUUID(request.referenceUUID()),
                new Party(
                        request.customerParty().partyUUID(),
                        request.customerParty().name(),
                        request.customerParty().address()
                ),
                new Party(
                        request.sellerParty().partyUUID(),
                        request.sellerParty().name(),
                        request.sellerParty().address()
                ),
                request.vesselNumber(),
                request.orderLines().stream().map(line ->
                        new PurchaseOrderLine(
                                line.lineNumber(),
                                RawMaterialData.valueOf(line.materialType()),
                                line.description(),
                                line.quantity(),
                                line.uom()
                        )
                ).toList()
        );

        fetchingPurchaseOrdersUseCase.getPurchaseOrder(sendPurchaseOrderCommand);

        return objectMapper.writeValueAsString(sendPurchaseOrderCommand);
    }

    @GetMapping("/purchase-order")
    public List<PurchaseOrder> fetchPurchaseOrders() {
        return fetchingPurchaseOrdersUseCase.getPurchaseOrders();
    }

    @GetMapping("/purchase-order/{referenceUUID}")
    public PurchaseOrder getPurchaseOrderByUUID(@PathVariable UUID referenceUUID) {
        return fetchingPurchaseOrdersUseCase.getPurchaseOrderByUUID(referenceUUID);
    }

    public record PurchaseOrderRequest(
            UUID referenceUUID,
            String poNumber,
            PurchaseOrderPartyRequest customerParty,
            PurchaseOrderPartyRequest sellerParty,
            String vesselNumber,
            List<PurchaseOrderLineRequest> orderLines
    ) {
    }

    public record PurchaseOrderLineRequest(
            int lineNumber,
            String materialType,
            String description,
            int quantity,
            String uom
    ) {
    }

    public record PurchaseOrderPartyRequest(
            UUID partyUUID,
            String name,
            String address
    ) {
    }
}
