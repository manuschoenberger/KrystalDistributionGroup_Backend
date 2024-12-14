package be.kdg.prog6.warehouse.adapters.out.db;

import be.kdg.prog6.common.domain.RawMaterialData;
import be.kdg.prog6.warehouse.domain.RawMaterial;
import be.kdg.prog6.warehouse.ports.out.RawMaterialLoadPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RawMaterialDBAdapter implements RawMaterialLoadPort {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialDBAdapter(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public Optional<RawMaterial> loadRawMaterialByRawMaterialData(RawMaterialData rawMaterialData) {
        Optional<RawMaterialJpaEntity> rawMaterialJpaEntity = rawMaterialRepository.findByRawMaterialData(rawMaterialData);

        if (rawMaterialJpaEntity.isEmpty()) {
            return Optional.empty();
        }

        RawMaterial rawMaterial = new RawMaterial(rawMaterialJpaEntity.get().getRawMaterialData(), rawMaterialJpaEntity.get().getDescription(), rawMaterialJpaEntity.get().getStoragePricePerTonPerDay(), rawMaterialJpaEntity.get().getPricePerTon());
        return Optional.of(rawMaterial);
    }
}
