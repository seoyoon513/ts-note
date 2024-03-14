package com.teamboring.ts_note

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.teamboring.ts_note.feature.main.MainActivity
import com.teamboring.ts_note.feature.main.R
import com.teamboring.ts_note.feature.write.WriteActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val mainActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun 더하기_버튼을_누르면_WriteActivity로_화면이_이동된다() {
        onView(withId(R.id.add_button)).perform(click())
        intended(hasComponent(WriteActivity::class.java.name))
    }

    @Test
    fun 저장된_노트가_없으면_없다는_문구를_띄운다() {
        onView(withId(R.id.empty_image_view)).check(matches(isDisplayed()))
        onView(withId(R.id.empty_text_view)).check(matches(isDisplayed()))

        val emptyText = InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(R.string.empty_notes)
        onView(withId(R.id.empty_text_view)).check { view, _ ->
            assertEquals((view as TextView).text, emptyText)
        }
    }
}