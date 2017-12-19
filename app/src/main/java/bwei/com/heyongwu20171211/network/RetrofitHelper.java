package bwei.com.heyongwu20171211.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private static ServiceAPI serviceAPI;
    static {
        intiokHttpClient();
    }

    public static OkHttpClient intiokHttpClient() {
        if(okHttpClient == null){
            synchronized (RetrofitHelper.class){
                if(okHttpClient == null){
                    okHttpClient = new OkHttpClient.Builder()

                            .build();
                }
            }
        }
        return okHttpClient;
    }
    public static ServiceAPI getServiceAPI(){
        if(serviceAPI == null){
            synchronized (ServiceAPI.class){
                if(serviceAPI == null){
                    serviceAPI = Oncreate(ServiceAPI.class,Api.HOST);
                }
            }
        }
        return serviceAPI;
    }
    public static <T> T Oncreate(Class<T> tClass,String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(tClass);
    }
}
