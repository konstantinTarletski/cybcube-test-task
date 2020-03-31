package home.konstantin.supplier.config;

import home.konstantin.supplier.dto.Person;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "test-data")
@Data
public class PersonConfiguration {

    private List<Person> person = new ArrayList<>();

}
