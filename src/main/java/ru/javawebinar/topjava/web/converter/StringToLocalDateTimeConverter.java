package ru.javawebinar.topjava.web.converter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;

/**
 * GKislin
 * 15.04.2015.
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String param) {
        return TimeUtil.toDateTime(param);
    }
}
