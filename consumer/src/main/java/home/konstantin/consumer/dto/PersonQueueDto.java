package home.konstantin.consumer.dto;

import lombok.Data;

@Data
public class PersonQueueDto {

    private String firstName;
    private String lastName;
    private int age;
    private double calculationSeed;

}
