package home.konstantin.consumer.listener;

import home.konstantin.consumer.dto.PersonQueueDto;
import home.konstantin.consumer.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class QueueConsumer {

    private final PersonService personService;

    @Bean
    public Consumer<PersonQueueDto> consumer() {
        return value -> {
            log.info("get item from queue = {}", value);
            personService.processPerson(value);
        };
    }

}
