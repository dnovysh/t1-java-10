package ru.t1.java.demo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.model.entity.TimeLimitExceedLog;

public interface TimeLimitExceedLogRepository extends JpaRepository<TimeLimitExceedLog, UUID> {

}
