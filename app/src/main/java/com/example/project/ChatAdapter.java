package com.example.project;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
// 3번째 fragment에 붙일 adpater(프로필, 이름, 대화내역)

public class ChatAdapter extends BaseAdapter {

    ArrayList<Chat> dataset = new ArrayList<>();

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Object getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context root = parent.getContext();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) root.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat,parent,false);
        }
        Chat chat = dataset.get(position);

        ImageView ivChatIcon = convertView.findViewById(R.id.ivChatIcon);
        TextView tvChatName = convertView.findViewById(R.id.tvChatName);
        TextView tvChatMessage = convertView.findViewById(R.id.tvChatMessage);

        ivChatIcon.setImageResource(chat.getIcon());
        tvChatName.setText(chat.getName());
        tvChatMessage.setText(chat.getMsg());

        return convertView;
    }

    public void addItem(int icon, String name, String msg){
        Chat chat= new Chat(icon,name,msg);
        this.notifyDataSetChanged();
        dataset.add(chat);
    }

    public void addItem(int ic_baseline_account_circle_24, ArrayList<Chat> list2) {
        this.dataset = list2;
    }

    public void addItem(ArrayList<Chat> list) {
        this.dataset = list;
    }
}
