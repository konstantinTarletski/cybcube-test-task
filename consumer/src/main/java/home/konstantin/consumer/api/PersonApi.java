package home.konstantin.consumer.api;

import home.konstantin.consumer.dto.PersonDbDto;
import home.konstantin.consumer.dto.PersonRedisDto;
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

    @GetMapping(path = "/get-db-person-list")
    public List<PersonDbDto> publishMessage() {
        return personService.getAllPersonsFromDatabase();
    }

    @GetMapping(path = "/get-redis-person-list")
    public List<PersonRedisDto> enableSender() {
        return personService.getAllPersonsFromRedis();
    }

}
