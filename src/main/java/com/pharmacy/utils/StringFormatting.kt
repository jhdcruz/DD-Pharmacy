package com.pharmacy.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class StringFormatting {
    fun stringToDate(date: String?): Date? {
        var dateFormat: Date? = null

        try {
            dateFormat = SimpleDateFormat("MMM dd, yyyy").parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateFormat
    }
}
