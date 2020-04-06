package home.konstantin.consumer.dto;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("persons")
public class PersonQueue {

    private String firstName;
    private String lastName;
    private int age;
    private double calculationSeed;

}
