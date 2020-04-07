package home.konstantin.supplier.api;

import home.konstantin.supplier.dto.PersonApiDto;
import home.konstantin.supplier.scheduler.SchedulerSender;
import home.konstantin.supplier.service.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("api/supplier")
@RequiredArgsConstructor
public class PersonApi {

    private final SchedulerSender schedulerSender;
    private final MessageSender messageSender;

    @PostMapping(path="/sent-person-to-queue")
    public String publishMessage(@Valid @RequestBody PersonApiDto assetDto) {
        messageSender.sendPersonToQueue(assetDto);
        String logText = format("Person = %s was sent to queue", assetDto);
        log.info(logText);
        return logText;
    }

    @GetMapping(path="/enable-scheduler")
    public String enableSender(@RequestParam boolean enabled) {
        schedulerSender.setEnabled(enabled);
        String logText = format("Scheduler status now = %s", schedulerSender.isEnabled());
        log.info(logText);
        return logText;
    }

}
