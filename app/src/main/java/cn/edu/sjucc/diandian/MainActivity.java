package cn.edu.sjucc.diandian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class MainActivity extends AppCompatActivity {
    private RecyclerView channelRv;
    private ChannelLad lad = ChannelLad.getInstance();
    private ChannelRvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.channelRv = findViewById(R.id.channel_rv);
        rvAdapter = new ChannelRvAdapter(this, p -> {
            //跳转到新界面，使用意图Intent
            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            //通过位置p得到当前频道channel
            Channel c = lad.getChannel(p);
            intent.putExtra("Channel", c);
            startActivity(intent);
        });

        initData();
        this.channelRv.setAdapter(rvAdapter);
        this.channelRv.setLayoutManager(new LinearLayoutManager(this));

    }

    //初始化即将显示的数据
    private void initData() {
        //得到网络上的数据后，去更新界面
        Handler handler = new Handler() {
            //快捷键 ctrl o
            @Override
            public void handleMessage(@NonNull Message msg) {
                //若收到了来自其他线程的数据，则运行以下代码
                rvAdapter.notifyDataSetChanged();
            }
        };
        lad.getData(handler);
    }
}
