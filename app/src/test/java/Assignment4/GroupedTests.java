package Assignment4;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.schabi.newpipe.fragments.local.dialog.PlaylistDialog;
import org.schabi.newpipe.util.AnimationUtils;
import org.schabi.newpipe.util.StateSaver;

import java.io.File;

import static org.junit.Assert.*;


import Assignment4.CodeCoverage;
import us.shandian.giga.util.Utility;

import static junit.framework.Assert.assertEquals;

/**
 * Created by emillindblom on 2018-02-25.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, Bundle.class, TextUtils.class})
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
    public void testMusic(){
        //Contract: FileType is MUSIC if file ending matches music file endings.
        //It's VIDEO if file ending matches video file endings. Else it's unknown

        String methodName = "getFileType";
        CodeCoverage cc = new CodeCoverage(methodName);


        //0 1 2 3
        assertEquals(Utility.getFileType("file.mp3", cc), Utility.FileType.MUSIC);
        assertEquals(Utility.getFileType("file.wav", cc), Utility.FileType.MUSIC);
        assertEquals(Utility.getFileType("file.flac", cc), Utility.FileType.MUSIC);
        assertEquals(Utility.getFileType("file.m4a", cc), Utility.FileType.MUSIC);

        // 4 5 6 7 8 9 10
        assertEquals(Utility.getFileType("file.mp4", cc), Utility.FileType.VIDEO);
        assertEquals(Utility.getFileType("file.mpeg", cc), Utility.FileType.VIDEO);
        assertEquals(Utility.getFileType("file.rm", cc), Utility.FileType.VIDEO);
        assertEquals(Utility.getFileType("file.rmvb", cc), Utility.FileType.VIDEO);
        assertEquals(Utility.getFileType("file.flv", cc), Utility.FileType.VIDEO);
        assertEquals(Utility.getFileType("file.webp", cc), Utility.FileType.VIDEO);
        assertEquals(Utility.getFileType("file.webm", cc), Utility.FileType.VIDEO);

        // 11
        assertEquals(Utility.getFileType("", cc), Utility.FileType.UNKNOWN);

        System.out.println("Calculating branch coverage for " + methodName);
        System.out.println(cc.toString());
    }


    /**
     * Requirements
     * - If the view is visible and we are entering or exiting view, set it to visible and run executable if any
     * - If the view is either gone or invisible and we are not entering or exiting view, set it to gone and run executable if any
     * - Depending on the input animationType, run the desired animation
     */
    @Test
    public void animateViewTest() throws Exception {
        PowerMockito.mockStatic(Log.class);
        String methodName = "animateView";
        System.out.println("Calculating branch coverage for " + methodName);
        CodeCoverage cc = new CodeCoverage(methodName);

        AnimationUtils.MockView mockView;

        //Branch 0, 1, 2
        mockView = new AnimationUtils().new MockView(View.VISIBLE);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.ALPHA, true, 0, 0, () -> {}, cc);

        //Branch 0, 1, 3
        AnimationUtils.animateView(mockView, AnimationUtils.Type.ALPHA, true, 0, 0, null, cc);

        //Branch 4, 6, 7
        mockView = new AnimationUtils().new MockView(View.GONE);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.ALPHA, false, 0, 100, () -> {}, cc);

        //Branch 5, 6, 8
        mockView = new AnimationUtils().new MockView(View.INVISIBLE);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.ALPHA, false, 0, 0, null, cc);

        //Branch 9
        mockView = new AnimationUtils().new MockView(-1);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.ALPHA, false, 0, 0, null, cc);

        //Branch 10
        mockView = new AnimationUtils().new MockView(-1);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.SCALE_AND_ALPHA, false, 0, 0, null, cc);

        //Branch 11
        mockView = new AnimationUtils().new MockView(-1);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.LIGHT_SCALE_AND_ALPHA, false, 0, 0, null, cc);

        //Branch 12
        mockView = new AnimationUtils().new MockView(-1);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.SLIDE_AND_ALPHA, false, 0, 0, null, cc);

        //Branch 13
        mockView = new AnimationUtils().new MockView(-1);
        AnimationUtils.animateView(mockView, AnimationUtils.Type.LIGHT_SLIDE_AND_ALPHA, false, 0, 0, null, cc);

        System.out.println(cc.toString());
    }

    @Test
    /**
     * Requirements
     * - If the configuration is changing and there are saved objects, put those objects in a holder for
     * the prefix and return a new SavedState with that prefix.
     * - Otherwise start saving the state to the cache
     * - If the directory in which the cache directory should be does not exist throw RuntimeException
     * - If the cache directory does not exist, try to create it. If it cannot be created abort and return.
     * - If the cache directory was created or already existed check if a file with this exact name exists
     * - If it does exist and is not empty, return a SavedState pointing to that file
     * - If it does not, remove all files containing the prefix and write to the new file.
     * - Should there be any Exception thrown log it
     */
    public void tryToSaveTest() throws Exception {
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(TextUtils.class);
        Bundle mock = PowerMockito.mock(Bundle.class);
        PowerMockito.whenNew(Bundle.class).withNoArguments().thenReturn(mock);
        String methodName = "tryToSave";
        System.out.println("Calculating branch coverage for " + methodName);
        //Delete the cache directory to have the same behaviour every time.
        deleteDir(new File("/tmp/state_cache"));
        CodeCoverage cc = new CodeCoverage(methodName);
        Bundle outState = new Bundle();
        StateSaver.WriteRead pld = new PlaylistDialog() {};

        //Reaches branch 0 and 1. Returns a new SavedState with unique path
        assertNotNull(StateSaver.tryToSave(true, null, outState, pld, cc));

        //Reaches branches 3, 4, 17 and 20. Cache dir path is set to avoid null pointer
        StateSaver.setCacheDirPath("");
        assertNull(StateSaver.tryToSave(false, null, outState, pld, cc));

        //Reaches branches 7 and 18
        StateSaver.setCacheDirPath("/");
        assertNull(StateSaver.tryToSave(false, null, outState, pld, cc));

        //Reaches branches 5, 6, 8, 11, 14 and 16
        StateSaver.setCacheDirPath("/tmp");
        StateSaver.SavedState savedState = StateSaver.tryToSave(false, null, outState, pld, cc);
        assertNotNull(savedState);

        //Reaches branch 9, 12, 13
        assertNotNull(StateSaver.tryToSave(false, savedState, outState, pld, cc));

        //Branch 15 needs multiple streams to be saved at the same time, cant figure out how that works.
        //Branch 19 needs to produce an I/O exception on the fileOutputStream.close()

        //Dead branches:
        // 2. Always has data, is never empty
        cc.visitBranch(2, "THIS BRANCH HAS DEAD CODE");
        // 10. Will always be generated via generateSuffix() and both implementing classes will return a string or exception there.
        cc.visitBranch(10, "THIS BRANCH HAS DEAD CODE");

        System.out.println(cc.toString());
    }

    private void deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child: children)
                deleteDir(new File(dir, child));
        }
        // The directory is now empty or this is a file so delete it
        dir.delete();
    }


}

