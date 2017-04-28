package com.adapter.smart.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * Created by smart on 2017/4/24.
 */

public class CommonAdapter<T extends CommonAdapter.IBaseViewHolder>  extends BaseAdapter{
    private final int mItemViewLayout;//item布局文件
    protected Context mContext;
    private T mBaseViewHolder;
    private ViewHolderCallback mViewHolderCallback;
    private BaseBean mBaseBean;
    private int listSize;

    /**
     * @param context 上下文
     * @param baseBean 数据集
     * @param itemViewLayout （item的布局文件）
     * @param viewHolderCallback （viewholder的接口）
     */
    public CommonAdapter(Context context, BaseBean baseBean,Integer listSize,int itemViewLayout, ViewHolderCallback viewHolderCallback) {
        mContext = context;
        mBaseBean = baseBean;
        mItemViewLayout = itemViewLayout;
        mViewHolderCallback = viewHolderCallback;
        if (listSize != null) {
            this.listSize = listSize;
        }else {
            this.listSize = 1;
        }

    }

    @Override
    public int getCount() {
        return listSize;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mItemViewLayout,parent,false);
            mBaseViewHolder = (T) mViewHolderCallback.initViewHolder(mBaseViewHolder,convertView);
            convertView.setTag(mBaseViewHolder);
        }else {
            mBaseViewHolder = (T)convertView.getTag();
        }
        mViewHolderCallback.bindView(mContext,mBaseBean,mBaseViewHolder,position);
        Log.i("xxx", "getView: ");
        return convertView;
    }


    public interface ViewHolderCallback<BVH extends IBaseViewHolder,BEAN extends BaseBean>{
        /** 用于初始化ViewHolder
         * @param convertView
         */
        IBaseViewHolder initViewHolder(@NonNull BVH viewHolder, @NonNull View convertView);

        /**用于设置 item中 的每一个控件
         * @param position
         */
       void bindView(Context context,BEAN beanList,BVH viewHolder,int position);
    }

    /*
    * 你所写的viewholder要继承这个BaseViewHolder
    * */
    public interface IBaseViewHolder {

    }
}
