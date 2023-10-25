package com.toastcatalog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import com.toastcatalog.ItemDetailActivity
import org.hamcrest.core.IsNot.not
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class ItemDetailActivityTest {
    @JvmField
    @Rule
    var itemDetailActivityTestRules: ActivityTestRule<ItemDetailActivity> = ActivityTestRule(
        ItemDetailActivity::class.java
    )

    @Test
    fun testTextViewsAreDisplayedCorrectly() {
        // Check if the TextViews with their respective IDs exist and are displayed
        onView(withId(R.id.tv_item_name_detail))
            .check(matches(isDisplayed()))
            .check(matches(withText("Item Name")))

        onView(withId(R.id.tv_item_price_detail))
            .check(matches(isDisplayed()))
            .check(matches(withText("Price")))

        onView(withId(R.id.tv_item_currency_detail))
            .check(matches(isDisplayed()))
            .check(matches(withText("Currency")))

        onView(withId(R.id.tv_item_last_sold_detail))
            .check(matches(isDisplayed()))
            .check(matches(withText("Last Sold")))
    }

    @Test
    fun testTextViewsDoesNotHaveTextDifferentFromWhatIsExpected() {
        // Check if the TextViews have text that differs from what is expected
        onView(withId(R.id.tv_item_name_detail))
            .check(matches(isDisplayed()))
            .check(matches(not(withText("Incorrect Item Name"))))

        onView(withId(R.id.tv_item_price_detail))
            .check(matches(isDisplayed()))
            .check(matches(not(withText("Incorrect Item Price"))))

        onView(withId(R.id.tv_item_currency_detail))
            .check(matches(isDisplayed()))
            .check(matches(not(withText("Incorrect Item Currency"))))

        onView(withId(R.id.tv_item_last_sold_detail))
            .check(matches(isDisplayed()))
            .check(matches(not(withText("Incorrect Item Last Sold"))))
    }
}