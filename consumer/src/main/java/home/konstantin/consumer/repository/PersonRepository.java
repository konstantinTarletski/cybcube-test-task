package home.konstantin.consumer.repository;

import home.konstantin.consumer.dto.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

}
