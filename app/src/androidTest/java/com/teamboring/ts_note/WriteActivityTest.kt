package com.teamboring.ts_note

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.teamboring.ts_note.feature.write.WriteActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WriteActivityTest {
    @get:Rule
    val writeActivityRule = ActivityScenarioRule(WriteActivity::class.java)

    @Test
    fun 제목과_내용을_입력하기_전에_문구확인() {
        onView(withId(com.teamboring.ts_note.feature.write.R.id.title_view)).check(
            matches(
                withHint(
                    com.teamboring.ts_note.feature.write.R.string.input_title_hint
                )
            )
        )
        onView(withId(com.teamboring.ts_note.feature.write.R.id.content_text)).check(
            matches(
                withHint(
                    com.teamboring.ts_note.common.R.string.input_content_hint
                )
            )
        )
    }
}