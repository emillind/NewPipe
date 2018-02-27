package Assignment4;

import org.junit.Test;
import org.schabi.newpipe.player.MainVideoPlayer;

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
    public void onClickTest() throws Exception {
        String methodName = "onClick";
        System.out.println("Calculating branch coverage for " + methodName);
        CodeCoverage cc = new CodeCoverage(methodName);

        MainVideoPlayer.MySimpleOnGestureListener mvp = null;
        //mvp.onScroll();


        System.out.println(cc.toString());
    }

}

