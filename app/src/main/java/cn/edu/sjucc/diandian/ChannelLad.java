package cn.edu.sjucc.diandian;

import android.os.Message;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * 数据源,这里存放了所有的数据。
 * 使用单例模式保证这一类
 */
public class ChannelLad {
    private static ChannelLad INSTANCE = null;
    private List<Channel> data;

    //TODO 未实现单例模式，可能会是实现多例模式
    private ChannelLad() {
        //初始化空白列表
        data = new ArrayList<>();
    }

    public static ChannelLad getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChannelLad();
        }
        return INSTANCE;
    }

    /**
     * 获取当前数据源中共有多少个频道
     *
     * @return
     */
    public int getSize() {
        return data.size();
    }

    /**
     * 获取一个指定频道
     *
     * @param position 频道的序号
     * @return 频道对象Channel
     */
    public Channel getChannel(int position) {
        return this.data.get(position);
    }

    /**
     * 访问网络得到真实数据，代替以前的test()方法
     */
    public void getData(Handler handler) {
        //调用单例
        Retrofit retrofit = RetrofitClient.getInstance();
        ChannelApi api = retrofit.create(ChannelApi.class);
        Call<List<Channel>> call = api.getAllChannels();
        //enqueue会自己生成子线程， 去执行后续代码
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call,
                                   Response<List<Channel>> response) {
                if (null != response && null != response.body()) {
                    Log.d("DianDian", "从阿里云得到的数据是：");
                    Log.d("DianDian", response.body().toString());
                    data = response.body();
                    //发出通知
                    Message msg = new Message();
                    msg.what = 1;  //自己规定1代表从阿里云获取数据完毕
                    handler.sendMessage(msg);
                } else {
                    Log.w("DianDian", "response没有数据！");
                    Message msg = new Message();
                    msg.what = 4;
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                //如果网络访问失败
                Log.e("DianDian", "网络访问出错了", t);
                Message msg = new Message();
                msg.what = 4;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 替换之前的getChannelData()，获取热门评论
     *
     * @param channelId 频道编号
     * @param handler
     */
    public void getHotComments(String channelId, Handler handler) {
        //调用单例
        Retrofit retrofit = RetrofitClient.getInstance();
        ChannelApi api = retrofit.create(ChannelApi.class);
        Call<List<Comment>> call = api.getHotComments(channelId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.body() != null) {
                    Log.d("DianDian", "服务器返回的热门评论是：");
                    Log.d("DianDian", response.body().toString());
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = response.body();
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("DianDian", "访问网络失败！", t);
                Message msg = new Message();
                msg.what = 4;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 添加评论
     */
    public void addComment(String channelId, Comment comment, Handler handler) {
        //调用单例
        Retrofit retrofit = RetrofitClient.getInstance();
        ChannelApi api = retrofit.create(ChannelApi.class);
        Call<Channel> call = api.addComment(channelId, comment);
        call.enqueue(new Callback<Channel>() {
            @Override
            public void onResponse(Call<Channel> call, Response<Channel> response) {
                Log.d("DianDian", "添加评论后服务器返回的数据是：");
                Log.d("DianDian", response.body().toString());
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<Channel> call, Throwable t) {
                Log.e("DianDian", "访问网络失败！", t);
                Message msg = new Message();
                msg.what = 4;
                handler.sendMessage(msg);
            }
        });
    }
}