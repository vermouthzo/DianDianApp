package cn.edu.sjucc.diandian;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @GET("/user/login/{username}/{password}")
    Call<Integer> login(@Path("username") String username, @Path("password") String password);
}
