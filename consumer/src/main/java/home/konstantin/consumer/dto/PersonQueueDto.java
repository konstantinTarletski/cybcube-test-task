package home.konstantin.consumer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class PersonQueueDto {

    private String firstName;
    private String lastName;
    private int age;
    private double calculationSeed;

}
