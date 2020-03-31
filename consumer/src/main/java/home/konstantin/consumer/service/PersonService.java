package home.konstantin.consumer.service;

import home.konstantin.consumer.dto.Person;
import home.konstantin.consumer.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void processPerson(Person person) {
        log.info("processing person = {}", person);
        personRepository.save(person);
    }

}
