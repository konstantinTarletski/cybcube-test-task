package home.konstantin.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonQueueDto {

    private String firstName;
    private String lastName;
    private double calculationSeed;
    private int age;

}
