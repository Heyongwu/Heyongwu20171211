package bwei.com.heyongwu20171211.persenter;

import java.util.ArrayList;
import java.util.List;

import bwei.com.heyongwu20171211.bean.JavaBean;
import bwei.com.heyongwu20171211.mdoel.IMainModel;
import bwei.com.heyongwu20171211.mdoel.MainModel;
import bwei.com.heyongwu20171211.network.OnNetListener;
import bwei.com.heyongwu20171211.view.IMainActivity;
import retrofit2.Call;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public class MainPercenter {
    private IMainActivity iMainActivity;
    private final IMainModel imainModel;

    public MainPercenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        imainModel = new MainModel();
    }
    public void getShow(){
        imainModel.show(new OnNetListener<JavaBean>() {
            @Override
            public void Success(JavaBean javaBean) {
                List<JavaBean.DataBean> data = javaBean.getData();
                ArrayList<List<JavaBean.DataBean.ListBean>> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    List<JavaBean.DataBean.ListBean> list1 = data.get(i).getList();
                    list.add(list1);
                }
                iMainActivity.showData(data,list);
            }

            @Override
            public void Failure(Call<JavaBean> call) {

            }
        });
    }
}
