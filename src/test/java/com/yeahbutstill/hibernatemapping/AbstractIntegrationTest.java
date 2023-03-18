package com.yeahbutstill.hibernatemapping;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@ActiveProfiles("local")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractIntegrationTest {

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Container
        public static PostgreSQLContainer<?> postgres =
                new PostgreSQLContainer<>(DockerImageName.parse("postgres:14-alpine"));

        @Container
        public static GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:7.0.9-alpine")).withExposedPorts(6379);

        @Container
        public static KafkaContainer kafka =
                new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

        @DynamicPropertySource
        public static Map<String, String> getProperties() {

            Startables.deepStart(Stream.of(redis, kafka, postgres)).join();

            return Map.of(
                    "spring.datasource.url", postgres.getJdbcUrl(),
                    "spring.datasource.username", postgres.getUsername(),
                    "spring.datasource.password", postgres.getPassword(),
                    "spring.redis.host", redis.getHost(),
                    "spring.redis.port", redis.getFirstMappedPort() + "",
                    "spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
        }

        @Override
        public void initialize(ConfigurableApplicationContext context) {

            ConfigurableEnvironment env = context.getEnvironment();
            env.getPropertySources()
                    .addFirst(new MapPropertySource("testcontainers", (Map) getProperties()));
        }
    }
}
