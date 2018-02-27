package org.schabi.newpipe;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Eyrun on 27/02/2018.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RouterActivityTest {


    @Rule
    public ActivityTestRule<RouterActivity> mActivityRule = new ActivityTestRule<>(
            RouterActivity.class);


    @Test
    public void Test1(){

        //Here you can use espresso to access the components needed

    }
}