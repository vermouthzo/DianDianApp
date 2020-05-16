package cn.edu.sjucc.diandian;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @POST("/user/register")
    Call<User> register(@Body User user);
}
