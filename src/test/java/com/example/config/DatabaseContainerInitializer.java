package com.example.config;

import com.github.dockerjava.api.model.RestartPolicy;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MariaDBContainer;

public class DatabaseContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String TESTCONTAINERS_DISABLE_PROPERTY = "testcontainers.disable";

    private static MariaDBContainer mySQLContainer = new MariaDBContainer<>("mariadb:10.5")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("demo")
            .withReuse(true)
            .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withRestartPolicy(RestartPolicy.alwaysRestart()))
            .withLabel("group", "demo-db");

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        if (isTestcontainersDisabled(configurableApplicationContext)) {
            return;
        }

        mySQLContainer.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                "spring.datasource.password=" + mySQLContainer.getPassword(),
                "spring.datasource.username=" + mySQLContainer.getUsername()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }

    private boolean isTestcontainersDisabled(ConfigurableApplicationContext configurableApplicationContext) {
        Boolean testcontainersDiabled = configurableApplicationContext.getEnvironment().getProperty(TESTCONTAINERS_DISABLE_PROPERTY, Boolean.class);
        return Boolean.TRUE.equals(testcontainersDiabled);
    }
}
