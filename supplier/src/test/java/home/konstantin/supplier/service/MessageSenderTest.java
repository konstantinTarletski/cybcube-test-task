package home.konstantin.supplier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import home.konstantin.supplier.config.PersonConfiguration;
import home.konstantin.supplier.dto.PersonApiDto;
import home.konstantin.supplier.dto.PersonQueueDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {
    "--spring.cloud.stream.bindings.input.contentType=application/json",
    "--spring.cloud.stream.bindings.output.contentType=application/json"})
@DirtiesContext
public class MessageSenderTest {

    @Autowired
    private Source channels;

    @Autowired
    private MessageCollector collector;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private PersonConfiguration personConfiguration;

    @Test
    public void testMessages() throws JsonProcessingException {
        messageSender.sendPersonToQueue(getTestPersonApiDto());

        Message<String> received = (Message<String>) this.collector.forChannel(channels.output()).poll();
        PersonQueueDto personFromQueue = new ObjectMapper().readValue(received.getPayload(), PersonQueueDto.class);

        assertTrue(personFromQueue instanceof PersonQueueDto);

        var testPerson = getTestPersonQueueDto();

        assertTrue(personFromQueue.getAge() == testPerson.getAge());
        assertEquals(personFromQueue.getFirstName(), testPerson.getFirstName());
        assertEquals(personFromQueue.getLastName(), testPerson.getLastName());
        assertTrue(personFromQueue.getCalculationSeed() == personConfiguration.getCalculationSeed());
    }

    private PersonApiDto getTestPersonApiDto() {
        var person = new PersonApiDto();
        person.setAge(10);
        person.setFirstName("ABC");
        person.setLastName("123");
        return person;
    }

    private PersonQueueDto getTestPersonQueueDto() {
        var person = new PersonQueueDto();
        person.setAge(10);
        person.setFirstName("ABC");
        person.setLastName("123");
        person.setCalculationSeed(personConfiguration.getCalculationSeed());
        return person;
    }

}
