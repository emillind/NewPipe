package Assignment4;

import org.junit.Test;
import org.schabi.newpipe.player.MainVideoPlayer;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;	 import static org.junit.Assert.*;

/**
 * Created by emillindblom on 2018-02-25.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainVideoPlayer.MySimpleOnGestureListener.class, Bundle.class, MotionEvent.class, SystemClock.class})
public class GroupedTests {

    @Test
    public void exampleMethodTest() throws Exception {
        //String methodName = "exampleMethod";
        //System.out.println("Calculating branch coverage for " + methodName);
        //CodeCoverage cc = new CodeCoverage(methodName);

        //assertEquals(The result, exampleMethod(some, data, that, visits, specific, branches, cc));
        //assertEquals(The result, exampleMethod(some, data, that, visits, other, branches, cc);
        //System.out.println(cc.toString());
    }

    @Test
    public void onScrollTest() throws Exception {
        PowerMockito.mockStatic(MainVideoPlayer.MySimpleOnGestureListener.class);
        PowerMockito.mockStatic(MotionEvent.class);
        PowerMockito.mockStatic(SystemClock.class);
        Bundle mock = PowerMockito.mock(Bundle.class);
        PowerMockito.whenNew(Bundle.class).withNoArguments().thenReturn(mock);

        String methodName = "onScroll";
        System.out.println("Calculating branch coverage for " + methodName);
        CodeCoverage cc = new CodeCoverage(methodName);

        // get the coordinates of the view
        int[] coordinates = new int[2];
        // MotionEvent parameters
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        int action = MotionEvent.ACTION_DOWN;
        int x = coordinates[0];
        int y = coordinates[1];
        int metaState = 0;

        // dispatch the event
        MotionEvent e1 = MotionEvent.obtain(downTime, eventTime, action, x, y, metaState);
        MotionEvent e2 = MotionEvent.obtain(downTime, eventTime, action, x, y, metaState);
        // Branch 0


        //MainVideoPlayer.MySimpleOnGestureListener.isPlayerGestureEnabled = true;

        MainVideoPlayer.MySimpleOnGestureListener mvp = new MainVideoPlayer.MySimpleOnGestureListener();

        boolean b = onScroll(e1, e2, 2, 1, cc);

        System.out.println(cc.toString());
    }

}

