package home.konstantin.supplier.config;

import home.konstantin.supplier.dto.PersonApiDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@PropertySource("${test-data}")
@ConfigurationProperties(prefix = "test-data")
public class PersonConfiguration {

    private double calculationSeed;

    private List<PersonApiDto> person = new ArrayList<>();

}
