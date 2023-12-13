package com.fullcycle.admin.catalogo;


import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@DataJpaTest
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*MySQLGateway")
})
@ExtendWith(MySQLGatewayTest.CleanUpExtentions.class)
public @interface MySQLGatewayTest {

    class CleanUpExtentions implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) throws Exception {
            final var repostiories = SpringExtension
                    .getApplicationContext(context)
                    .getBeansOfType(CrudRepository.class)
                    .values();

            cleanUp(repostiories);
        }

        private void cleanUp(Collection<CrudRepository> repostiories) {
            repostiories
                    .forEach(CrudRepository::deleteAll);
        }
    }
}
