package com.ghb.coltpath;

import org.springframework.data.auditing.DateTimeProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Ghebo on 1/20/2016.
 */
public class AuditingDateTimeProvider implements DateTimeProvider {


    @Override
    public Calendar getNow() {
        return Calendar.getInstance();
    }
}
