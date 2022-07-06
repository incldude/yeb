package com.xxxx.server.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Mr.Z
 * @title: DateConverter
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/1916:13
 */
@SuppressWarnings({"all"})
public class DateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String sourse) {

        try {
            return LocalDate.parse(sourse, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
