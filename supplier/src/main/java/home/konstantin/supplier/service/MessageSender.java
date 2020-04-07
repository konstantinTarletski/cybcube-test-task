package home.konstantin.supplier.service;

import home.konstantin.supplier.config.PersonConfiguration;
import home.konstantin.supplier.dto.PersonApiDto;
import home.konstantin.supplier.dto.PersonQueueDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(Source.class)
@RequiredArgsConstructor
public class MessageSender {

    private final Source messageSource;
    private final PersonConfiguration personConfiguration;

    public void sendPersonToQueue(PersonApiDto personApi) {
        log.info("Sending message {} to {} channel", personApi, messageSource.OUTPUT);
        messageSource.output().send(MessageBuilder.withPayload(getPersonQueueDtoFromPersonApiDto(personApi)).build());
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
