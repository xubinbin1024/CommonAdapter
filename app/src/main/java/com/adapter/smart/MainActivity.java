package com.adapter.smart;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.adapter.smart.bean.MocoBean;
import com.adapter.smart.common.CommonAdapter;
import com.adapter.smart.viewholder.MocoViewHolder;
import com.adapter.smart.viewholder.MocoViewHolderHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.adapter.smart.constants.ConstantUrl.MOCO_URL;
import static com.adapter.smart.constants.ConstantUrl.TESTJSON;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private MocoBean mMocoBean;
    private Context mContext;
    private List<MocoBean> mDataBeanList;
//    private MocoViewHolder mMocoViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        getDataByString();
//        getDataByNet();

    }

    private void getDataByString() {
        Gson gson = new Gson();
        mMocoBean = new MocoBean();
        mMocoBean = gson.fromJson(TESTJSON, new TypeToken<MocoBean>(){
        }.getType());

        if (mMocoBean != null) {
            //传统的写法
//                        mListView.setAdapter(new UsualAdapter(mContext,mMocoBean));
            //封装后的写法
            mListView.setAdapter(new CommonAdapter<MocoViewHolder>(mContext,mMocoBean, R.layout.list_view_item,new MocoViewHolderHelper()));
        }
    }

    private void getDataByNet() {

        OkGo.get(MOCO_URL)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.i("xxx", "onSuccess: "+s);
                        Gson gson = new Gson();
                        mMocoBean = new MocoBean();
                        mMocoBean = gson.fromJson(s, new TypeToken<MocoBean>(){
                        }.getType());

                        if (mMocoBean != null) {
                            //传统的写法
//                        mListView.setAdapter(new UsualAdapter(mContext,mMocoBean));
                            //封装后的写法
                            mListView.setAdapter(new CommonAdapter<MocoViewHolder>(mContext,mMocoBean, R.layout.list_view_item,new MocoViewHolderHelper()));
                        }
                    }
                });
    }
    private void initView() {
        mListView = (ListView) findViewById(R.id.id_listview);
    }


}
