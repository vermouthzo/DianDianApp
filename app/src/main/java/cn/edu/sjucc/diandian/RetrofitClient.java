package cn.edu.sjucc.diandian;

import com.squareup.moshi.Moshi;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * 使用单例模式创建Retrofit，避免资源的浪费。
 */
public class RetrofitClient {
    private static Retrofit INSTANCE = null;

    public static Retrofit getInstance() {
        if (INSTANCE == null) {

            Moshi moshi = new Moshi.Builder()
                    .add(new MyDateAdapter())
                    .build();

            INSTANCE = new Retrofit.Builder()
                    .baseUrl("http://120.24.109.59:8080") //阿里ip
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build();
        }
        return INSTANCE;
    }
}
