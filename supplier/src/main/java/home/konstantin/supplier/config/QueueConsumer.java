package home.konstantin.supplier.config;

import home.konstantin.supplier.dto.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Configuration
public class QueueConsumer {

    @Autowired
    private PersonConfiguration personConfiguration;

    private int randomNum;

    @Bean
    public void scheduler() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                QueueConsumer.this.randomNum = current().nextInt(0, personConfiguration.getPerson().size());
                log.info("Sending item with index = {}", QueueConsumer.this.randomNum);
                QueueConsumer.this.supplier();
            }
        }, 0, 1000);
    }

    @Bean
    public Supplier<Person> supplier() {
        return () -> personConfiguration.getPerson().get(randomNum);
    }

}
