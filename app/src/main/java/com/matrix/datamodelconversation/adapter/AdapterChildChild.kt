package com.matrix.datamodelconversation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.matrix.datamodelconversation.R
import com.matrix.datamodelconversation.databinding.RowInplayChildChildBinding
import com.matrix.datamodelconversation.model.SocketUpdate
import com.matrix.datamodelconversation.model.eventres.EventsData


class AdapterChildChild(
    val isLeftRight: Boolean,
    var mItems: MutableList<EventsData?>,
    onItemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<AdapterChildChild.MyViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null
    private var onItemSwitchListener: OnItemSwitchListener? = null
    private var context: Context? = null
    private var selectedItemPos = -1
    private var lastItemSelectedPos = -1
    var odd: Double = 0.0
    private var total: Double = 0.0
    var stake: Double = 0.0
    var tv1Click = false
    var tv2Click = false
    var tv3Click = false
    interface OnItemClickListener {
        fun onItemClick(childTransfer: EventsData)
    }

    interface OnItemSwitchListener {
        fun onItemSwitch(isLeftRight: Boolean)
    }

    private var socketUpdate: SocketUpdate? = null
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
    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.tvOneTitle.text = mItems[position]!!.event_name
        try {
            val selction = mItems[position]?.market_odds?.selections
            if (selction?.get(0)?.back_odds?.size!! > 0) {
                if (selction[0]?.back_odds?.get(0)?.price != null) {
                    holder.binding.tv1.text = selction[0]?.back_odds?.get(0)?.price.toString()
                } else if (selction[0]?.back_odds?.get(1)?.price != null && selction[0]?.back_odds?.size!! >= 2) {
                    holder.binding.tv1.text = selction[0]?.back_odds?.get(1)?.price.toString()
                } else if (selction[0]?.back_odds?.get(2)?.price != null && selction[0]?.back_odds?.size!! >= 3) {
                    holder.binding.tv1.text = selction[0]?.back_odds?.get(2)?.price.toString()
                }
            }
            if (selction[1]?.back_odds?.size!! > 0) {
                if (selction[1]?.back_odds!![0].price != null) {
                    holder.binding.tv2.text = selction[1]?.back_odds!![0].price.toString()
                } else if (selction[1]?.back_odds!![1].price != null && selction[1]?.back_odds?.size!! >= 2) {
                    holder.binding.tv2.text = selction[1]?.back_odds!![1].price.toString()
                } else if (selction[1]?.back_odds!![2].price != null && selction[1]?.back_odds?.size!! >= 3) {
                    holder.binding.tv2.text = selction[1]?.back_odds!![2].price.toString()
                }
            }
            if (selction[2]?.back_odds?.size!! > 0) {
                if (selction[2]?.back_odds!![0].price != null) {
                    holder.binding.tvX.text = selction[2]?.back_odds!![0].price.toString()
                } else if (selction[2]?.back_odds!![1].price != null && selction[2]?.back_odds?.size!! >= 2) {
                    holder.binding.tvX.text = selction[2]?.back_odds!![1].price.toString()
                } else if (selction[2]?.back_odds!![2].price != null && selction[2]?.back_odds?.size!! >= 3) {
                    holder.binding.tvX.text = selction[2]?.back_odds!![2].price.toString()
                }
            }


        } catch (ex: Exception) {

        }

        holder.binding.apply {
            if (position == selectedItemPos) {
                if (tv1Click && !tv2Click && !tv3Click) {
                    if (isLeftRight) {
                        if (holder.binding.tvX.text.toString() != "-") {
                            odd = tv1.text.toString().toDouble()
                            viewBetting.tvOddsValue.setText(holder.binding.tv1.text)
                        }
                    } else {
                        if (holder.binding.tvX.text.toString() != "-") {
                            odd = tv1.text.toString().toDouble()
                            viewBetting.tvOddsValue.setText(holder.binding.tv1.text.toString())
                        }

                    }

                }
                if (tv2Click && !tv1Click && !tv3Click) {
                    if (isLeftRight) {
                        if (holder.binding.tvX.text.toString() != "-") {
                            odd = tv2.text.toString().toDouble()
                            viewBetting.tvOddsValue.setText(holder.binding.tv2.text)
                        }

                    } else {
                        if (holder.binding.tvX.text.toString() != "-") {
                            odd = tv2.text.toString().toDouble()
                            viewBetting.tvOddsValue.setText(holder.binding.tv2.text)
                        }

                    }

                }
                if (tv3Click && !tv1Click && !tv2Click) {
                    if (isLeftRight) {
                        if (holder.binding.tvX.text.toString() != "-") {
                            odd = tvX.text.toString().toDouble()
                            viewBetting.tvOddsValue.setText(holder.binding.tvX.text)
                        }

                    } else {
                        if (holder.binding.tvX.text.toString() != "-") {
                            odd = tvX.text.toString().toDouble()
                            viewBetting.tvOddsValue.setText(holder.binding.tvX.text)
                        }

                    }

                }
                viewBetting.ro.visibility = View.VISIBLE

            } else {
                viewBetting.ro.visibility = View.GONE
            }
            viewBetting.ro.isVisible = position == selectedItemPos

            viewBetting.btnCancel.setOnClickListener {
                viewBetting.ro.isVisible = false
            }
            tv1.setOnClickListener {
                tv1Click = true
                selectedItemPos = position
                if (lastItemSelectedPos == -1) {
                    lastItemSelectedPos = selectedItemPos
                } else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                    tv2Click = false
                    tv3Click = false
                }
                notifyItemChanged(selectedItemPos)
            }
            tv2.setOnClickListener {
                tv2Click = true
                selectedItemPos = position
                if (lastItemSelectedPos == -1) {
                    lastItemSelectedPos = selectedItemPos
                } else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                    tv1Click = false
                    tv3Click = false
                }
                notifyItemChanged(selectedItemPos)
            }
            tvX.setOnClickListener {
                tv3Click = true
                selectedItemPos = position
                if (lastItemSelectedPos == -1) {
                    lastItemSelectedPos = selectedItemPos
                } else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                    tv2Click = false
                    tv1Click = false
                }
                notifyItemChanged(selectedItemPos)
            }
        }

    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class MyViewHolder(val binding: RowInplayChildChildBinding) :
        RecyclerView.ViewHolder(binding.root)

}