package home.konstantin.supplier.config;

import home.konstantin.supplier.dto.PersonApiDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "test-data")
@PropertySource("${test-data}")
@Data
public class PersonConfiguration {

    private double calculationSeed;

    private List<PersonApiDto> person = new ArrayList<>();

}
