package com.che.grouprecycleview.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by yutianran on 16/2/27.
 */
public class FileUtil {


    /*读取Assets文件内容*/
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
