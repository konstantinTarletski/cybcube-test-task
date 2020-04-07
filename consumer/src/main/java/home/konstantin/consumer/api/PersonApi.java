package home.konstantin.consumer.api;

import home.konstantin.consumer.dto.PersonDB;
import home.konstantin.consumer.dto.PersonRedis;
import home.konstantin.consumer.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/consumer")
@RequiredArgsConstructor
public class PersonApi {

    private final PersonService personService;

    @GetMapping(path="/get-db-person-list")
    public List<PersonDB> publishMessage() {
        return personService.getAllPersonsFromDatabase();
    }

    @GetMapping(path="/get-redis-person-list")
    public List<PersonRedis> enableSender() {
        return personService.getAllPersonsFromRedis();
    }

}
