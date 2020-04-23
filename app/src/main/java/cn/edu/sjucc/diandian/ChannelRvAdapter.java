package cn.edu.sjucc.diandian;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.math.RoundingMode;

public class ChannelRvAdapter extends RecyclerView.Adapter<ChannelRvAdapter.ChannelRowHolder> {

    private ChannelLad lad = ChannelLad.getInstance();
    private ChannelClickListener listener;
    private Context context;

    public ChannelRvAdapter(Context context, ChannelClickListener lis) {
        this.listener = lis;
        this.context = context;
    }

    /**
     * 当需要新的一行时，此方法负责创建这一行对应得对象，即ChannelRowHolder对象.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ChannelRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_row, parent, false);
        ChannelRowHolder holder = new ChannelRowHolder(rowView);

        return holder;
    }

    /**
     * 用于确定列表总共有几行（即多少个ChannelRowHolder对象）
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //TODO 此处暂时硬编码为30行
        return lad.getSize();
    }

    /**
     * 用于确定每一行的内容是什么，即填充行中各个视图的内容。
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ChannelRowHolder holder, int position) {
        Channel c = lad.getChannel(position);
        holder.bind(c);
    }

    //自定义新接口
    public interface ChannelClickListener {
        public void onChannelClick(int position);
    }

    /**
     * 单行布局对应得Java控制类
     */
    public class ChannelRowHolder extends RecyclerView.ViewHolder {
        private TextView title; //频道标题
        private TextView quality;
        private ImageView cover;

        public ChannelRowHolder(@NonNull View row) {
            super(row);
            this.title = row.findViewById(R.id.channel_title);
            this.quality = row.findViewById(R.id.channel_quality);
            this.cover = row.findViewById(R.id.channel_cover);
            row.setOnClickListener((v) -> {
                int position = getLayoutPosition();
                Log.d("DianDian", position + 1 + "行被点击啦！");
                //TODO 调用时机的跳转代码
                listener.onChannelClick(position);
            });

        }

        /**
         * 自定义方法，用于向内部的title修改
         *
         * @param c
         */
        public void bind(Channel c) {
            this.title.setText(c.getTitle());
            this.quality.setText(c.getQuality());

            //图片圆角处理
            RoundedCorners rc = new RoundedCorners(50);
            RequestOptions ro = RequestOptions.bitmapTransform(rc)
                    .override(300, 300);

            //获得上下文
            Glide.with(context)
                    .load(c.getCover())
                    .placeholder(R.drawable.cctv1)
                    .apply(ro)
                    .into(this.cover);


        }
    }
}
