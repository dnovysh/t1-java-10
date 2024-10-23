package ru.t1.java.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.model.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
