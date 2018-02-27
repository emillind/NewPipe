package org.schabi.newpipe;


import android.os.Parcel;
import android.support.test.annotation.UiThreadTest;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.CoreMatchers.allOf;


import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.StringContains.containsString;

import com.google.android.exoplayer2.extractor.ts.TsExtractor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schabi.newpipe.fragments.detail.VideoDetailFragment;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;



/**
 * Created by Eyrun on 26/02/2018.
 */



@RunWith(AndroidJUnit4.class)
@LargeTest
public class VideoDetailFragmentTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class, true, true);

    @Before
    public void init(){
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

    }


    @Test
    public void Test1(){

        //This id is found in the fragment. Test fails as it can't find it
      //  onView(withId(R.id.detail_controls_background)).check(matches(withText(startsWith("Background"))));

      //  onView(allOf(withId(R.id.detail_controls_background)))
        //is only checking the very first screen it looks like
        onView(withText(startsWith("Trending"))).check(matches(withText(startsWith("Trending"))));
        onView(withText(R.string.trending)).check(matches(withText(startsWith("Trending"))));

        //must have to inflate the video detail fragment here
        //Verð að ýta á myndband einhvernvegin, þá kemst ég á detail fragmentið
       // onData(withId(R.id.items_list)).perform(click());

       // onView(withId(R.id.items_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(ViewMatchers.withId(R.id.items_list)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        click()));

     /*   onView(allOf(ViewMatchers.withText(startsWith("John Cena")))).perform(closeSoftKeyboard());

        onView(allOf(ViewMatchers.withText(startsWith("John Cena"))))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,
                        click())); */
        //onView(withId(android.R.id.content)).check(matches(isDisplayed()));
        //onView(withId(R.id.toolbar_search_edit_text)).check(matches(isEnabled()));

    }
}

