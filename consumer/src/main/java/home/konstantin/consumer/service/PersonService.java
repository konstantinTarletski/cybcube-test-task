package home.konstantin.consumer.service;

import home.konstantin.consumer.dto.PersonQueueDto;
import home.konstantin.consumer.model.Person;
import home.konstantin.consumer.model.PersonRedis;
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

    private final int INITAL_HANDLING_COUNT = 0;

    private final PersonRedisRepository personRedisRepository;
    private final PersonDBRepository personDBRepository;

    public void processPerson(PersonQueueDto personQueue) {
        log.info("processing person = {}", personQueue);
        var rating = calculateRating(personQueue);
        processDb(personQueue, rating);
        var redisPerson = processRedis(personQueue, rating);
        log.info("{} has {} score", redisPerson.getId(), redisPerson.getRating());
    }

    protected void processDb(PersonQueueDto personQueue, double rating) {
        var dbPerson = personDBRepository.findByFirstNameAndLastName(personQueue.getFirstName(),
            personQueue.getLastName()).orElse(getPersonDBFromPersonQueue(personQueue));
        dbPerson.setHandlingCount(dbPerson.getHandlingCount() + 1);
        dbPerson.setRating(rating);
        personDBRepository.save(dbPerson);
    }

    protected PersonRedis processRedis(PersonQueueDto personQueue, double rating) {
        var redisPerson = personRedisRepository.findById(
            getRedisId(personQueue.getFirstName(), personQueue.getLastName())).
            orElse(getPersonRedisFromPersonQueue(personQueue));
        redisPerson.setRating(rating);
        personRedisRepository.save(redisPerson);
        return redisPerson;
    }

    public List<Person> getAllPersonsFromDatabase() {
        return StreamSupport.stream(personDBRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<PersonRedis> getAllPersonsFromRedis() {
        return StreamSupport.stream(personRedisRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    protected double calculateRating(PersonQueueDto personQueue) {
        return personQueue.getCalculationSeed() * personQueue.getAge();
    }

    protected String getRedisId(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    private PersonRedis getPersonRedisFromPersonQueue(PersonQueueDto input) {
        return PersonRedis.builder()
            .id(getRedisId(input.getFirstName(), input.getLastName())).build();
    }

    private Person getPersonDBFromPersonQueue(PersonQueueDto input) {
        return Person.builder()
            .age(input.getAge())
            .firstName(input.getFirstName())
            .lastName(input.getLastName())
            .handlingCount(INITAL_HANDLING_COUNT).build();
    }

}
