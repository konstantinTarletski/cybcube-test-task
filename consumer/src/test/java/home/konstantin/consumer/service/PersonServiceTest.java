package home.konstantin.consumer.service;

import home.konstantin.consumer.model.Person;
import home.konstantin.consumer.dto.PersonQueueDto;
import home.konstantin.consumer.model.PersonRedis;
import home.konstantin.consumer.repository.PersonDBRepository;
import home.konstantin.consumer.repository.PersonRedisRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    @Mock
    private PersonRedisRepository personRedisRepository;

    @Mock
    private PersonDBRepository personDBRepository;

    @InjectMocks
    private PersonService personService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void successfulProcessPerson() {
        var personQueue = getTestPersonQueueDto();
        var personDb = getTestPersonDbDto();
        var personRedis = getTestPersonRedisDto();

        when(personDBRepository.findByFirstNameAndLastName(personQueue.getFirstName(),
            personQueue.getLastName())).thenReturn(Optional.of(personDb));

        when(personRedisRepository.findById(personQueue.getFirstName() +
            personQueue.getLastName())).thenReturn(Optional.of(personRedis));

        personService.processPerson(personQueue);

        verify(personDBRepository, times(1)).save(personDb);
        verify(personRedisRepository, times(1)).save(personRedis);
    }

    @Test
    public void successfulGetAllPersonsFromDatabase() {
        personService.getAllPersonsFromDatabase();
        verify(personDBRepository, times(1)).findAll();
    }

    @Test
    public void successfulGetAllPersonsFromRedis() {
        personService.getAllPersonsFromRedis();
        verify(personRedisRepository, times(1)).findAll();
    }

    @Test
    public void testCalculateRating() {
        var personQueue = getTestPersonQueueDto();
        var rating = personService.calculateRating(getTestPersonQueueDto());
        assertTrue(rating == personQueue.getCalculationSeed() * personQueue.getAge());
    }

    @Test
    public void testGetRedisId() {
        var id = personService.getRedisId("ABC", "123");
        assertEquals(id, "ABC 123");
    }

    private PersonRedis getTestPersonRedisDto(){
        var person = new PersonRedis();
        person.setId("ABC 123");
        person.setRating(5);
        return person;
    }

    private PersonQueueDto getTestPersonQueueDto(){
        var person = new PersonQueueDto();
        person.setAge(10);
        person.setFirstName("ABC");
        person.setLastName("123");
        person.setCalculationSeed(0.5);
        return person;
    }

    private Person getTestPersonDbDto(){
        var person = new Person();
        person.setAge(10);
        person.setFirstName("ABC");
        person.setLastName("123");
        person.setHandlingCount(1);
        person.setRating(10);
        return person;
    }

}
