package home.konstantin.consumer.service;

import home.konstantin.consumer.dto.PersonQueue;
import home.konstantin.consumer.dto.PersonRedis;
import home.konstantin.consumer.dto.PersonDB;
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

    public void processPerson(PersonQueue personQueue) {
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

    public List<PersonDB> getAllPersonsFromDatabase(){
        return StreamSupport.stream(personDBRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public List<PersonRedis> getAllPersonsFromRedis(){
        return StreamSupport.stream(personRedisRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    private double calculateRating(PersonQueue personQueue){
        return personQueue.getCalculationSeed() * personQueue.getAge();
    }

    private String getRedisId(String firstName, String lastName){
        return firstName + " " + lastName;
    }

    private PersonRedis getPersonRedisFromPersonQueue(PersonQueue input){
        var personRedis = new PersonRedis();
        personRedis.setId(getRedisId(input.getFirstName(), input.getLastName()));
        return personRedis;
    }

    private PersonDB getPersonDBFromPersonQueue(PersonQueue input){
        var personDB = new PersonDB();
        personDB.setAge(input.getAge());
        personDB.setFirstName(input.getFirstName());
        personDB.setLastName(input.getLastName());
        personDB.setHandlingCount(0);
        return personDB;
    }

}
