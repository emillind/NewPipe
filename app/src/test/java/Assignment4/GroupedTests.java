package Assignment4;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.os.Looper;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
import org.schabi.newpipe.fragments.list.search.SearchFragment;
import static junit.framework.Assert.*;

/**
 * Created by emillindblom on 2018-02-25.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class, Bundle.class, TextUtils.class, Looper.class})
public class GroupedTests {

    @Test
    public void initSuggestionObserverTest() throws Exception {
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(Looper.class);
        Bundle mock = PowerMockito.mock(Bundle.class);
        PowerMockito.whenNew(Bundle.class).withNoArguments().thenReturn(mock);

        String methodName = "initSuggestionObserver";
        System.out.println("Calculating branch coverage for " + methodName);
        CodeCoverage cc = new CodeCoverage(methodName);
        Bundle outState = new Bundle();

        // visit branch 0
        SearchFragment sf = SearchFragment.getInstance(0,"");
        sf.initSuggestionObserver(cc);

        sf = SearchFragment.getInstance(1,"a");
        sf.initSuggestionObserver(cc);
       
        System.out.println(cc.toString());
    }

    @Test
    public void initSearchListenersTest() throws Exception {
        PowerMockito.mockStatic(Log.class);
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(Looper.class);
        Bundle mock = PowerMockito.mock(Bundle.class);
        PowerMockito.whenNew(Bundle.class).withNoArguments().thenReturn(mock);

        String methodName = "initSearchListeners";
        System.out.println("Calculating branch coverage for " + methodName);
        CodeCoverage cc = new CodeCoverage(methodName);
        //Bundle outState = new Bundle();

        //visit branch 0
        //SearchFragment sf = SearchFragment.getInstance(0,"");
        //sf.initSearchListeners(cc);

        
        System.out.println(cc.toString());
    }

}

