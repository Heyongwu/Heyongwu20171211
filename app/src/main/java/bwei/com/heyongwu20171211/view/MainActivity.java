package bwei.com.heyongwu20171211.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bwei.com.heyongwu20171211.Adapter.MyAdapter;
import bwei.com.heyongwu20171211.R;
import bwei.com.heyongwu20171211.bean.JavaBean;
import bwei.com.heyongwu20171211.persenter.MainPercenter;
import bwei.com.heyongwu20171211.widget.MyExpanableListView;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    @BindView(R.id.mlv)
    MyExpanableListView mlv;
    @BindView(R.id.main_cb)
    CheckBox mainCb;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new MainPercenter(this).getShow();
    }

    @Override
    public void showData(List<JavaBean.DataBean> list, List<List<JavaBean.DataBean.ListBean>> lists) {
        MyAdapter adapter = new MyAdapter(list, lists, this);
        mlv.setAdapter(adapter);
                mlv.setGroupIndicator(null);
        //默认让其全部展开
        for (int i = 0; i < list.size(); i++) {
            mlv.expandGroup(i);
        }
    }
}
