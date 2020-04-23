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
    private List<Channel> data = new ArrayList<>();

    //TODO 未实现单例模式，可能会是实现多例模式
    private ChannelLad() {

    }

    public static ChannelLad getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChannelLad();
        }
        return INSTANCE;
    }

    public void setData(List<Channel> newData) {
        this.data = newData;
    }
    /**
     * 生成测试数据，用于引入网络前的项目
     */

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
        return data.get(position);
    }

    /**
     * 访问网络得到真实数据，代替以前的test()方法
     */
    public void getData(Handler handler) {
        Retrofit retrofit = RetrofitClient.get();
        ChannelApi api = retrofit.create(ChannelApi.class);
        Call<List<Channel>> call = api.getAllChannels();
        //enqueue把代码放在子线程中进行
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call,
                                   Response<List<Channel>> response) {
                if (null != response && null != response.body()) {
                    //如果网络访问成功
                    Log.d("DianDian", "网络访问成功！");
                    Log.d("DianDian", response.body().toString());
                    Message msg = new Message();
                    data = response.body();
                    handler.sendMessage(msg);
                } else {
                    Log.w("DianDian", "response没有数据！");
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                //如果网络访问失败
                Log.e("DianDian", "网络访问出错了", t);
            }
        });
    }
}