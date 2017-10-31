package com.example.firstkotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.firstkotlin.MainAdapter.MainHolder
import com.example.firstkotlin.listener.OnItemClickListener

/**
 * Created by data on 2017/10/27.
 */
class MainAdapter (var mContext : Context,var list : List<Catalog.Result>): Adapter<MainHolder>() {

    private var mListener:OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MainHolder?, position: Int) {
        var item = list.get(position)
        holder!!.textView.setText(item.catalog)
//        holder.itemView.setOnClickListener(View.OnClickListener {
//            //没有执行
//            fun onClick(view:View){
//                mListener?.onItemClick(view,position)//没传递position
//            }
//        })
        /**
         * lambda表达式
         * -> 前是参数和返回值，-> 后是函数体
         * view:View -> Unit -> println()  :  两个->中间的关键字为返回类型
         */

        holder.itemView.setOnClickListener{view: View? -> mListener?.onItemClick(view!!,position)}//view声明？，则使用时要用!!表示不为空，而不能再用?

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder {
        var view : View = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,parent,false)
        view.setBackgroundResource(R.drawable.listitem_touch_bg)
        return MainHolder(view)
    }

    class MainHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tag : String = "MainHolder"
        var textView : TextView

        init {
            textView = itemView?.findViewById(android.R.id.text1) as TextView
        }
    }
}