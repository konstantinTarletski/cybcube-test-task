package home.konstantin.supplier.service;

import home.konstantin.supplier.config.PersonConfiguration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

import static java.util.concurrent.ThreadLocalRandom.current;

@Slf4j
@Service
public class SchedulerSender {

    @Autowired
    private PersonConfiguration personConfiguration;

    @Autowired
    private SupplierSender supplierSender;

    @Getter
    @Setter
    private boolean enabled = false;

    @Bean
    public void scheduler() {
        var timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (enabled){
                    log.info("Scheduler executed for data sending");
                    var randomNum = current().nextInt(0, personConfiguration.getPerson().size());
                    var person = personConfiguration.getPerson().get(randomNum);
                    supplierSender.setPerson(person);
                }
            }
        }, 0, 1000);
    }



}
