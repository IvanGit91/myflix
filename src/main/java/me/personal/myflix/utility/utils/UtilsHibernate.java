package me.personal.myflix.utility.utils;

import org.hibernate.Hibernate;

public class UtilsHibernate {
    private UtilsHibernate() {
        throw new IllegalStateException("UtilsHibernate utility class");
    }

    @SuppressWarnings("unchecked")
    public static <T> T unProxy(T entity) {
        return (T) Hibernate.unproxy(entity);
    }
}
