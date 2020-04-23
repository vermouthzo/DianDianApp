package cn.edu.sjucc.diandian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PictureActivity extends AppCompatActivity {

    private ImageView avater;
    private Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        avater = findViewById(R.id.avater);
        download = findViewById(R.id.download);

        download.setOnClickListener(v -> {
            Glide.with(this)
                    .load("https://images.idgesg.net/images/article/2019/01/android-robot-security-100785201-large.3x2.jpg")
                    .into(avater);
        });
    }
}
