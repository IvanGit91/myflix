package me.personal.myflix.exception;

import lombok.Getter;
import me.personal.myflix.utility.multilanguage.Multilanguage;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.Serial;

@Getter
public class AppException extends HttpStatusCodeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AppException(HttpStatus statusCode, String message) {
        super(statusCode, message);
    }

    public AppException(HttpStatus statusCode, String message, Object... params) {
        super(statusCode, Multilanguage.getMessage(message, params));
    }
}
