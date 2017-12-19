package bwei.com.heyongwu20171211.network;

import bwei.com.heyongwu20171211.bean.JavaBean;
import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public interface ServiceAPI {
//    http://120.27.23.105/product/getCarts?uid=100
    @GET(Api.CARTS)
    public Flowable<JavaBean> bean();
}
