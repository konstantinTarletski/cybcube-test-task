package home.konstantin.consumer.repository;

import home.konstantin.consumer.model.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonDBRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

}
