package com.teamboring.ts_note.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentTimeText(date: Date = Date()): String {
    val dateFormatWithZone = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormatWithZone.format(date)
}