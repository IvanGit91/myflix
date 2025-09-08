package me.personal.myflix.mockito;

import java.sql.SQLException;
import java.util.List;

public class Repository {
    public List<String> getStuff() throws SQLException {
        // Execute Query

        // Return results
        return List.of("One", "Two", "Three");
    }
}