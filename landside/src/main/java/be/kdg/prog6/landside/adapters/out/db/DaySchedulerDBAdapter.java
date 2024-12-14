package be.kdg.prog6.landside.adapters.out.db;

import be.kdg.prog6.common.domain.uuid.SellerUUID;
import be.kdg.prog6.landside.domain.*;
import be.kdg.prog6.landside.domain.uuid.DaySchedulerUUID;
import be.kdg.prog6.landside.domain.uuid.DeliveryAppointmentUUID;
import be.kdg.prog6.landside.ports.out.DaySchedulerLoadPort;
import be.kdg.prog6.landside.ports.out.DaySchedulerUpdatePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class DaySchedulerDBAdapter implements DaySchedulerLoadPort, DaySchedulerUpdatePort {

    private final DaySchedulerRepository daySchedulerRepository;

    public DaySchedulerDBAdapter(DaySchedulerRepository daySchedulerRepository) {
        this.daySchedulerRepository = daySchedulerRepository;
    }

    @Override
    public Optional<DayScheduler> loadSchedulerByDate(LocalDate date) {
        Optional<DaySchedulerJpaEntity> daySchedulerJpaEntity = daySchedulerRepository.findByDate(date);

        if (daySchedulerJpaEntity.isEmpty()) {
            return Optional.empty();
        }

        DayScheduler dayScheduler = new DayScheduler(
                new DaySchedulerUUID(daySchedulerJpaEntity.get().getDaySchedulerUUID()),
                daySchedulerJpaEntity.get().getDate()
        );

        List<DeliveryAppointmentHourTimeslot> deliveryAppointmentHourTimeslots = daySchedulerJpaEntity.get()
                .getDeliveryAppointmentHourTimeslotJpaEntities()
                .stream()
                .map(this::mapToDeliveryAppointmentHourTimeslot)
                .collect(Collectors.toList());

        dayScheduler.setDeliveryAppointmentHourTimeslots(deliveryAppointmentHourTimeslots);

        return Optional.of(dayScheduler);
    }

    @Override
    public Optional<List<DayScheduler>> loadAllDayScheduler() {
        List<DaySchedulerJpaEntity> dayScheduleJpaEntities = daySchedulerRepository.findAll();

        if (dayScheduleJpaEntities.isEmpty()) {
            return Optional.empty();
        }

        List<DayScheduler> dayScheduler = mapToDomainDayScheduler(dayScheduleJpaEntities);
        return Optional.of(dayScheduler);
    }

    private List<DayScheduler> mapToDomainDayScheduler(List<DaySchedulerJpaEntity> daySchedulerJpaEntities) {
        return daySchedulerJpaEntities.stream()
                .map(daySchedulerJpaEntity -> {
                    DayScheduler dayScheduler = new DayScheduler(new DaySchedulerUUID(daySchedulerJpaEntity.getDaySchedulerUUID()), daySchedulerJpaEntity.getDate());

                    List<DeliveryAppointmentHourTimeslot> deliveryAppointmentHourTimeslots = daySchedulerJpaEntity.getDeliveryAppointmentHourTimeslotJpaEntities().stream()
                            .map(this::mapToDeliveryAppointmentHourTimeslot)
                            .collect(Collectors.toList());

                    dayScheduler.setDeliveryAppointmentHourTimeslots(deliveryAppointmentHourTimeslots);

                    return dayScheduler;
                })
                .collect(Collectors.toList());
    }

    private DeliveryAppointment mapToDeliveryAppointment(DeliveryAppointmentJpaEntity deliveryAppointmentJpaEntity) {
        return new DeliveryAppointment(
                new DeliveryAppointmentUUID(deliveryAppointmentJpaEntity.getDeliveryAppointmentUUID()),
                new SellerUUID(deliveryAppointmentJpaEntity.getSellerUUID()),
                new LicensePlate(deliveryAppointmentJpaEntity.getLicensePlate()),
                deliveryAppointmentJpaEntity.getPayloadData(),
                deliveryAppointmentJpaEntity.getDeliveryAppointmentWindowStart(),
                deliveryAppointmentJpaEntity.getDeliveryAppointmentStatus()
        );
    }

    private DeliveryAppointmentHourTimeslot mapToDeliveryAppointmentHourTimeslot(DeliveryAppointmentHourTimeslotJpaEntity deliveryAppointmentHourTimeslotJpaEntity) {
        DeliveryAppointmentHourTimeslot deliveryAppointmentHourTimeslot = new DeliveryAppointmentHourTimeslot(
                new DeliveryAppointmentHourTimeslot.DeliveryAppointmentHourTimeslotUUID(deliveryAppointmentHourTimeslotJpaEntity.getTimeslotUUID()),
                deliveryAppointmentHourTimeslotJpaEntity.getStartTime()
        );

        List<DeliveryAppointment> deliveryAppointments = deliveryAppointmentHourTimeslotJpaEntity.getDeliveryAppointmentJpaEntities()
                .stream()
                .map(this::mapToDeliveryAppointment)
                .collect(Collectors.toList());

        deliveryAppointmentHourTimeslot.setDeliveryAppointments(deliveryAppointments);

        return deliveryAppointmentHourTimeslot;
    }

    @Override
    @Transactional
    public void updateDayScheduler(DayScheduler dayScheduler) {
        DaySchedulerJpaEntity daySchedulerJpaEntity = new DaySchedulerJpaEntity(dayScheduler.getDaySchedulerUUID().uuid());
        daySchedulerJpaEntity.setDate(dayScheduler.getDate());

        List<DeliveryAppointmentHourTimeslotJpaEntity> deliveryAppointmentTimeslotJpaEntities = dayScheduler.getDeliveryAppointmentHourTimeslots()
                .stream()
                .map(deliveryAppointmentHourTimeslot -> {
                    DeliveryAppointmentHourTimeslotJpaEntity deliveryAppointmentHourTimeslotJpaEntity = mapToJpaDeliveryAppointmentHourTimeslot(deliveryAppointmentHourTimeslot);
                    deliveryAppointmentHourTimeslotJpaEntity.setDayScheduler(daySchedulerJpaEntity);
                    return deliveryAppointmentHourTimeslotJpaEntity;
                })
                .collect(Collectors.toList());

        daySchedulerJpaEntity.setDeliveryAppointmentHourTimeslotJpaEntities(deliveryAppointmentTimeslotJpaEntities);

        daySchedulerRepository.save(daySchedulerJpaEntity);
    }

    private DeliveryAppointmentHourTimeslotJpaEntity mapToJpaDeliveryAppointmentHourTimeslot(DeliveryAppointmentHourTimeslot deliveryAppointmentHourTimeslot) {
        DeliveryAppointmentHourTimeslotJpaEntity deliveryAppointmentHourTimeslotJpaEntity = new DeliveryAppointmentHourTimeslotJpaEntity(
                deliveryAppointmentHourTimeslot.getDeliveryAppointmentHourTimeslotUUID().uuid()
        );
        deliveryAppointmentHourTimeslotJpaEntity.setStartTime(deliveryAppointmentHourTimeslot.getStartTime());

        List<DeliveryAppointmentJpaEntity> deliveryAppointmentJpaEntities = deliveryAppointmentHourTimeslot.getDeliveryAppointments()
                .stream()
                .map(deliveryAppointment -> {
                    DeliveryAppointmentJpaEntity deliveryAppointmentJpaEntity = mapToJpaDeliveryAppointment(deliveryAppointment);
                    deliveryAppointmentJpaEntity.setDeliveryAppointmentHourTimeslotJpaEntity(deliveryAppointmentHourTimeslotJpaEntity);
                    return deliveryAppointmentJpaEntity;
                })
                .collect(Collectors.toList());

        deliveryAppointmentHourTimeslotJpaEntity.setDeliveryAppointmentJpaEntities(deliveryAppointmentJpaEntities);

        return deliveryAppointmentHourTimeslotJpaEntity;
    }

    private DeliveryAppointmentJpaEntity mapToJpaDeliveryAppointment(DeliveryAppointment deliveryAppointment) {
        DeliveryAppointmentJpaEntity deliveryAppointmentJpaEntity = new DeliveryAppointmentJpaEntity(deliveryAppointment.getDeliveryAppointmentUUID().uuid());
        deliveryAppointmentJpaEntity.setSellerUUID(deliveryAppointment.getSellerUUID().uuid());
        deliveryAppointmentJpaEntity.setLicensePlate(deliveryAppointment.getLicensePlate().licensePlate());
        deliveryAppointmentJpaEntity.setPayloadData(deliveryAppointment.getPayloadData());
        deliveryAppointmentJpaEntity.setDeliveryAppointmentWindowStart(deliveryAppointment.getArrivalWindowStart());
        deliveryAppointmentJpaEntity.setDeliveryAppointmentStatus(deliveryAppointment.getDeliveryAppointmentStatus());

        return deliveryAppointmentJpaEntity;
    }
}
