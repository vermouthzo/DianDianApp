package cn.edu.sjucc.diandian;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 用来处理Shared preference 中保存数据，简化代码。
 */
public class MyPreference {
    private static MyPreference INSTANCE = null;
    private Context context = null;
    private SharedPreferences prefs;

    private MyPreference() {

    }

    public static MyPreference getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new MyPreference();
        }
        return INSTANCE;
    }

    public void setup(Context ctx) {
        context = ctx;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveUser(String username) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user", username);
        editor.apply();
    }

    public String currentUser() {
        return prefs.getString(UserLab.USER_CURRENT, "未登录！");
    }
}
