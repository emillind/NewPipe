package us.shandian.giga.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.schabi.newpipe.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import us.shandian.giga.CodeCoverage;

public class Utility {

    public enum FileType {
        VIDEO,
        MUSIC,
        UNKNOWN
    }

    public static String formatBytes(long bytes) {
        if (bytes < 1024) {
            return String.format("%d B", bytes);
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f kB", (float) bytes / 1024);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", (float) bytes / 1024 / 1024);
        } else {
            return String.format("%.2f GB", (float) bytes / 1024 / 1024 / 1024);
        }
    }

    public static String formatSpeed(float speed) {
        if (speed < 1024) {
            return String.format("%.2f B/s", speed);
        } else if (speed < 1024 * 1024) {
            return String.format("%.2f kB/s", speed / 1024);
        } else if (speed < 1024 * 1024 * 1024) {
            return String.format("%.2f MB/s", speed / 1024 / 1024);
        } else {
            return String.format("%.2f GB/s", speed / 1024 / 1024 / 1024);
        }
    }

    public static void writeToFile(String fileName, String content) {
        try {
            writeToFile(fileName, content.getBytes("UTF-8"));
        } catch (Exception e) {

        }
    }

    public static void writeToFile(String fileName, byte[] content) {
        File f = new File(fileName);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {

            }
        }

        try {
            FileOutputStream opt = new FileOutputStream(f, false);
            opt.write(content, 0, content.length);
            opt.close();
        } catch (Exception e) {

        }
    }

    public static String readFromFile(String file) {
        try {
            File f = new File(file);

            if (!f.exists() || !f.canRead()) {
                return null;
            }

            BufferedInputStream ipt = new BufferedInputStream(new FileInputStream(f));

            byte[] buf = new byte[512];
            StringBuilder sb = new StringBuilder();

            while (ipt.available() > 0) {
                int len = ipt.read(buf, 0, 512);
                sb.append(new String(buf, 0, len, "UTF-8"));
            }

            ipt.close();
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String getFileExt(String url) {
        int index;
        if ((index = url.indexOf("?")) > -1) {
            url = url.substring(0, index);
        }

        index = url.lastIndexOf(".");
        if (index == -1) {
            return null;
        } else {
            String ext = url.substring(index);
            if ((index = ext.indexOf("%")) > -1) {
                ext = ext.substring(0, index);
            }
            if ((index = ext.indexOf("/")) > -1) {
                ext = ext.substring(0, index);
            }
            return ext.toLowerCase();
        }
    }


    /*
    * Requirements:
    * The type of file is to be set to VIDEO or MUSIC depending on the file ending.
    * If the file has not got a recognised file ending, the file type is set to UNKNOWN.
    *
    * */

    public static FileType getFileType(String file, CodeCoverage... codeCoverage) {
       CodeCoverage cc = codeCoverage.length != 0 ? codeCoverage[0] : new CodeCoverage("getFileType");
       String data = "file: " + file;

       if(file.endsWith(".mp3")) cc.visitBranch(0, data); //0
       else if(file.endsWith(".wav")) cc.visitBranch(1, data); //1
       else if(file.endsWith(".flac")) cc.visitBranch(2, data); //2
       else if(file.endsWith(".m4a")) cc.visitBranch(3, data); //3

       if (file.endsWith(".mp3") || file.endsWith(".wav") || file.endsWith(".flac") || file.endsWith(".m4a")) {  // (0||1||2||3)
           return FileType.MUSIC;
       }
       else cc.visitBranch(4, data); //4

       if(file.endsWith(".mp4")) cc.visitBranch(5, data); //5
       else if(file.endsWith(".mpeg")) cc.visitBranch(6, data); //6
       else if(file.endsWith(".rm")) cc.visitBranch(7, data); //7
       else if(file.endsWith(".rmvb")) cc.visitBranch(8, data); //8
       else if(file.endsWith(".flv")) cc.visitBranch(9, data); //9
       else if(file.endsWith(".webp")) cc.visitBranch(10, data); //10
       else if(file.endsWith(".webm")) cc.visitBranch(11, data); //11

       if (file.endsWith(".mp4") || file.endsWith(".mpeg") || file.endsWith(".rm") || file.endsWith(".rmvb")
                || file.endsWith(".flv") || file.endsWith(".webp") || file.endsWith(".webm")){ // (4||6||7||8||9||10||11)
           return FileType.VIDEO;
       }
       else cc.visitBranch(12, data); //12

       return FileType.UNKNOWN;
    }

    @ColorRes
    public static int getBackgroundForFileType(FileType type) {
        switch (type) {
            case MUSIC:
                return R.color.audio_left_to_load_color;
            case VIDEO:
                return R.color.video_left_to_load_color;
            default:
                return R.color.gray;
        }
    }

    @ColorRes
    public static int getForegroundForFileType(FileType type) {
        switch (type) {
            case MUSIC:
                return R.color.audio_already_load_color;
            case VIDEO:
                return R.color.video_already_load_color;
            default:
                return R.color.gray;
        }
    }

    @DrawableRes
    public static int getIconForFileType(FileType type) {
        switch (type) {
            case MUSIC:
                return R.drawable.music;
            case VIDEO:
                return R.drawable.video;
            default:
                return R.drawable.video;
        }
    }

    public static void copyToClipboard(Context context, String str) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("text", str));
        Toast.makeText(context, R.string.msg_copied, Toast.LENGTH_SHORT).show();
    }

    public static String checksum(String path, String algorithm) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        FileInputStream i = null;

        try {
            i = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        byte[] buf = new byte[1024];
        int len = 0;

        try {
            while ((len = i.read(buf)) != -1) {
                md.update(buf, 0, len);
            }
        } catch (IOException e) {

        }

        byte[] digest = md.digest();

        // HEX
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();

    }
}
