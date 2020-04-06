package home.konstantin.consumer.dto;

import lombok.Data;
import javax.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("persons")
public class PersonRedis {

    @Id
    public String id;
    private double rating;

}
