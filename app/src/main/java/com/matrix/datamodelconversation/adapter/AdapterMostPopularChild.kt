package com.matrix.datamodelconversation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowMostPopularChildBinding


class AdapterMostPopularChild(onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AdapterMostPopularChild.MyViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null
    private var context: Context? = null
    var selectedItemPos = -1
    var lastItemSelectedPos = -1

    var tv1Click = false
    var tv2Click = false


    interface OnItemClickListener {
        fun onItemClick()
    }

    init {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        val binding =
            RowMostPopularChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.tvTime.text = "Today \n12:45"


    }

    fun setValue(minmax: AppCompatEditText, i: Int, initValue: String) {
        var value:Int=0
        if (minmax.text.toString().isNotEmpty()){
            value= minmax.text.toString().toInt()
            value += i
            minmax.setText(value.toString())
        }else{
            minmax.setText(initValue)
        }
    }


    override fun getItemCount(): Int {
        return 2
    }

    inner class MyViewHolder(val binding: RowMostPopularChildBinding) :
        RecyclerView.ViewHolder(binding.root)


}