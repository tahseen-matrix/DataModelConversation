package com.matrix.datamodelconversation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.databinding.RowInplayOpenbetsBinding
import com.matrix.datamodelconversation.databinding.RowMostPopularHeaderBinding
import com.matrix.datamodelconversation.model.EventNewModel


class AdapterBothMatch(var isLeftRight:Boolean, var itemList: MutableList<Int>, var mItems: MutableList<EventNewModel>, var mostList: MutableList<EventNewModel>, onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AdapterBothMatch.MyViewHolder>(),
    AdapterChildInPlay.OnItemClickListener {

    private var mOnItemClickListener: OnItemClickListener? = null
    private var context: Context? = null
    private var value:String?= "onRight"
    companion object {
        private const val IN_PLAY = 0
        private const val MOST_POPULAR = 2
    }

    interface OnItemClickListener {
        fun onItemClick()
    }

    init {
        this.mOnItemClickListener = onItemClickListener
    }

    fun updateAdapter(newItemList: MutableList<Int>, newMItems: MutableList<EventNewModel>, newMostList: MutableList<EventNewModel>){
        itemList.clear()
        mItems.clear()
        mostList.clear()
        itemList.addAll(newItemList)
        mItems.addAll(newMItems)
        mostList.addAll(newMostList)
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        val binding = when (viewType) {
            IN_PLAY -> RowInplayOpenbetsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            MOST_POPULAR -> RowMostPopularHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            else -> throw IllegalArgumentException("Invalid type")

        }
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (itemList[position] == IN_PLAY) {
            holder.bindInPlay()

        } else {
            holder.bindMostPopular()
        }


    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position]
    }


    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class MyViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root),
        AdapterChildInPlay.OnItemClickListener {
        @SuppressLint("ClickableViewAccessibility")
        fun bindInPlay() {
            val view = binding as RowInplayOpenbetsBinding
            view.rvChildInplay.adapter = AdapterChildInPlay(isLeftRight,mItems,this)

        }

        @SuppressLint("ClickableViewAccessibility")
        fun bindMostPopular() {
            val view = binding as RowMostPopularHeaderBinding
            view.rvChildMostPopular.adapter = AdapterChildInPlay(isLeftRight,mostList,this)

        }

        override fun onItemClick() {

        }

    }

    override fun onItemClick() {

    }


}