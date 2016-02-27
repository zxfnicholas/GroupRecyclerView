package com.che.grouprecycleview.response;

import com.che.grouprecycleview.bean.Group;

import java.util.List;

public class getCarFilterParamsResponse extends BaseResponse {

    private List<Group> data;

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }
}
