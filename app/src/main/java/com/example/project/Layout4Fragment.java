package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// bottomNavigation 에서 매칭된 네번째 fragment
public class Layout4Fragment extends Fragment implements View.OnClickListener{
    FirebaseAuth auth;
    FirebaseUser userof;
    TextView user, result;
    Button updateuser, logout;
    CalendarView calendar;
    ImageButton menu;
    FirebaseDatabase database;
    DatabaseReference reference;
    public int day1;
    public int month1;
    public int year1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_layout4,container,false);
        setHasOptionsMenu(true); //프래그먼트에서 메뉴옵션 허용

        user =(TextView)root.findViewById(R.id.user);
        //updateuser = (Button)root.findViewById(R.id.updateuser); 버튼 주석 처리
        //logout = (Button)root.findViewById(R.id.logout);  버튼 주석 처리
        calendar = (CalendarView)root.findViewById(R.id.calendar);
        auth = FirebaseAuth.getInstance();
        userof = auth.getCurrentUser();
        menu = (ImageButton)root.findViewById(R.id.menu);
        result = (TextView)root.findViewById(R.id.result);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        //유저 닉네임 초기 설정
        String username = userof.getDisplayName();
        user.setText(username+"님 환영합니다");

        Log.e("sad", String.valueOf(year1));
        Log.e("sad1", String.valueOf(day1));
        Log.e("sad2", String.valueOf(month1));


        String a = String.valueOf(year1);
        String b = String.valueOf(month1);
        String c = String.valueOf(day1);

        Log.e("sad", a);


        String da = a+b+c;
        Log.i("sdf3",da);
        //String.valueOf(year1)+String.valueOf(month1)+String.valueOf(day1);

        reference.child("Calender").child(userof.getDisplayName()).child("2021513").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SaveCalDTO> list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SaveCalDTO geteat = snapshot1.getValue(SaveCalDTO.class);
                        String[] leat = geteat.eat;
                        Log.e("여기요", String.valueOf(leat));
//                        String lhealth = geteat.exercise;
//                        list.add(new SaveCalDTO()[]{leat,lhealth};);
//                        for(String[] gett : list){
//                            result.setText(gett.toString());
//                            Log.e("get value", gett.toString());
//                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        });


//        updateuser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PWinsertActivity.class);
//                startActivity(intent);
//            }
//        });


        //캘린더 클릭 날짜 데이터 가지고 이동
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                year1 = year;
                month1 = month+1;
                day1 = dayOfMonth;
                Intent intent = new Intent(getActivity(), CalenderSaveActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month+1);
                intent.putExtra("day",dayOfMonth);
                startActivity(intent);
            }
        });

        //상단 메뉴 바(로그아웃, 회원정보 수정 이동)
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.ex_4menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.updateus:
                                Intent intent = new Intent(getActivity(), PWinsertActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.log:
                                auth.signOut();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                return true;
                            default:
                                return false;
                        }

                    }
                });

                popupMenu.show();

            }
        });

        //logout.setOnClickListener(this);
        return root;
    }


//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.ex_4menu, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int curId = item.getItemId();
//
//        switch (curId){
//            case R.id.updateus:
//                Intent intent = new Intent(getActivity(), PWinsertActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //파이어베이스 세션 로그아웃(추후 보강 필요)
    @Override
    public void onClick(View v) {
        if (v == logout) {
            auth.signOut();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
    }


}
