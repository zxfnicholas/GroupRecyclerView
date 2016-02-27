package com.che.grouprecycleview.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.che.grouprecycleview.R;
import com.che.grouprecycleview.bean.Child;
import com.che.grouprecycleview.bean.Group;
import com.che.grouprecycleview.response.getCarFilterParamsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yutianran on 16/2/27.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private static final int VIEW_TYPE_GROUP = R.layout.item_group;
    private static final int VIEW_TYPE_CHILD = R.layout.item_child;


    private List<Object> mDataArrayList = new ArrayList<>();
    private List<Group> groups;
    private Map<Group, Boolean> groupsStatus;//记录是否展开
    private Map<Group, List<Child>> groupsData;
    private Map<Child, Boolean> childsStatus;//记录是否显示选中
    private Map<Child, Group> childsType;

    private Context mContext;
    private GridLayoutManager gridLayoutManager;
    private OnAdapterClickLisenter onAdapterClickLisenter;

    public GroupAdapter(Context mContext, GridLayoutManager mGridLayoutManager, OnAdapterClickLisenter lisenter) {
        this.mContext = mContext;
        this.gridLayoutManager = mGridLayoutManager;
        this.onAdapterClickLisenter = lisenter;
        this.gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isGroup(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }

    public void setData(getCarFilterParamsResponse response) {
        groups = response.getData();
        groupsStatus = new HashMap<>();
        groupsData = new HashMap<>();
        childsStatus=new HashMap<>();
        childsType=new HashMap<>();
        for (Group group : groups) {
            groupsStatus.put(group, true);
            groupsData.put(group, group.getGroupData());
            for (int i = 0; i < group.getGroupData().size(); i++) {
                childsStatus.put(group.getGroupData().get(i), i==0?true:false);
                childsType.put(group.getGroupData().get(i),group);
            }
        }
        refreshData();
    }

    private void refreshData() {
        mDataArrayList.clear();
        for (Group group : groups) {
            mDataArrayList.add(group);
            if (groupsStatus.get(group)) {
                mDataArrayList.addAll(groupsData.get(group));
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.viewType) {
            case VIEW_TYPE_GROUP:
                final Group group = (Group) mDataArrayList.get(position);
                holder.tvGroupName.setText(group.getGroupName());
                holder.tvGroupDesc.setText(group.getGroupDesc());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAdapterClickLisenter.onGroupClick(group);
                        refreshGroupStatus(group);
                    }
                });
                break;

            case VIEW_TYPE_CHILD:
                final Child child = (Child) mDataArrayList.get(position);
                holder.tvChildName.setText(child.getChildName());
                if(childsStatus.get(child)){
                    holder.tvChildName.setBackgroundResource(R.drawable.status_on);
                }else {
                    holder.tvChildName.setBackground(null);
                }
                holder.tvChildName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAdapterClickLisenter.onChildClick(childsType.get(child), child);
                        refreshChildStatus(child);
                    }
                });
                break;
        }
    }

    private void refreshChildStatus(Child child) {
        boolean mStatus=!childsStatus.get(child);
        childsStatus.put(child, mStatus);
        for(Child c:childsType.get(child).getGroupData()){
            if(c!=child){
                childsStatus.put(c, !mStatus);
            }
        }
        refreshData();
    }

    private void refreshGroupStatus(Group group) {
        groupsStatus.put(group, !groupsStatus.get(group));
        refreshData();
    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isGroup(position))
            return VIEW_TYPE_GROUP;
        else {
            return VIEW_TYPE_CHILD;
        }
    }

    private boolean isGroup(int position) {
        return mDataArrayList.get(position) instanceof Group;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        int viewType;
        //group
        TextView tvGroupName;
        TextView tvGroupDesc;
        //child
        TextView tvChildName;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            switch (viewType) {
                case VIEW_TYPE_GROUP:
                    tvGroupName = (TextView) view.findViewById(R.id.tv_groupName);
                    tvGroupDesc = (TextView) view.findViewById(R.id.tv_groupDesc);
                    break;
                case VIEW_TYPE_CHILD:
                    tvChildName = (TextView) view.findViewById(R.id.tv_childName);
                    break;
            }
        }
    }

    public interface OnAdapterClickLisenter {
        void onGroupClick(Group group);

        void onChildClick(Group group,Child child);
    }
}
