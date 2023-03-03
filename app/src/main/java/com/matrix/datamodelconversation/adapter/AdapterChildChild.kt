package com.matrix.datamodelconversation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowInplayChildChildBinding
import com.matrix.datamodelconversation.model.eventres.EventsData


class AdapterChildChild(
    val isLeftRight: Boolean,
    var mItems: MutableList<EventsData?>,
    onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AdapterChildChild.MyViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null
    private var onItemSwitchListener: OnItemSwitchListener? = null
    private var context: Context? = null

    interface OnItemClickListener {
        fun onItemClick(childTransfer: EventsData)
    }

    interface OnItemSwitchListener {
        fun onItemSwitch(isLeftRight: Boolean)
    }

    init {
        this.mOnItemClickListener = onItemClickListener
        this.onItemSwitchListener = onItemSwitchListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        val binding =
            RowInplayChildChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }
    @SuppressLint("ClickableViewAccessibility", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvOneTitle.text = mItems[position]!!.event_name
        try {
            val selction = mItems[position]?.market_odds?.selections

            if (selction?.get(0)?.back_odds?.size!!  > 0){
                if (selction?.get(0)?.back_odds?.get(0)?.price != null){
                    holder.binding.tv1.text = selction?.get(0)?.back_odds?.get(0)?.price.toString()
                }else if (selction?.get(0)?.back_odds?.get(1)?.price != null && selction?.get(0)?.back_odds?.size!! >= 2 ){
                    holder.binding.tv1.text = selction?.get(0)?.back_odds?.get(1)?.price.toString()
                }else if (selction?.get(0)?.back_odds?.get(2)?.price != null && selction?.get(0)?.back_odds?.size!! >= 3 ){
                    holder.binding.tv1.text = selction?.get(0)?.back_odds?.get(2)?.price.toString()
                }
            }

            if (selction?.get(1)?.back_odds?.size!!  > 0){
                if (selction?.get(1)?.back_odds!![0].price != null){
                    holder.binding.tv2.text = selction?.get(1)?.back_odds!![0].price.toString()
                }else if (selction?.get(1)?.back_odds!![1].price != null && selction?.get(1)?.back_odds?.size!! >= 2 ){
                    holder.binding.tv2.text = selction?.get(1)?.back_odds!![1].price.toString()
                }else if (selction?.get(1)?.back_odds!![2].price != null && selction?.get(1)?.back_odds?.size!! >= 3 ){
                    holder.binding.tv2.text = selction?.get(1)?.back_odds!![2].price.toString()
                }
            }
            if (selction?.get(2)?.back_odds?.size!!  > 0){
                if (selction?.get(2)?.back_odds!![0].price != null){
                    holder.binding.tvX.text = selction?.get(2)?.back_odds!![0].price.toString()
                }else if (selction?.get(2)?.back_odds!![1].price != null && selction?.get(2)?.back_odds?.size!! >= 2 ){
                    holder.binding.tvX.text = selction?.get(2)?.back_odds!![1].price.toString()
                }else if (selction?.get(2)?.back_odds!![2].price != null && selction?.get(2)?.back_odds?.size!! >= 3 ){
                    holder.binding.tvX.text = selction?.get(2)?.back_odds!![2].price.toString()
                }
            }

         /*   holder.binding.tv1.text =
                mItems[position]?.market_odds?.selections?.get(0)?.back_odds?.get(0)?.price?.toString()
            holder.binding.tv2.text =
                mItems[position]?.market_odds?.selections?.get(1)?.back_odds?.get(0)?.price?.toString()
            holder.binding.tvX.text =
                mItems[position]?.market_odds?.selections?.get(2)?.back_odds?.get(0)?.price?.toString()*/

        } catch (ex: Exception) {

        }

    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class MyViewHolder(val binding: RowInplayChildChildBinding) :
        RecyclerView.ViewHolder(binding.root)

}