package com.teamboring.ts_note.common

import org.junit.Test
import java.time.Instant
import java.util.Date

class UtilTest {
    @Test
    fun getCurrentTimeTextTestInKorea() {
        val date = Date.from(Instant.parse("2024-03-04T00:00:00.00Z"))

        val actual = getCurrentTimeText(date)

        assert(actual == "2024-03-04 09:00:00") // UTC+9 in Korea
    }
}