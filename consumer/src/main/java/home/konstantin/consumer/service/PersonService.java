package home.konstantin.consumer.service;

import home.konstantin.consumer.dto.PersonRedis;
import home.konstantin.consumer.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void processPerson(PersonRedis personRedis) {
        log.info("processing person = {}", personRedis);
        var id = personRedis.getFirstName() + " " + personRedis.getLastName();
        var handlingCount = personRepository.findById(id).map(x -> x.getHandlingCount()).orElse(0);
        personRedis.setHandlingCount(++handlingCount);
        personRedis.setId(id);
        personRepository.save(personRedis);
    }

}
