package home.konstantin.consumer.repository;

import home.konstantin.consumer.dto.PersonDbDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonDBRepository extends CrudRepository<PersonDbDto, Long> {

    Optional<PersonDbDto> findByFirstNameAndLastName(String firstName, String lastName);

}
