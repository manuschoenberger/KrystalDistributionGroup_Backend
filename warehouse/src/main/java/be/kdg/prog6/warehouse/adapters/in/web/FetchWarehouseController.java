package be.kdg.prog6.warehouse.adapters.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class FetchWarehouseController {

    /*
    private final DeliveringRawMaterialUseCase deliveringRawMaterialUseCase;

    public WarehouseController(DeliveringRawMaterialUseCase deliveringRawMaterialUseCase) {
        this.deliveringRawMaterialUseCase = deliveringRawMaterialUseCase;
    }
     */

    @PostMapping("/delivery")
    public String deliverRawMaterial(@RequestBody DeliveryRequest request) {

        return "Deprecated";

        /*
        return deliveringRawMaterialUseCase.deliverRawMaterial(
                new DeliverRawMaterialCommand(
                        request.amount,
                        new SellerUUID(request.sellerUUID),
                        RawMaterialData.valueOf(request.material.toUpperCase())
                )
        );
         */
    }



    public record DeliveryRequest(UUID sellerUUID, String material, int amount) {
    }
}
