package com.che.grouprecycleview.bean;

import java.io.Serializable;

/**
 * Created by yutianran on 16/2/27.
 */
public class Child implements Serializable{
    private String childName;

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildName() {
        return childName;
    }
}
