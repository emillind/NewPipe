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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.exoplayer2.source.MediaSource;

import Assignment4.CodeCoverage;

import static org.junit.Assert.*;	 import static org.junit.Assert.*;

import android.os.Looper;
import android.util.Log;

import org.junit.Test;
import org.schabi.newpipe.RouterActivity;
import org.schabi.newpipe.extractor.Info;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelInfo;
import org.schabi.newpipe.extractor.stream.StreamInfo;
import org.schabi.newpipe.extractor.stream.StreamType;

import Assignment4.CodeCoverage;
import io.reactivex.functions.Consumer;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.schabi.newpipe.extractor.stream.StreamType.*;

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

        //Branch 3 & 23
        msgl = mvp.new MySimpleOnGestureListener();
        msgl.eventsNum = 1;
        assertFalse(msgl.onScroll(e1, e2, 2, 1, cc));

        //Branch 5, 16, 18, 20, 21, 24
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


        Log.i("Coverage info: " ,cc.toString());
    }

    @Test
    public void getResultHandlerTest() throws Exception {
        String methodName = "getResultHandler";
        System.out.println("Calculating branch coverage for " + methodName);
        Looper.prepare();

        CodeCoverage cc = new CodeCoverage(methodName);
        RouterActivity.FetcherService fs;
        RouterActivity.Choice choice;
        Consumer<Info> info;
        StreamInfo in;
        ChannelInfo ci;

        //Branch 0, 1
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(1, StreamingService.LinkType.NONE, "myurl", "background_player_key");
        info = fs.getResultHandler(choice, cc);
        fs.isExtAudioEnabled = true;
        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        //Branch 0, 2
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(2, StreamingService.LinkType.NONE, "myurl", "video_player_key");
        info = fs.getResultHandler(choice, cc);
        fs.isExtVideoEnabled = true;
        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        //Branch 0, 3
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(3, StreamingService.LinkType.NONE, "myurl", "video_player_key");
        info = fs.getResultHandler(choice, cc);
        fs.useOldVideoPlayer = true;
        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        // Branch 0, 4, 5
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(5, StreamingService.LinkType.NONE, "myurl", "video_player_key");
        info = fs.getResultHandler(choice, cc);

        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        // Branch 0, 4, 6
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(6, StreamingService.LinkType.NONE, "myurl", "background_player_key");
        info = fs.getResultHandler(choice, cc);

        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        // Branch 0, 4, 7
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(7, StreamingService.LinkType.NONE, "myurl", "popup_player_key");
        info = fs.getResultHandler(choice, cc);

        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        // Branch 0, 4, 8
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(8, StreamingService.LinkType.NONE, "myurl", "rubbish");
        info = fs.getResultHandler(choice, cc);

        in = new StreamInfo(0, "url", NONE, "id", "name", 18) {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(in);
        assertNotNull(info);

        // Branch 9, 10
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(10, StreamingService.LinkType.NONE, "myurl", "video_player_key");
        info = fs.getResultHandler(choice, cc);

        ci = new ChannelInfo(0, "url", "id", "name");
        info.accept(ci);
        assertNotNull(info);

        // Branch 9, 11
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(11, StreamingService.LinkType.NONE, "myurl", "background_player_key");
        info = fs.getResultHandler(choice, cc);

        ci = new ChannelInfo(0, "url", "id", "name");
        info.accept(ci);
        assertNotNull(info);

        // Branch 9, 12
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(12, StreamingService.LinkType.NONE, "myurl", "popup_player_key");
        info = fs.getResultHandler(choice, cc);

        ci = new ChannelInfo(0, "url", "id", "name");
        info.accept(ci);
        assertNotNull(info);

        // Branch 9, 13
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(13, StreamingService.LinkType.NONE, "myurl", "rubbish");
        info = fs.getResultHandler(choice, cc);

        ci = new ChannelInfo(0, "url", "id", "name");
        info.accept(ci);
        assertNotNull(info);

        // Branch 14
        fs = new RouterActivity.FetcherService();
        choice = new RouterActivity.Choice(14, StreamingService.LinkType.NONE, "myurl", "rubbish");
        info = fs.getResultHandler(choice, cc);

        Info i = new Info(0, "url", "id", "name") {
            @Override
            public void addError(Throwable throwable) {
                super.addError(throwable);
            }
        };
        info.accept(i);
        assertNotNull(info);

        Log.i("Coverage info: ", cc.toString());
    }
}

