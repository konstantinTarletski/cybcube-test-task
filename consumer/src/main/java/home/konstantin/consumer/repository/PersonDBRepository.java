package home.konstantin.consumer.repository;

import home.konstantin.consumer.dto.PersonDB;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonDBRepository extends CrudRepository<PersonDB, Long> {

    Optional<PersonDB> findByFirstNameAndLastName(String firstName, String lastName);

}
