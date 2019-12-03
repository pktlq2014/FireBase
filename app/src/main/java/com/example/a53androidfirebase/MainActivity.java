package com.example.a53androidfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    DatabaseReference mData;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        mData = FirebaseDatabase.getInstance().getReference();

        // trường hợp 1
        // thay đổi
        mData.child("HoTen").setValue("Nguyễn Văn Tỷ");
        // thêm mới
        mData.child("HoTen").setValue("Nguyễn Văn Thiên", new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError == null)
                {
                    Toast.makeText(MainActivity.this, "Lấy Giá Trị Thành Công", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Lấy Giá Trị Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // lấy dữ liệu từ web về -> lấy all giá trị
        mData.child("HoTen").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                textView1.append(dataSnapshot.getValue().toString() + "\n");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        // trường hợp 2
        SinhVien sv = new SinhVien("Nguyễn Văn Tỷ", "Ngô Y Linh", 1997);
        mData.child("SinhVien").setValue(sv);
        // nhận giá trị từ web về textView -> lấy 1 giá trị
        mData.child("SinhVien").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                textView1.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        // trường hợp 3
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("XeMay", 2);
        mData.child("PhuongTien").setValue(map);
    }

    private void anhXa()
    {
        textView1 = findViewById(R.id.textView1);
    }
}
