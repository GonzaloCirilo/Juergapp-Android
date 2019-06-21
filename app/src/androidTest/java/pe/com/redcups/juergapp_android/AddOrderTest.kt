package pe.com.redcups.juergapp_android

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
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
import pe.com.redcups.juergapp_android.adapter.ProductCategoryAdapter
import pe.com.redcups.juergapp_android.adapter.ProductListAdapter
import java.util.*


class AddOrderTest{

   @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()



    @Test
    fun addEvent() {


        // NAVIGATE - Setup
        onView(withId(R.id.events_dest)).perform(ViewActions.click())
        onView(withId(R.id.event_add_button)).perform(ViewActions.click())

        // NAME

        onView(withId(R.id.event_name_text_edit)).perform(
            ViewActions.clearText(),
            ViewActions.typeText(NAME_TO_BE_TYPED),
            ViewActions.closeSoftKeyboard())

        // DESCRIPTION

        onView(withId(R.id.event_description_text_edit)).perform(
            ViewActions.clearText(),
            ViewActions.typeText(DESCRIPTION_TO_BE_TYPED),
            ViewActions.closeSoftKeyboard())

        // ADDRESS
        onView(withId(R.id.event_address_text_edit)).perform(
            ViewActions.clearText(),
            ViewActions.typeText(ADDRESS_TO_BE_TYPED),
            ViewActions.closeSoftKeyboard())

        // Save new Event

        onView(withId(R.id.add_event_button)).perform(ViewActions.click())

        // TODO: Change this to actual find
        for (i in 1..15){
            onView(withId(R.id.recycler_view_event)).perform(ViewActions.swipeUp())
        }

        onView(withText(NAME_TO_BE_TYPED)).check(matches(isDisplayed()))
        onView(withText(DESCRIPTION_TO_BE_TYPED)).check(matches(isDisplayed()))
        onView(withText(ADDRESS_TO_BE_TYPED)).check(matches(isDisplayed()))

    }

    @Test
    fun buyProduct() {

        // NAVIGATE - Setup
        onView(withId(R.id.product_category_dest)).perform(ViewActions.click())

        onView(withId(R.id.recycler_view_product_category)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProductCategoryAdapter.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.recycler_view_product_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ViewHolder>(0, ViewActions.click()))
        onView(withId(R.id.add_to_cart_button)).perform(ViewActions.click())
        onView(withId(R.id.add_to_cart_button)).perform(ViewActions.click())



        }

    companion object {
        val NAME_TO_BE_TYPED = "Event Espresso" + Date()
        val DESCRIPTION_TO_BE_TYPED = "Description Espresso" + Date()
        val ADDRESS_TO_BE_TYPED = "Address Espresso" + Date()

    }
}