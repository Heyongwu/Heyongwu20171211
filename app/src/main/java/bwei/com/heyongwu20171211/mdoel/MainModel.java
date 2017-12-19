package bwei.com.heyongwu20171211.mdoel;

import bwei.com.heyongwu20171211.bean.JavaBean;
import bwei.com.heyongwu20171211.network.OnNetListener;
import bwei.com.heyongwu20171211.network.RetrofitHelper;
import bwei.com.heyongwu20171211.network.ServiceAPI;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public class MainModel implements IMainModel{

    @Override
    public void show(final OnNetListener<JavaBean> onNetListener) {
        ServiceAPI serviceAPI = RetrofitHelper.getServiceAPI();
        Flowable<JavaBean> bean = serviceAPI.bean();
        bean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JavaBean>() {
                    @Override
                    public void accept(JavaBean gwcBean) throws Exception {
                        onNetListener.Success(gwcBean);
                    }
                });
    }
}
