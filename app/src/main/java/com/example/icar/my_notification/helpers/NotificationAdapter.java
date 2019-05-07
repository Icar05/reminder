package com.example.icar.my_notification.helpers;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.icar.my_notification.Model.NotificationObject;
import com.example.icar.my_notification.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifViewHolder>{

    List<NotificationObject> data;
    Context context;

    public static OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(View parentItem, int position,  View currentView, int item_id);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //constructor
    public NotificationAdapter(List<NotificationObject> data) {
        this.data = data;
    }

    @Override
    public NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_item, parent, false);
        this.context = parent.getContext();
        return new NotifViewHolder(view);
    }

    @Override //if we change values of allready create list item
    public void onBindViewHolder(NotifViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());



          holder.date.setText(TimeHelper.formatDateToString(data.get(position).getIntData()));



        holder.id.setText(data.get(position).getId());

        String temp_priority = data.get(position).getPriority();
        holder.priority.setText(temp_priority);


        if(temp_priority.equals(Priority.HIGH.toString())) {
            holder.priority.setTextColor(Color.RED);
            holder.priority.setText(context.getResources().getString(R.string.important));
        } else{
            holder.priority.setTextColor(Color.parseColor("#007f00"));
            holder.priority.setText(context.getResources().getString(R.string.not_important));
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public int deleteItem(int position)
    {
        data.remove(position);
        super.notifyItemRemoved(position);
        return data.size();
    }

    //i work with  item
    public static class NotifViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //he have us item of list, and he store values for item of this list
        TextView title, description, date, priority, id;
        ImageButton delete, edit;
        LinearLayout activation_layout;


        public NotifViewHolder(View itemView) {
            super(itemView);

//            cardView = (CardView)itemView.findViewById(R.id.cardView);
            activation_layout = (LinearLayout)itemView.findViewById(R.id.activation_layout);
            title = (TextView)itemView.findViewById(R.id.item_title);
            description = (TextView)itemView.findViewById(R.id.item_description);
            date = (TextView)itemView.findViewById(R.id.item_date);
            priority = (TextView)itemView.findViewById(R.id.item_priority);
            id = (TextView)itemView.findViewById(R.id.item_id);

            edit = (ImageButton)itemView.findViewById(R.id.item_edit);
            delete = (ImageButton)itemView.findViewById(R.id.item_delete);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
            activation_layout.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            if (NotificationAdapter.listener != null) {
                int item_id = Integer.valueOf(""+id.getText());
                NotificationAdapter.listener.onItemClick(itemView, getLayoutPosition(), view, item_id);
            }
        }
    }


}
