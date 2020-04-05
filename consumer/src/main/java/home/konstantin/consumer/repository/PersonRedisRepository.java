package home.konstantin.consumer.repository;

import home.konstantin.consumer.dto.PersonRedis;
import org.springframework.data.repository.CrudRepository;

public interface PersonRedisRepository extends CrudRepository<PersonRedis, String> {

}
