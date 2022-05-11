package com.openclassrooms.entrevoisins.neighbour_list;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }
    /**
     * When we remove an item from the favorites list, the item is no longer displayed
     */
    @Test
    public void myNeighboursFavoriteList_deleteAction_shouldRemoveItem() {

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
        // When perform a click on a delete icon
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(actionOnItemAtPosition(0, new DeleteViewAction()));
        // Then : the number of element is 0
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(withItemCount(0));
        // Swipe left to display the favorites list
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours)))
                .perform(swipeRight());
        // 1 second pause for list loading
        SystemClock.sleep(1000);
        // Then : the number of element is 11
        onView(allOf(isDisplayed(), withId(R.id.list_neighbours))).check(withItemCount(ITEMS_COUNT-1));

    }
}
