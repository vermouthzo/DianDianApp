package cn.edu.sjucc.diandian;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * 使用单例模式创建Retrofit，避免资源的浪费。
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit get() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://120.24.109.59:8080") //阿里ip
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
