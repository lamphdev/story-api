package lamph.web.storyapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class StoryApiApplication {

    public static void main(String[] args) {
        String profileEnv = System.getenv("SPRING_PROFILE");
        if (StringUtils.isEmpty(profileEnv))
            profileEnv = "dev";

        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles(profileEnv.split(","));

        SpringApplication application = new SpringApplication(StoryApiApplication.class);
        application.setEnvironment(environment);
        application.run(args);
    }

}
