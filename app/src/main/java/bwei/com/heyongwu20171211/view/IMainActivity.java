package bwei.com.heyongwu20171211.view;

import java.util.List;

import bwei.com.heyongwu20171211.bean.JavaBean;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public interface IMainActivity {
    void showData(List<JavaBean.DataBean> list, List<List<JavaBean.DataBean.ListBean>> lists);
}
