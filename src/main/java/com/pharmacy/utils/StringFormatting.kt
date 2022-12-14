package com.pharmacy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormatting {

    public Date stringToDate(String date) {
        Date dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("MMM dd, yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat;
    }
}
