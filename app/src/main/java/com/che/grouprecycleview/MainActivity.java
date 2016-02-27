package com.che.grouprecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.che.grouprecycleview.adapter.GroupAdapter;
import com.che.grouprecycleview.bean.Child;
import com.che.grouprecycleview.bean.Group;
import com.che.grouprecycleview.callback.MyCallBack;
import com.che.grouprecycleview.config.MyWebClient;
import com.che.grouprecycleview.response.getCarFilterParamsResponse;
import com.che.grouprecycleview.util.JsonUtil;
import com.che.grouprecycleview.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private ViewHolder viewHolder;
    private getCarFilterParamsResponse response;

    private int gridSpanCount = 4;
    private GroupAdapter adapter;
    private Map<String,String> carFilterRequest=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadView();
        loadData();
    }

    private void loadView() {
        viewHolder = new ViewHolder(getWindow().getDecorView());
        initRecyclerView();
        initHead();
        initFoot();
    }

    private void loadData() {
        MyWebClient.getCarFilter(this, new MyCallBack<getCarFilterParamsResponse>() {
            @Override
            public void onSuccess(getCarFilterParamsResponse _response) {
                response = _response;
                adapter.setData(response);
                setDefultParams();
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void setDefultParams() {
        for (Group group : response.getData()) {
            carFilterRequest.put(group.getGroupName(), group.getGroupData().get(0).getChildName());
        }
    }

    private void initHead() {
        viewHolder.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewHolder.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("你点击了：重置");
                setDefultParams();
                adapter.setData(response);
            }
        });
    }

    private void initFoot() {
        viewHolder.tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("你点击了：筛选条件=" + JsonUtil.getInstance().objToString(carFilterRequest));
                LogUtil.print("你点击了：筛选条件=" + JsonUtil.getInstance().objToString(carFilterRequest));
            }
        });
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, gridSpanCount);
        viewHolder.rvCarFilter.setLayoutManager(gridLayoutManager);
        adapter = new GroupAdapter(this, gridLayoutManager, new GroupAdapter.OnAdapterClickLisenter() {
            @Override
            public void onGroupClick(Group group) {
                toast(group.getGroupName());
            }

            @Override
            public void onChildClick(Group group, Child child) {
                toast(child.getChildName());
                carFilterRequest.put(group.getGroupName(),child.getChildName());
            }
        });
        viewHolder.rvCarFilter.setAdapter(adapter);
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    static class ViewHolder {
        @Bind(R.id.iv_back)
        ImageView ivBack;
        @Bind(R.id.tv_reset)
        TextView tvReset;
        @Bind(R.id.rl_head)
        RelativeLayout rlHead;
        @Bind(R.id.rv_carFilter)
        RecyclerView rvCarFilter;
        @Bind(R.id.tv_filter)
        TextView tvFilter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
