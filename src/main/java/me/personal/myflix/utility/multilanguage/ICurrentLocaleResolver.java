package me.personal.myflix.utility.multilanguage;

import java.util.Locale;
import java.util.Optional;

public interface ICurrentLocaleResolver {
    Optional<Locale> getCurrentLocale();
}
