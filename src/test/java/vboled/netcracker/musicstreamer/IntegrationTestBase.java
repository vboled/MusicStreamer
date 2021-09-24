package vboled.netcracker.musicstreamer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import vboled.netcracker.musicstreamer.initializer.Postgres;

import javax.transaction.Transactional;

@SpringBootTest
@Sql("/database/initDBTest.sql")
@Sql("/database/populateDBTest.sql")
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
@Transactional
public abstract class IntegrationTestBase {

    @BeforeAll
    static void init() {
        Postgres.container.start();
    }
}
