package cn.edu.sjucc.diandian;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DataSource;


public class PlayerActivity extends AppCompatActivity {

    private PlayerView tvPlayerView;
    private SimpleExoPlayer player;
    private MediaSource videoSource;
    private Channel channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        channel = (Channel) getIntent().getSerializableExtra("Channel");
        updateUI();
        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clean();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player == null) {
            init();
            if (tvPlayerView != null) {
                tvPlayerView.onResume();
            }
            clean();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tvPlayerView != null) {
            tvPlayerView.onResume();
        }
    }

    //重构。初始化播放器
    private void init() {
        if (player == null) {
            //创建播放器
            player = ExoPlayerFactory.newSimpleInstance(this);
            player.setPlayWhenReady(true);
            //绑定界面与播放器
            PlayerView tvPlayerView = findViewById(R.id.tv_player);
            //
            tvPlayerView.setPlayer(player);
            //准备播放器
            Uri videoUrl = Uri.parse(channel.getUrl());
            DataSource.Factory factory = new DefaultDataSourceFactory(this, "DianDian");
            videoSource = new HlsMediaSource.Factory(factory).createMediaSource(videoUrl);
        }
        //把播放源传给播放器(并开始播放)
        player.prepare(videoSource);
    }

    /**
     * 初始化界面
     */
    private void updateUI() {
        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(this.channel.getTitle());
        //ToDO 修改当前频道清晰度
        TextView tvQuality = findViewById(R.id.tv_quality);
        tvQuality.setText(this.channel.getQuality());
    }

    //重构。释放与清理资源
    private void clean() {
        if (player != null) {
            player.release();
            player = null;
            videoSource = null;
        }
    }
}
