package com.che.grouprecycleview.config;

import android.content.Context;

import com.che.grouprecycleview.BuildConfig;
import com.che.grouprecycleview.callback.MyCallBack;
import com.che.grouprecycleview.util.FileUtil;
import com.che.grouprecycleview.util.JsonUtil;
import com.che.grouprecycleview.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by yutianran on 16/2/27.
 */
public class MyWebClient {

    public static <T> void getCarFilter(final Context context, final MyCallBack<T> callBack) {
        //测试服务器，先用本地自己写的json
        if (BuildConfig.DEBUG) {

            final String body = FileUtil.getFromAssets(context, "json/getCarFilterParams.json");
            try {
                Type[] types = callBack.getClass().getGenericInterfaces();
                ParameterizedType parameterizedType = (ParameterizedType) types[0];
                final Type actualTypeArguments = parameterizedType.getActualTypeArguments()[0];
                final T t = JsonUtil.getInstance().stringToObj(body, actualTypeArguments);

                callBack.onSuccess(t);

            } catch (Exception e) {
                LogUtil.print("出错啦：" + e.getMessage());
            }
        }
        //正式服务器
        else{
            //……
        }
    }


}
