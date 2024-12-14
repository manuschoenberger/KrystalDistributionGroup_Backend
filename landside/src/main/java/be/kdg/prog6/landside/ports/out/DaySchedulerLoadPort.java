package be.kdg.prog6.landside.ports.out;

import be.kdg.prog6.landside.domain.DayScheduler;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DaySchedulerLoadPort {
    Optional<DayScheduler> loadSchedulerByDate(LocalDate date);

    Optional<List<DayScheduler>> loadAllDayScheduler();
}
