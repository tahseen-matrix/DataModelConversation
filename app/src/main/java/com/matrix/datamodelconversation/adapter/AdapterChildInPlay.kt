package com.matrix.datamodelconversation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowInplayChildBinding
import com.matrix.datamodelconversation.model.EventNewModel
import com.matrix.datamodelconversation.model.eventres.EventsData


class AdapterChildInPlay(val isLeftRight: Boolean, var mItems: MutableList<EventNewModel>, onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AdapterChildInPlay.MyViewHolder>() , AdapterChildChild.OnItemClickListener{

    private var mOnItemClickListener: OnItemClickListener? = null
    private var context: Context? = null

    interface OnItemClickListener {
        fun onItemClick()
    }

    init {
        this.mOnItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        val binding =
            RowInplayChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val album = mItems[position]
        holder.binding.tvTitle.text =album.date
        holder.binding.rvChildChild.adapter = AdapterChildChild(isLeftRight,album.childItems,this)


        if (mItems[position].childItems!=null){
            holder.binding.group2.visibility = View.VISIBLE
        }else{
            holder.binding.group2.visibility = View.GONE
        }
        holder.binding.root.setOnClickListener {
            mOnItemClickListener!!.onItemClick()
        }

    }




    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class MyViewHolder(val binding: RowInplayChildBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onItemClick(childTransfer: EventsData) {
      //  (context as DashboardActivity) .alertBetDialog("profit", childTransfer)
    }




}