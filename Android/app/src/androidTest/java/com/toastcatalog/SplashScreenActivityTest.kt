package com.toastcatalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasTextColor
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.toastcatalog.SplashScreenActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashScreenActivityTest {
    @JvmField
    @Rule
    var splashScreenActivityTestRules: ActivityTestRule<SplashScreenActivity> = ActivityTestRule(
        SplashScreenActivity::class.java
    )

    @Test
    fun testTextIsDisplayedWithCorrectTextAndBackgroundColor() {
        // Check that the "Red Zone" Button is displayed with the correct text and background color
        onView(withId(R.id.textView))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.appTitle)))
            .check(matches(hasTextColor(R.color.white)))
    }

    @Test
    fun testSplashScreenHasCorrectBackgroundColor() {
        val bar = splashScreenActivityTestRules.activity.findViewById<View>(R.id.splashScreen)
        val actualColor = (bar.background as ColorDrawable).color
        val expectedColor = Color.parseColor("#FF018786")
        TestCase.assertEquals(actualColor, expectedColor)
    }
}