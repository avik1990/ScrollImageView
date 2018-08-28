package com.app.imageanim;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserApapter extends RecyclerView.Adapter<UserApapter.Viewholder> {

    ClickEventLisener clickEventLisener;
    Context context;
    List<Users> list_user;
    int layout;


    public UserApapter(Context context, int layout, List<Users> list_user, ClickEventLisener clickEventLisener) {
        this.context = context;
        this.clickEventLisener = clickEventLisener;
        this.list_user = list_user;
        this.layout = layout;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Users user = list_user.get(position);
        holder.tv_name.setText(user.getName());
        clickEventLisener.Currentposition(position);
    }

    @Override
    public int getItemCount() {
        return list_user.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView tv_name;

        public Viewholder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
