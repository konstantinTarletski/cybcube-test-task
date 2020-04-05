package home.konstantin.consumer.listener;

import home.konstantin.consumer.dto.PersonQueue;
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
    public Consumer<PersonQueue> consumer() {
        return value -> {
            log.info("get item = {}", value);
            personService.processPerson(value);
        };
    }

}
