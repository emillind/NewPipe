package org.schabi.newpipe;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.exoplayer2.source.MediaSource;

import org.junit.Test;
import org.schabi.newpipe.extractor.stream.StreamInfo;
import org.schabi.newpipe.player.playback.MediaSourceManager;
import org.schabi.newpipe.player.playback.PlaybackListener;
import org.schabi.newpipe.playlist.PlayQueue;
import org.schabi.newpipe.playlist.PlayQueueItem;
import org.schabi.newpipe.playlist.events.*;

import java.util.ArrayList;
import java.util.List;

import Assignment4.CodeCoverage;

import static junit.framework.Assert.*;


/**
 * Created by emillindblom on 2018-02-25.
 */

public class GroupedTests {

    private static final String TAG = "Coverage info: ";

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
    public void onPlayQueueChangedTest_1() throws Exception {
        String methodName = "onPlayQueueChanged";
        System.out.println("Calculating branch coverage for " + methodName);
        CodeCoverage cc = new CodeCoverage(methodName);

        PlaybackListener listener = new PlaybackListener() {
            @Override
            public void block() {

            }

            @Override
            public void unblock(MediaSource mediaSource) {

            }

            @Override
            public void sync(@android.support.annotation.NonNull PlayQueueItem item, @Nullable StreamInfo info) {

            }

            @Nullable
            @Override
            public MediaSource sourceOf(PlayQueueItem item, StreamInfo info) {
                return null;
            }

            @Override
            public void shutdown() {

            }
        };
        //PlayQueueItem playQueueItem = new PlayQueueItem();
        List<PlayQueueItem> listWithPlayQueueItems = new ArrayList<>();
        //listWithPlayQueueItems.add(playQueueItem);
        PlayQueue playQueue = new PlayQueue(0, listWithPlayQueueItems) {
            @Override
            public boolean isComplete() {
                return false;
            }

            @Override
            public void fetch() {

            }
        };
        playQueue.init();
        MediaSourceManager mediaSourceManager = new MediaSourceManager(listener, playQueue);
        InitEvent event1 = new InitEvent();
        mediaSourceManager.onPlayQueueChanged(event1, cc);

        assertFalse(mediaSourceManager.isPlayQueueReady());

        RemoveEvent event2 = new RemoveEvent(1, 0);
        mediaSourceManager.onPlayQueueChanged(event2, cc);

        assertFalse(mediaSourceManager.isPlayQueueReady());

        Log.i(TAG, cc.toString());
    }

}

