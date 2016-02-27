package com.che.grouprecycleview.callback;

/**
 * Created by yutianran on 16/2/27.
 */
public interface MyCallBack<T> {

    void onSuccess(T response);
    void onFailure(Throwable error);
}
