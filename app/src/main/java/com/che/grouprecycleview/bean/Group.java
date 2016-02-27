package com.che.grouprecycleview.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yutianran on 16/2/27.
 */
public class Group implements Serializable {
    private String groupName;
    private String groupDesc;
    private List<Child> groupData;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public List<Child> getGroupData() {
        return groupData;
    }

    public void setGroupData(List<Child> groupData) {
        this.groupData = groupData;
    }
}
