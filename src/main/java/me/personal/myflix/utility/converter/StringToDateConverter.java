package me.personal.myflix.utility.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String from) {
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        Date pDate = null;
        try {
            pDate = df.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pDate;
    }
}
