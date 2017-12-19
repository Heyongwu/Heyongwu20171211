package bwei.com.heyongwu20171211.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bwei.com.heyongwu20171211.R;
import bwei.com.heyongwu20171211.bean.JavaBean;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public class MyAdapter extends BaseExpandableListAdapter{
    private List<JavaBean.DataBean> groupList;
    private List<List<JavaBean.DataBean.ListBean>> childList;
    private Context context;


    public MyAdapter(List<JavaBean.DataBean> groupList, List<List<JavaBean.DataBean.ListBean>> childList, Context context) {
        this.groupList = groupList;
        this.childList = childList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MygroupViewHodler mygroupViewHodler;
        View view;
        if(convertView == null){
            mygroupViewHodler = new MygroupViewHodler();
            view = LayoutInflater.from(context).inflate(R.layout.group_item,null);
            mygroupViewHodler.group_cb = view.findViewById(R.id.group_cb);
            mygroupViewHodler.group_tv = view.findViewById(R.id.group_tv);
            mygroupViewHodler.group_xiangq = view.findViewById(R.id.group_xiangq);
            view.setTag(mygroupViewHodler);
        }else{
            view =convertView;
            mygroupViewHodler = (MygroupViewHodler) view.getTag();
        }
        JavaBean.DataBean dataBean = groupList.get(groupPosition);

        mygroupViewHodler.group_cb.setChecked(dataBean.isChecked());
        mygroupViewHodler.group_tv.setText(dataBean.getSellerName());
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final MychildViewHolder mychildViewHolder;
        View view;
        if(convertView == null){
            mychildViewHolder = new MychildViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.child_item,null);
            mychildViewHolder.child_name = view.findViewById(R.id.child_name);
            mychildViewHolder.child_msg = view.findViewById(R.id.child_msg);
            mychildViewHolder.child_num = view.findViewById(R.id.child_num);
            mychildViewHolder.child_add = view.findViewById(R.id.child_add);
            mychildViewHolder.child_del = view.findViewById(R.id.child_del);
            mychildViewHolder.child_price = view.findViewById(R.id.child_pri);
            mychildViewHolder.child_time = view.findViewById(R.id.child_time);
            mychildViewHolder.child_cb = view.findViewById(R.id.child_cb);
            view.setTag(mychildViewHolder);
        }else {
            view = convertView;
            mychildViewHolder = (MychildViewHolder) view.getTag();
        }
        final JavaBean.DataBean.ListBean listBean = childList.get(groupPosition).get(childPosition);

        mychildViewHolder.child_cb.setChecked(listBean.isChecked());
        mychildViewHolder.child_time.setText(listBean.getCreatetime());
        mychildViewHolder.child_price.setText(listBean.getPrice()+"");
        mychildViewHolder.child_msg.setText(listBean.getSubhead());
        mychildViewHolder.child_name.setText(listBean.getTitle());
        mychildViewHolder.child_num.setText(listBean.getNum()+"");
        mychildViewHolder.child_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBean.getNum();
                listBean.setNum(++num);
                if(listBean.isChecked()){
                    setCountAndPrice(true, (int) listBean.getPrice(),1);
                }
                notifyDataSetChanged();
            }
        });
        mychildViewHolder.child_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBean.getNum();
                if(num == 1){
                    return;
                }
                if(listBean.isChecked()){
                    setCountAndPrice(false, (int) listBean.getPrice(),1);
                }
                listBean.setNum(--num);
                notifyDataSetChanged();
            }
        });
        mychildViewHolder.child_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //给holder.cbChild设置点击事件
                if (mychildViewHolder.child_cb.isChecked()) {
                    listBean.setChecked(true);
                    setCountAndPrice(true, (int) (listBean.getPrice()*listBean.getNum()),listBean.getNum());
                    //if里面是判断其他的二级列表是否都选择 选择的话一级列表也都给true
                    if (isAllChildListChecked(groupPosition)) {
                        JavaBean.DataBean dataBean = groupList.get(groupPosition);
                        dataBean.setChecked(true);
                    }
                    //如何一级列表全选
                    if (isAllGroupListChecked()) {
                        Qxa(true);
                    }
                    notifyDataSetChanged();
                } else {
                    //取消后把二级列表选择false
                    listBean.setChecked(false);
                    //把一级取消的false
                    JavaBean.DataBean dataBean = groupList.get(groupPosition);
                    dataBean.setChecked(false);
                    //把全选false
                    Qxa(false);
                    setCountAndPrice(false, (int) (listBean.getPrice()*listBean.getNum()),listBean.getNum());
                    notifyDataSetChanged();
                }

            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class MygroupViewHodler{
        CheckBox group_cb;
        TextView group_tv;
        TextView group_xiangq;
    }
    class MychildViewHolder{
        CheckBox child_cb;
        TextView child_msg;
        TextView child_time;
        TextView child_price;
        TextView child_num;
        TextView child_add;
        TextView child_del;
        TextView child_name;
    }
    private void setCountAndPrice(boolean isAll,int price,int count){
        PriceAndCountEvent priceAndCountEvent = new PriceAndCountEvent();
        priceAndCountEvent.setCount(isAll ? count : -count);
        priceAndCountEvent.setPrice(isAll ? price : -price);
        EventBus.getDefault().post(priceAndCountEvent);
    }
    private PriceAndCountEvent computeEvenbut(boolean flag,int groupPostion){
        int count = 0;
        int price = 0;
        List<JavaBean.DataBean.ListBean> datasBeen = childList.get(groupPostion);
        for (int i = 0; i < datasBeen.size(); i++) {
            JavaBean.DataBean.ListBean datasBean = datasBeen.get(i);
            if(flag){
                if(!datasBean.isChecked()){
                    count++;
                    price+=datasBean.getPrice();
                }
            }else{
                if(datasBean.isChecked()){
                    count++;
                    price+=datasBean.getPrice();
                }
            }

        }
        PriceAndCountEvent priceAndCountEvent = new PriceAndCountEvent();
        priceAndCountEvent.setPrice(price);
        priceAndCountEvent.setCount(count);
        return priceAndCountEvent;
    }
    /**
     * 遍历一个二级列表其他数据，判断其它的checkbox是否也都选中
     *
     * @return
     */
    public boolean isAllChildListChecked(int groupPostion) {
        List<JavaBean.DataBean.ListBean> datasBeen = childList.get(groupPostion);
        for (int i = 0; i < datasBeen.size(); i++) {
            JavaBean.DataBean.ListBean datasBean = datasBeen.get(i);
            if (!datasBean.isChecked()) {
                return false;
            }
        }
        return true;
    }


    /**
     * 改变MainActivity里的全选按钮状态
     * <p>
     * //     * @param isChecked
     */
    private void Qxa(boolean isChecked) {
        EventBut eventBut = new EventBut();
        eventBut.setChecked(isChecked);
        EventBus.getDefault().post(eventBut);
    };




    //判断一级了列表是否全部选择
    public boolean isAllGroupListChecked() {

        for (int i = 0; i < groupList.size(); i++) {
            JavaBean.DataBean dataBean = groupList.get(i);
            if (!dataBean.isChecked()) {
                return false;
            }
        }
        return true;
    }


    /**
     * 设置二级列表是否全选
     *
     * @param groupPosition
    //     * @param isSelectAll   true 表示全选 false 表示全不选
     */
    private void isSelectChildCheck(int groupPosition, boolean b) {
        List<JavaBean.DataBean.ListBean> datasBeen = childList.get(groupPosition);
        for (int i = 0; i <datasBeen.size() ; i++) {
            JavaBean.DataBean.ListBean datasBean = datasBeen.get(i);
            datasBean.setChecked(b);
        }
        notifyDataSetChanged();
    }


    //判断一级是否全选 给主页面调用该方法
    public void SelectAllGroupListChecked(boolean isChecked) {
        int count = 0 ;
        int price = 0 ;
        for (int i = 0; i < childList.size(); i++) {
            List<JavaBean.DataBean.ListBean> datasBeen = childList.get(i);
            count += datasBeen.size();
            for (int j = 0; j < datasBeen.size(); j++) {
                price += datasBeen.get(j).getPrice();
            }
        }

        setCountAndPrice(isChecked,price,count);


        for (int i = 0; i < groupList.size(); i++) {
            JavaBean.DataBean dataBean = groupList.get(i);
            //一级是否全选
            dataBean.setChecked(isChecked);
            //二级是否全选
            isSelectChildCheck(i,isChecked);
        }
        notifyDataSetChanged();

    }

}
