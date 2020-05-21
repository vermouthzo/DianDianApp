package cn.edu.sjucc.diandian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (null != msg) {
                switch (msg.what) {
                    case UserLab.USER_LOGIN_SUCCESS:
                        loginSuccess();
                        break;
                    case UserLab.USER_LOGIN_PASSWORD_ERROR:
                        loginPasswordError();
                        break;
                    case UserLab.USER_LOGIN_NET_ERROR:
                        loginFailed();
                        break;
                }
            }
        }
    };

    private TextInputLayout username, password;
    private Button loginButton, registerButton;
    private UserLab lab = UserLab.getInstance();
    private MyPreference prefs = MyPreference.getInstance();

    private void loginSuccess() {
        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_LONG)
                .show();
        //FIXME 替换tom，从服务器获取登录成功的真实用户名。
        prefs.saveUser("tom");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void loginPasswordError() {
        Toast.makeText(LoginActivity.this, "密码错误，请重试！", Toast.LENGTH_LONG)
                .show();
    }

    private void loginFailed() {
        Toast.makeText(LoginActivity.this, "服务器错误，请稍后再试！", Toast.LENGTH_LONG)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        prefs.setup(getApplicationContext());

        loginButton.setOnClickListener(v -> {
            TextInputLayout username = findViewById(R.id.username);
            TextInputLayout password = findViewById(R.id.password);
            String u = username.getEditText().getText().toString();
            String p = password.getEditText().getText().toString();
            lab.login(u, p, handler);
        });

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "正在注册！", Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
