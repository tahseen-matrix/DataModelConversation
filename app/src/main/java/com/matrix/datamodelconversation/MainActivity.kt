package com.matrix.datamodelconversation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.matrix.datamodelconversation.adapter.AdapterChildChild
import com.matrix.datamodelconversation.adapter.AdapterChildInPlay
import com.matrix.datamodelconversation.adapter.AdapterChildMostPopular
import com.matrix.datamodelconversation.databinding.ActivityMainBinding
import com.matrix.datamodelconversation.model.EventNewModel
import com.matrix.datamodelconversation.model.eventres.*
import com.matrix.datamodelconversation.model.socketres.SocketRes

class MainActivity : AppCompatActivity(), AdapterChildInPlay.OnItemClickListener,
    AdapterChildMostPopular.OnItemClickListener, AdapterChildChild.OnItemClickListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var itemsArrayList: MutableList<EventNewModel>? = ArrayList()
    private var mostPopularList: MutableList<EventNewModel>? = ArrayList()

    private val inPlayCricket: MutableList<EventsData> = mutableListOf()
    private val inPlayTennis: MutableList<EventsData> = mutableListOf()
    private val inPlayFootball: MutableList<EventsData> = mutableListOf()

    private val cricketData: MutableList<EventsData> = mutableListOf()
    private val tennisData: MutableList<EventsData> = mutableListOf()
    private val footballData: MutableList<EventsData> = mutableListOf()

    private val selectionMainList: MutableList<Selection?> = mutableListOf()


    private val adapterChildInPlay: AdapterChildInPlay = AdapterChildInPlay(true, ArrayList(), this)
    private val adapterChildMostPopular: AdapterChildMostPopular =
        AdapterChildMostPopular(true, ArrayList(), this)

    var market: EventRes? = null

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val jsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("indicator", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }

        val gson = Gson()
        market = gson.fromJson(jsonData, EventRes::class.java)

        setData(market!!)
        val socketJsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("socket", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }

        val socketRes: SocketRes = gson.fromJson(socketJsonData, SocketRes::class.java)

        binding.connectBtn.setOnClickListener {
            selectionMainList.clear()
            var inPlay: Int? = null
            for (i in market!!.events_data.indices) {
                if (market!!.events_data[i].id == socketRes.MI) {
                    Log.d("Tag", "response ${market!!.events_data[i].market_odds?.selections}")


                    if (market!!.events_data[i].id == socketRes.MI) {
                        for (j in market!!.events_data[i].market_odds?.selections?.indices!!) {
                            val resId =
                                market!!.events_data[i].market_odds?.selections?.get(j)?.selection_id
                            val socketId = socketRes.message?.BF?.SL?.get(j)?.I
                            if (resId == socketId) {
                                val bo: MutableList<BackOdd> = mutableListOf()
                                val lo: MutableList<LayOdd> = mutableListOf()
                                if (socketRes.message?.BF?.SL?.get(j)?.BO?.size!! > 0) {
                                    if (socketRes.message.BF.SL[j]?.BO?.get(0) != null)
                                        bo.add(BackOdd(socketRes.message.BF.SL[j]?.BO?.get(0)?.O,
                                            socketRes.message.BF.SL[j]?.BO?.get(0)?.S,
                                            socketRes.message.BF.SL[j]?.BO?.get(0)?.I))
                                }

                                if (socketRes.message.BF.SL[j]?.LO?.size!! > 0) {
                                    if (socketRes.message.BF.SL[j]?.LO?.get(0) != null)
                                        lo.add(LayOdd(socketRes.message.BF.SL[j]?.LO?.get(0)?.O,
                                            socketRes.message.BF.SL[j]?.LO?.get(0)?.S,
                                            socketRes.message.BF.SL[j]?.LO?.get(0)?.I))
                                }

                                selectionMainList.add(Selection(bo, lo, socketId, "ACTIVE"))
                                market!!.events_data[i].market_odds?.selections?.set(j,
                                    selectionMainList[j])
                            }
                        }
                        Log.d("TAG", selectionMainList.toString())

                    }


                }
            }


        }

        binding.rvChildInplay.adapter = adapterChildInPlay
        binding.rvChildMostPopular.adapter = adapterChildMostPopular
        adapterChildInPlay.notifyDataSetChanged()
        adapterChildMostPopular.notifyDataSetChanged()
    }

    private fun setData(market: EventRes) {
        inPlayFootball.clear()
        inPlayCricket.clear()
        inPlayTennis.clear()
        cricketData.clear()
        tennisData.clear()
        footballData.clear()
        for (i in market.events_data.indices) {
            if (market.events_data[i].sport_id == AppConstants.SportType.CRICKET && market.events_data[i].currently_live == 1) {
                inPlayCricket.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.FOOTBALL && market.events_data[i].currently_live == 1) {
                inPlayFootball.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.TENNIS && market.events_data[i].currently_live == 1) {
                inPlayTennis.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.CRICKET && market.events_data[i].currently_live == 0) {
                cricketData.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.FOOTBALL && market.events_data[i].currently_live == 0) {
                footballData.add(market.events_data[i])
            } else if (market.events_data[i].sport_id == AppConstants.SportType.TENNIS && market.events_data[i].currently_live == 0) {
                tennisData.add(market.events_data[i])
            }
        }

        Log.e("LoginActivity",
            "from tennis ${inPlayTennis} \n from cricket ${inPlayCricket} \nfrom fotball ${inPlayFootball}")
        if (inPlayCricket.size > 0) {
            itemsArrayList!!.add(EventNewModel("Cricket", inPlayCricket.toMutableList()))
        }
        if (inPlayTennis.size > 0) {
            itemsArrayList!!.add(EventNewModel("Tennis", inPlayTennis.toMutableList()))
        }
        if (inPlayFootball.size > 0) {
            itemsArrayList!!.add(EventNewModel("Football", inPlayFootball.toMutableList()))
        }
        if (cricketData.size > 0) {
            mostPopularList!!.add(EventNewModel("Cricket", cricketData.toMutableList()))
        }
        if (tennisData.size > 0) {
            mostPopularList!!.add(EventNewModel("Tennis", tennisData.toMutableList()))
        }
        if (footballData.size > 0) {
            mostPopularList!!.add(EventNewModel("Football", footballData.toMutableList()))
        }


        adapterChildInPlay.updateAdapter(itemsArrayList!!)
        adapterChildMostPopular.updateAdapter(mostPopularList!!)


    }


    override fun onItemClick(childTransfer: EventsData) {

    }

    override fun onItemClick(position: Int) {
        Log.d("TAG", "parent $position")
    }

    override fun onItemClick() {

    }


}