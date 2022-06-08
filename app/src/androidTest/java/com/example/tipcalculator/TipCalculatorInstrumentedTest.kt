package com.example.tipcalculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class TipCalculatorInstrumentedTest {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        calculate_percent_tip("50", R.id.rbChoice1, R.id.rbChoice2, R.id.rbChoice3, "$10.00")

    }

    @Test
    fun calculate_18_percent_tip() {
        calculate_percent_tip("100", R.id.rbChoice2, R.id.rbChoice1, R.id.rbChoice3, "$18.00")

    }

    @Test
    fun calculate_15_percent_tip() {
        calculate_percent_tip("100", R.id.rbChoice3, R.id.rbChoice1, R.id.rbChoice2, "$15.00")
    }

    @Test
    fun calculate_20_percent_with_round_up_unchecked(){
        onView(withId(R.id.etServiceCost))
            .perform(typeText("108.99"))

        onView(withId(R.id.switcher))
            .perform(click())

        onView(withId(R.id.btnCalculate))
            .perform(click())
            .perform(closeSoftKeyboard())

        onView(withId(R.id.tvTipAmount))
            .check(matches(withText(containsString("$21.80"))))
    }


    private fun calculate_percent_tip(
        typeToEt: String,
        rbIdChecked: Int,
        rbIdNotChecked1: Int,
        rbIdNotChecked2: Int,
        expected: String
    ) {
        onView(withId(R.id.etServiceCost))
            .perform(typeText(typeToEt))
            .perform(closeSoftKeyboard())

        onView(withId(rbIdChecked))
            .perform(click())

        onView(withId(R.id.btnCalculate))
            .perform(click())

        onView(withId(rbIdNotChecked1))
            .check(matches(isNotChecked()))

        onView(withId(rbIdNotChecked2))
            .check(matches(isNotChecked()))

        onView(withId(R.id.tvTipAmount))
            .check(matches(withText(containsString(expected))))
    }

//    @Test
//    fun useAppContext() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.tipcalculator", appContext.packageName)
//    }
}