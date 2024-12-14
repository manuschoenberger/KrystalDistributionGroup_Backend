package be.kdg.prog6.warehouse.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Party {
    private UUID uuid;
    private String name;
    private String address;

    public Party(UUID uuid, String name, String address) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
    }
}
