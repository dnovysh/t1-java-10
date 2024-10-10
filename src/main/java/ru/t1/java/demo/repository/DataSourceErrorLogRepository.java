package ru.t1.java.demo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.model.entity.DataSourceErrorLog;

public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog, UUID> {

}
