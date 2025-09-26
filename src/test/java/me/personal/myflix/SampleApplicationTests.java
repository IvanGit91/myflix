package me.personal.myflix;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


@SpringBootTest(classes = MyFlixApplication.class)
@ActiveProfiles({"dev", "integration_test"})
@WebAppConfiguration
@TestPropertySource(properties = "init.enabled=false")
@Log4j2
class SampleApplicationTests {

    /* OPERATIONS  */
    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @AfterAll
    static void done() {
        log.info("@AfterAll - executed after all test methods.");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }

    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        log.info("Success");
    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {
    }

    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executed after each test method.");
    }

    /* ASSERTIONS  */
    @Test
    void lambdaExpressions() {
        assertTrue(Stream.of(1, 2, 3)
                .mapToInt(i -> i)
                .sum() > 5, () -> "Sum should be greater than 5");
    }

    // It is also now possible to group assertions with assertAll() which will report any failed assertions within the group with a MultipleFailuresError:
    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        assertAll("numbers",
                () -> assertEquals(0, numbers[0]),
                () -> assertEquals(3, numbers[3]),
                () -> assertEquals(4, numbers[4])
        );
    }

    /* ASSUMPTIONS
     *  Assumptions are used to run tests only if certain conditions are met.
     *  This is typically used for external conditions that are required for the test to run properly, but which are not directly related to whatever is being tested. */
    @Test
    void trueAssumption() {
        assumeTrue(5 > 1);
        assertEquals(7, 5 + 2);
    }

    @Test
    void falseAssumption() {
        Assumptions.assumeFalse(5 < 1);
        assertEquals(7, 5 + 2);
    }

    @Test
    void assumptionThat() {
        String someString = "Just a string";
        assumingThat(
                someString.equals("Just a string"),
                () -> assertEquals(4, 2 + 2)
        );
    }

    /* Exception Testing  */
    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals("Not supported", exception.getMessage());
    }

    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Integer.valueOf(str);
        });
    }

    /* DYNAMIC TESTS */
    @TestFactory
    Stream<DynamicTest> translateDynamicTestsFromStream() {
        List<String> in = List.of("uno", "due", "tre"), out = List.of("uno", "due", "tre");
        return in.stream()
                .map(word ->
                        DynamicTest.dynamicTest("Test translate " + word, () -> {
                            int id = in.indexOf(word);
                            assertEquals(out.get(id), word);
                        })
                );
    }

}

