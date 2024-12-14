package be.kdg.prog6.landside.domain;

public record LicensePlate(String licensePlate) {
    public LicensePlate {
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be null or blank");
        }
    }
}
