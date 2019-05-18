package converter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;


public class DateTimeConverter implements Converter<String, DateTime> {
    @Override
    public DateTime convert(String string) {
        if(string == null || string.isEmpty())
            return null;

        String datePlusTime = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormat.forPattern(datePlusTime);

        if(string.length() == datePlusTime.length())
            return DateTime.parse(string, formatter);

        return new DateTime(string);
    }
}
