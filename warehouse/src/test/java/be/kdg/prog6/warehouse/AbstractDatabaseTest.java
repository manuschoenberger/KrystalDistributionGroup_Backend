package be.kdg.prog6.warehouse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractDatabaseTest.DataSourceInitializer.class)
@ActiveProfiles("test")
public abstract class AbstractDatabaseTest {
    private static final MySQLContainer<?> DATABASE;

    static {
        DATABASE = new MySQLContainer<>("mysql:9.0.1")
                .withUsername("root")
                .withPassword("")
                .withPrivilegedMode(true)
                .withInitScript("initScript.sql");
        DATABASE.start();
    }

    @Test
    void containerIsRunning() {
        assertTrue(DATABASE.isCreated());
        assertTrue(DATABASE.isRunning());
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + DATABASE.getJdbcUrl(),
                    "spring.datasource.username=" + DATABASE.getUsername(),
                    "spring.datasource.password=" + DATABASE.getPassword(),
                    "spring.sql.init.mode=always",
                    "spring.jpa.generate-ddl=true",
                    "spring.rabbitmq.listener.simple.auto-startup=false"
            );
        }
    }
}

