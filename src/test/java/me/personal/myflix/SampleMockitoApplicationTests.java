package me.personal.myflix;


import me.personal.myflix.mockito.Repository;
import me.personal.myflix.mockito.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;
import java.util.List;


@SpringBootTest(classes = MyFlixApplication.class)
@ActiveProfiles({"dev", "integration_test"})
@WebAppConfiguration
class SampleMockitoApplicationTests {

    @Mock
    private Repository repository;

    @InjectMocks
    private Service service;

    @Test
    void testSuccess() {
        // Setup mock scenario
        try {
            Mockito.when(repository.getStuff()).thenReturn(List.of("A", "B", "CDEFGHIJK", "12345", "1234"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Execute the service that uses the mocked repository
        List<String> stuff = service.getStuffWithLengthLessThanFive();

        // Validate the response
        Assertions.assertNotNull(stuff);
        Assertions.assertEquals(3, stuff.size());
    }

    @Test
    void testException() {
        // Setup mock scenario
        try {
            Mockito.when(repository.getStuff()).thenThrow(new SQLException("Connection Exception"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Execute the service that uses the mocked repository
        List<String> stuff = service.getStuffWithLengthLessThanFive();

        // Validate the response
        Assertions.assertNotNull(stuff);
        Assertions.assertEquals(0, stuff.size());
    }
}

