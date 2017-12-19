package bwei.com.heyongwu20171211.mdoel;

import bwei.com.heyongwu20171211.bean.JavaBean;
import bwei.com.heyongwu20171211.network.OnNetListener;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public interface IMainModel {
    void show(OnNetListener<JavaBean> onNetListener);
}
