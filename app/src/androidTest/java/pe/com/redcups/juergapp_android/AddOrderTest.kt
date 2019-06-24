package pe.com.redcups.juergapp_android

import android.app.Activity
import android.view.View
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
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
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import kotlinx.android.synthetic.main.fragment_cart.view.*

import org.junit.Rule
import org.junit.Test
import pe.com.redcups.juergapp_android.fragment.EventAddFragment
import pe.com.redcups.juergapp_android.adapter.EventAdapter
import pe.com.redcups.juergapp_android.adapter.OrderDetailsAdapter
import pe.com.redcups.juergapp_android.adapter.ProductCategoryAdapter
import pe.com.redcups.juergapp_android.adapter.ProductListAdapter
import java.util.*
import java.util.regex.Matcher



import android.content.res.Resources
import android.util.Log
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert


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

        onView(withId(R.id.event_add_image)).perform(ViewActions.click())

        // need to add a picture

        // Save new Event

        onView(withId(R.id.add_event_button)).perform(ViewActions.click())

        onView(withText(NAME_TO_BE_TYPED)).check(matches(isDisplayed()))
        onView(withText(ADDRESS_TO_BE_TYPED)).check(matches(isDisplayed()))
        onView(withText(DESCRIPTION_TO_BE_TYPED)).check(matches(isDisplayed()))

    }

    @Test
    fun buyProduct() {

        // NAVIGATE - Setup
        onView(withId(R.id.product_category_dest)).perform(ViewActions.click())


        // Pick first product category
        onView(withId(R.id.recycler_view_product_category)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProductCategoryAdapter.ViewHolder>(0, ViewActions.click()))

        // Pick first product
        onView(withId(R.id.recycler_view_product_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ViewHolder>(0, ViewActions.click()))

        // Add the product twice to cart
        onView(withId(R.id.add_to_cart_button)).perform(ViewActions.click())
        onView(withId(R.id.add_to_cart_button)).perform(ViewActions.click())

        // Navigate to Cart
        onView(withId(R.id.cart_dest)).perform(ViewActions.click())

        onView(withId(R.id.cart_content_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.cart_content_recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<OrderDetailsAdapter.ViewHolder>(0))

        // Confirm Purchase
        onView(withId(R.id.confirm_purchase_button)).perform(ViewActions.click())

        // Check Orders in profile
        onView(withId(R.id.profile_dest)).perform(ViewActions.click())

        onView(withId(R.id.cart_dest)).perform(ViewActions.click())

        //cart should now be empty

        Assert.assertEquals(0, getRVcount())
        }

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {

        return RecyclerViewMatcher(recyclerViewId)
    }
    companion object {
        val NAME_TO_BE_TYPED = "Event Espresso " + Date()
        val DESCRIPTION_TO_BE_TYPED = "Description Espresso " + Date()
        val ADDRESS_TO_BE_TYPED = "Address Espresso " + Date()

    }


    private fun getRVcount(): Int {

        var items =0

        val recyclerView = activityScenarioRule.scenario.onActivity { activity -> run {
            var recyclerView = activity.findViewById(R.id.cart_content_recycler_view) as RecyclerView
            items = recyclerView.adapter!!.itemCount
        } }
        return items
        }


}


/**
 * Created by dannyroa on 5/10/15.
 */
class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPosition(position: Int): TypeSafeMatcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): TypeSafeMatcher<View> {

        return object : TypeSafeMatcher<View>() {
            internal var resources: Resources? = null
            internal var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = Integer.toString(recyclerViewId)
                if (this.resources != null) {
                    try {
                        idDescription = this.resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        idDescription = String.format(
                            "%s (resource name not found)",
                            *arrayOf<Any>(Integer.valueOf(recyclerViewId))
                        )
                    }

                }

                description.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                    if (recyclerView != null && recyclerView.id === recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                    } else {
                        return false
                    }
                }

                if (targetViewId == -1) {
                    return view === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    return view === targetView
                }

            }
        }
    }
}