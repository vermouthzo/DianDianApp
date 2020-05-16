package cn.edu.sjucc.diandian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    public final static String TAG = "DianDian";
    private TextInputLayout birthdayInput, r_username, r_password1, r_password2, r_phone, r_birthday;
    private Button registerbutton;
    private Date birthday = new Date();
    private UserLab lab = UserLab.getInstance();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case UserLab.USER_REGISTER_SUCCESS:
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG)
                                .show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    case UserLab.USER_LOGIN_NET_ERROR:
                        Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_LONG)
                                .show();
                        break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        birthdayInput = findViewById(R.id.r_birthday);
        registerbutton = findViewById(R.id.register_button);
        registerbutton.setOnClickListener(v -> {
            register();
        });

        // new Builder()
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        //告诉builder我们想要的效果
        builder.setTitleText(R.string.birthday_title);
        MaterialDatePicker<Long> picker = builder.build();
        //操作日历

        //日历点击“确定”后的处理
        picker.addOnPositiveButtonClickListener(s -> {
            Log.d(TAG, "日历的结果是：" + s);
            Log.d(TAG, "标题是：" + picker.getHeaderText());
            birthday.setTime(s);
            birthdayInput.getEditText().setText(picker.getHeaderText());
        });

        birthdayInput.setEndIconOnClickListener(v -> {
            //弹出日历选择框
            Log.d(TAG, "生日图标被点击了！");
            picker.show(getSupportFragmentManager(), picker.toString());
        });
    }

    private void register() {
        User u = new User();
        boolean error = false;
        String errorMessage;
        //获取用户名
        TextInputLayout usernameInput = findViewById(R.id.username);
        String username = usernameInput.getEditText().getText().toString();
        u.setUsername(username != null ? username.toString() : "");

        //检查密码是否一致
        TextInputLayout passwordInput1 = findViewById(R.id.r_password1);
        TextInputLayout passwordInput2 = findViewById(R.id.r_password2);
        Editable password1 = passwordInput1.getEditText().getText();
        Editable password2 = passwordInput2.getEditText().getText();
        if (password1 != null && password2 != null) {
            if (!password2.toString().equals(password1.toString())) { //两次密码不相等
                error = true;
                errorMessage = "两次密码不相同";
            } else {
                u.setPassword(password1.toString());
            }
        }

        //获得手机号
        TextInputLayout phoneInput = findViewById(R.id.r_phone);
        String phone = phoneInput.getEditText().getText().toString();
        u.setUsername(phone != null ? phone.toString() : "");

        //获得性别
        RadioGroup genderGroup = findViewById(R.id.gender);
        int gender = genderGroup.getCheckedRadioButtonId();
        switch (gender) {
            case R.id.r_male:
                u.setGender("男");
            case R.id.r_female:
                u.setGender("女");
            case R.id.r_unknown:
                u.setGender("保密");
        }

        //获得生日
        u.setBirthday(birthday);

        lab.register(u, handler);
    }
}
