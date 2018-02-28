package org.schabi.newpipe;

import org.jsoup.Connection;
import org.junit.Test;
import org.schabi.newpipe.extractor.stream.StreamInfo;
import org.schabi.newpipe.player.BasePlayer;
import org.schabi.newpipe.player.MainVideoPlayer;
import org.schabi.newpipe.playlist.PlayQueueItem;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.exoplayer2.source.MediaSource;

import Assignment4.CodeCoverage;

import static org.junit.Assert.*;	 import static org.junit.Assert.*;

/**
 * Created by emillindblom on 2018-02-25.
 */

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


        //MainVideoPlayer.MySimpleOnGestureListener.isPlayerGestureEnabled = true;
        //PlayerHelper.isPlayerGestureEnabled();
        Looper.prepare();
        MainVideoPlayer mvp = new MainVideoPlayer();
        MainVideoPlayer.MySimpleOnGestureListener msgl = mvp.new MySimpleOnGestureListener();

        // Branch 0
        msgl = mvp.new MySimpleOnGestureListener();
        msgl.isPlayerGestureEnabled = false;
        assertFalse(msgl.onScroll(e1, e2, 2, 1, cc));

        //Branch 1 & 2
        msgl = mvp.new MySimpleOnGestureListener();
        msgl.triggered = false;
        assertFalse(msgl.onScroll(e1, e2, 2, 1, cc));

        //Branch 3 & 4
        msgl = mvp.new MySimpleOnGestureListener();
        msgl.currentState = 128;
        assertFalse(msgl.onScroll(e1, e2, 2, 1, cc));

        //Branch 5, 16, 18, 20, 21, 23
        msgl = mvp.new MySimpleOnGestureListener();
        assertTrue(msgl.onScroll(e1, e2, 2, 1, cc));

        //Branch 6, 8 , 10, 12, 13
        e1 = MotionEvent.obtain(downTime, eventTime, action, 1200, y, metaState);
        msgl = mvp.new MySimpleOnGestureListener();
        assertTrue(msgl.onScroll(e1, e2, 2, 1, cc));

        //Branch 6, 8 , 10, 12, 13
        e1 = MotionEvent.obtain(downTime, eventTime, action, 1200, y, metaState);
        msgl.getVolume = 100;
        msgl = mvp.new MySimpleOnGestureListener();
        assertTrue(msgl.onScroll(e1, e2, 2, 1, cc));


        System.out.println(cc.toString());
    }

}

