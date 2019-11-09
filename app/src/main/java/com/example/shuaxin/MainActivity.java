package com.example.shuaxin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Student student;
    List<Student> datalist =new ArrayList<>();
    StudentApaptert apaptert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        apaptert = new StudentApaptert(datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(apaptert);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata();
            }
        });
        loaddata();
    }

    private void loaddata() {
        String url = "http://crudtest.rdxer.com/student";
        //第一步获取okHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //第二步构建Request对象
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        //第三步构建Call对象
        Call call = client.newCall(request);

    //第四步:异步get请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure", e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray jsonArray =jsonObject.getJSONArray("content");
                List<Student> list= new ArrayList<>();
                for (int i = 0; i <jsonArray.size() ; i++) {
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                    Student student=jsonArray1.toJavaObject(Student.class);
                    list.add(student);
                }
                datalist.clear();
                datalist.addAll(list);
                // 数据刷新必须在主线程中
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      apaptert.notifyDataSetChanged();
                      swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
}
