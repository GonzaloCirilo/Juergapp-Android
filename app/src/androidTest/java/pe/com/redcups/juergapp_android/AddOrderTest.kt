package pe.com.redcups.juergapp_android

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule

import org.junit.Rule
import org.junit.Test
import pe.com.redcups.juergapp_android.fragment.EventAddFragment
import pe.com.redcups.juergapp_android.adapter.EventAdapter


class AddOrderTest{

   @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()



    @Test
    fun changeText_sameActivity() {


        Espresso.onView(ViewMatchers.withId(R.id.events_dest)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.event_add_button)).perform(ViewActions.click())

        // Delete whatevers on the test
        Espresso.onView(ViewMatchers.withId(R.id.event_name_text_edit)).perform(ViewActions.clearText())

        // Type text and then press the button.
        Espresso.onView(ViewMatchers.withId(R.id.event_name_text_edit))
            .perform(ViewActions.typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard())


        Espresso.onView(ViewMatchers.withId(R.id.add_event_button)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_event))
            //.check(ViewAssertions.matches(ViewMatchers.withText()))
        // Check that the text was changed.
        onView(withText(STRING_TO_BE_TYPED)).check(matches(isDisplayed()))
    }

    @Test
    fun changeText_newActivity() {
        // Type text and then press the button.
        }


    companion object {

        val STRING_TO_BE_TYPED = "Espresso view"
    }
}