package home.konstantin.supplier.service;

import home.konstantin.supplier.config.PersonConfiguration;
import home.konstantin.supplier.dto.PersonApiDto;
import home.konstantin.supplier.dto.PersonQueueDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@Service
public class SupplierSender implements Supplier<PersonQueueDto> {

    @Autowired
    private PersonConfiguration personConfiguration;

    @Getter
    @Setter
    private volatile PersonApiDto person;

    @Bean
    public Supplier<PersonQueueDto> supplier() {
        return this;
    }

    @Override
    public PersonQueueDto get() {
        //var personQueue = getPersonQueueDtoFromPersonApiDto(person);
        log.info("Sending person  = {}", person);
        return null;//personQueue;
    }

    private PersonQueueDto getPersonQueueDtoFromPersonApiDto(PersonApiDto personApi){
        var personQueue = new PersonQueueDto();
        personQueue.setAge(personApi.getAge());
        personQueue.setFirstName(personApi.getFirstName());
        personQueue.setLastName(personApi.getLastName());
        personQueue.setCalculationSeed(personConfiguration.getCalculationSeed());
        return personQueue;
    }

}
