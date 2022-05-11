
package com.openclassrooms.entrevoisins.neighbour_list;

import android.os.SystemClock;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mService = DI.getNeighbourApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(Matchers.allOf(isDisplayed(), ViewMatchers.withId(R.id.list_neighbours)))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Check that when the first element is clicked, its profile is launched
     */
    @Test
    public void myNeighboursList_openProfile_shouldNameNotBeEmpty() {

        // Click on the first element of the list
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(actionOnItemAtPosition(0, click()));
        // Check that the neighbour profile activity is Displayed
        onView(withId(R.id.profile_content)).check(matches(isDisplayed()));
        // Check that the name is not Null
        onView(withId(R.id.name))
                .check(matches(withText(mService.getNeighbours().get(0).getName())));
    }

    /**
     * Add and verify that the favorites list has an item
     */
    @Test
    public void myNeighboursFavoriteList_addAndCheck() {

        // Click on the first element of the list
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(actionOnItemAtPosition(0, click()));
        // Check that the neighbour profile activity is Displayed
        onView(withId(R.id.profile_content)).check(matches(isDisplayed()));
        // Click on the Flaoting button to add as a favorite
        onView(withId(R.id.add_fav_neighbour)).perform(click());
        // Back to the list_neighbours
        onView(withId(R.id.profile_content)).perform(pressBack());
        // Swipe left to display the favorites list
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(swipeLeft());
        // 1 second pause for list loading
        SystemClock.sleep(1000);
        // Check that the list has 1 item
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .check(withItemCount(1));
        // Click on the first element of the list
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(actionOnItemAtPosition(0, click()));
        // Check that the neighbour profile activity is Displayed
        onView(withId(R.id.profile_content)).check(matches(isDisplayed()));
        // Click on the Flaoting button to add as a favorite
        onView(withId(R.id.add_fav_neighbour)).perform(click());
        // Back to the list_neighbours
        onView(withId(R.id.profile_content)).perform(pressBack());
        // Check that the list has 0 item
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .check(withItemCount(0));
    }


}