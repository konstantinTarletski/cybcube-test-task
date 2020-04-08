package home.konstantin.consumer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@Setter
@NoArgsConstructor
@RedisHash("persons")
public class PersonRedis {

    @Id
    public String id;
    private double rating;

}
