package home.konstantin.supplier.scheduler;

import home.konstantin.supplier.config.PersonConfiguration;
import home.konstantin.supplier.service.MessageSender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerSender {

    @Value("${scheduler-period}")
    private int schedulerPeriod;

    private final PersonConfiguration personConfiguration;
    private final MessageSender messageSender;

    @Getter
    @Setter
    private volatile boolean enabled = false;

    @Bean
    public void scheduler() {
        var timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                log.info("Entering scheduler, enabled = {}", enabled);
                if (enabled) {
                    var randomNum = current().nextInt(0, personConfiguration.getPerson().size());
                    var person = personConfiguration.getPerson().get(randomNum);
                    messageSender.sendPersonToQueue(person);
                    log.info("Scheduler sent person = {}", person);
                }
            }
        }, 0, schedulerPeriod);
    }

}
