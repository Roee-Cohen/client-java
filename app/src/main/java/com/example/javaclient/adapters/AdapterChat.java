package com.example.javaclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaclient.R;
import com.example.javaclient.utils.Message;
import com.example.javaclient.utils.User;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.PlaceHolder> {

    private List<Message> messageChatList;
    private LayoutInflater layoutInflater;
    private Context context;


    public AdapterChat(List<Message> messageChats, Context context) {
        this.messageChatList = messageChats;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    public class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtMessageBody, txtTime;
        private RelativeLayout relativeLayoutContainerBody;

        public PlaceHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtMessageBody = view.findViewById(R.id.txtMessageBody);
            txtTime = view.findViewById(R.id.txtTime);
            relativeLayoutContainerBody = view.findViewById(R.id.relativeLayoutContainerBody);
        }
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_chat_message, parent, false);

        return new PlaceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PlaceHolder holder, final int position) {

        final Message message = messageChatList.get(position);
        holder.txtName.setText(message.getMessage().getSender());
        holder.txtMessageBody.setText(message.message.getContent());
        holder.txtTime.setText(message.getDate());
//        holder.txtTime.setText(message.getTime()); //TODO save the time of the message (with timestamp of course)
//        User user = new User("test", "test");
//        User user = SharedPreferencesHelper.getInstance(context).getUser(); //TODO save the user in shared preferences

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, message.getMessage().getContent().length()+"", Toast.LENGTH_SHORT).show();
            }
        });

        if (message.getMessage().getSender().equals(User.getApplicationUser().getUsername())) {

            RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsText.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.txtName.setLayoutParams(paramsText);

            RelativeLayout.LayoutParams paramsText2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsText2.addRule(RelativeLayout.BELOW, holder.txtName.getId());
            paramsText2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.relativeLayoutContainerBody.setLayoutParams(paramsText2);

            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) holder.relativeLayoutContainerBody.getLayoutParams();
            relativeParams.setMargins(80, 0, 0, 0);  // left, top, right, bottom
            holder.relativeLayoutContainerBody.setLayoutParams(relativeParams);


            if (message.getMessage().getContent().length()>=28){
                RelativeLayout.LayoutParams paramsText3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsText3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                paramsText3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                holder.txtTime.setLayoutParams(paramsText3);
            }else {
                RelativeLayout.LayoutParams paramsText3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsText3.addRule(RelativeLayout.LEFT_OF, holder.relativeLayoutContainerBody.getId());
                paramsText3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                holder.txtTime.setLayoutParams(paramsText3);
            }


            holder.txtTime.bringToFront();
            ViewCompat.setElevation(holder.txtTime, 3);
        } else {
            RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsText.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.txtName.setLayoutParams(paramsText);

            RelativeLayout.LayoutParams paramsText2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsText2.addRule(RelativeLayout.BELOW, holder.txtName.getId());
            paramsText2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.relativeLayoutContainerBody.setLayoutParams(paramsText2);

            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) holder.relativeLayoutContainerBody.getLayoutParams();
            relativeParams.setMargins(0, 0, 80, 0);  // left, top, right, bottom
            holder.relativeLayoutContainerBody.setLayoutParams(relativeParams);

            if (message.getMessage().getContent().length()>=28){
                RelativeLayout.LayoutParams paramsText3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsText3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                paramsText3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.txtTime.setLayoutParams(paramsText3);
            }else {
                RelativeLayout.LayoutParams paramsText3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsText3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                paramsText3.addRule(RelativeLayout.RIGHT_OF, holder.relativeLayoutContainerBody.getId());
                holder.txtTime.setLayoutParams(paramsText3);
            }
            holder.txtTime.bringToFront();
            ViewCompat.setElevation(holder.txtTime, 3);
        }
    }



    @Override
    public int getItemCount() {
        return messageChatList.size();
    }
}
