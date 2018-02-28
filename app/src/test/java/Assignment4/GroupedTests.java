package Assignment4;

import org.junit.Test;

import Assignment4.CodeCoverage;
import us.shandian.giga.util.Utility;

import static junit.framework.Assert.assertEquals;

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




}

