package me.personal.myflix;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsIterableContaining.hasItem;


@SpringBootTest(classes = MyFlixApplication.class)
@ActiveProfiles({"dev", "integration_test"})
@WebAppConfiguration
@Log4j2
@Tag("Development")
// mvn clean test -Dgroups="Development"
// mvn clean test -Dgroups="Development, Production"
// mvn clean test -DexcludedGroups="Production"
class SampleHamcrestApplicationTests {

    @Test
    @DisplayName("String Examples")
    void stringExamples() {
        String s1 = "Hello";
        String s2 = "Hello";

        assertThat("Comparing Strings", s1, is(s2));
        assertThat(s1, equalTo(s2));
        assertThat(s1, sameInstance(s2));
        assertThat("ABCDE", containsString("BC"));
        assertThat("ABCDE", not(containsString("EF")));
    }

    @Test
    @DisplayName("List Examples")
    void listExamples() {
        // Create an empty list
        List<String> list = new ArrayList<>();
        assertThat(list, isA(List.class));
        assertThat(list, empty());

        // Add a couple items
        list.add("One");
        list.add("Two");
        assertThat(list, not(empty()));
        assertThat(list, hasSize(2));
        assertThat(list, contains("One", "Two"));
        assertThat(list, containsInAnyOrder("Two", "One"));
        assertThat(list, hasItem("Two"));
    }

    @Test
    @DisplayName("Number Examples")
    void numberExamples() {
        assertThat(5, lessThan(10));
        assertThat(5, lessThanOrEqualTo(5));
        assertThat(5.01, closeTo(5.0, 0.01));
        assertThat("ciao", matchesPattern("[a-z]+"));
    }

}

