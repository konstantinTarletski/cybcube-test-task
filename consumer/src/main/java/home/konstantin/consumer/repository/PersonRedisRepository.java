package home.konstantin.consumer.repository;

import home.konstantin.consumer.dto.PersonRedisDto;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<PersonRedisDto, String> {

}
