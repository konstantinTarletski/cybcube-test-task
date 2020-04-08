package home.konstantin.supplier.scheduler;

import home.konstantin.supplier.config.PersonConfiguration;
import home.konstantin.supplier.service.MessageSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerSender {

    private final PersonConfiguration personConfiguration;
    private final MessageSender messageSender;

    @Getter
    @Setter
    private volatile boolean enabled = false;

    @Scheduled(fixedDelayString = "${scheduler-period}", initialDelay = 1000)
    public void scheduler() {
        log.info("Entering scheduler, enabled = {}", enabled);
        if (enabled) {
            var randomNum = current().nextInt(0, personConfiguration.getPerson().size());
            var person = personConfiguration.getPerson().get(randomNum);
            messageSender.sendPersonToQueue(person);
            log.info("Scheduler sent person = {}", person);
        }
    }

}
