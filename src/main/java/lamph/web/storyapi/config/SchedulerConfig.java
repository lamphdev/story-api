package lamph.web.storyapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfig {

    @Bean
    public Scheduler scheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(20));
    }

}
