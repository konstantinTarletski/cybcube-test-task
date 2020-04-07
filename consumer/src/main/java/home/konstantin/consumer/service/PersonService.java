package home.konstantin.consumer.service;

import home.konstantin.consumer.dto.PersonQueueDto;
import home.konstantin.consumer.dto.PersonRedisDto;
import home.konstantin.consumer.dto.PersonDbDto;
import home.konstantin.consumer.repository.PersonDBRepository;
import home.konstantin.consumer.repository.PersonRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRedisRepository personRedisRepository;
    private final PersonDBRepository personDBRepository;

    public void processPerson(PersonQueueDto personQueue) {
        log.info("processing person = {}", personQueue);

        var rating = calculateRating(personQueue);

        var dbPerson = personDBRepository.findByFirstNameAndLastName(personQueue.getFirstName(),
            personQueue.getLastName()).orElse(getPersonDBFromPersonQueue(personQueue));
        dbPerson.setHandlingCount(dbPerson.getHandlingCount() + 1);
        dbPerson.setRating(rating);
        personDBRepository.save(dbPerson);

        var redisPerson = personRedisRepository.findById(
            getRedisId(personQueue.getFirstName(), personQueue.getLastName())).
            orElse(getPersonRedisFromPersonQueue(personQueue));
        redisPerson.setRating(rating);
        personRedisRepository.save(redisPerson);

        log.info("{} has {} score", redisPerson.getId(), redisPerson.getRating());
    }

    public List<PersonDbDto> getAllPersonsFromDatabase() {
        return StreamSupport.stream(personDBRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<PersonRedisDto> getAllPersonsFromRedis() {
        return StreamSupport.stream(personRedisRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    protected double calculateRating(PersonQueueDto personQueue) {
        return personQueue.getCalculationSeed() * personQueue.getAge();
    }

    protected String getRedisId(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    private PersonRedisDto getPersonRedisFromPersonQueue(PersonQueueDto input) {
        var personRedis = new PersonRedisDto();
        personRedis.setId(getRedisId(input.getFirstName(), input.getLastName()));
        return personRedis;
    }

    private PersonDbDto getPersonDBFromPersonQueue(PersonQueueDto input) {
        var personDB = new PersonDbDto();
        personDB.setAge(input.getAge());
        personDB.setFirstName(input.getFirstName());
        personDB.setLastName(input.getLastName());
        personDB.setHandlingCount(0);
        return personDB;
    }

}
