package com.matrix.datamodelconversation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.matrix.datamodelconversation.adapter.AdapterBothMatch
import com.matrix.datamodelconversation.databinding.ActivityMainBinding
import com.matrix.datamodelconversation.model.EventNewModel
import com.matrix.datamodelconversation.model.eventres.*
import com.matrix.datamodelconversation.model.socketres.SocketRes

class MainActivity : AppCompatActivity(), AdapterBothMatch.OnItemClickListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var itemsArrayList: MutableList<EventNewModel>? = ArrayList()
    private var mostPopularList: MutableList<EventNewModel>? = ArrayList()
    private var itemList: MutableList<Int>? = ArrayList()

    private val inPlayCricket: MutableList<EventsData> = mutableListOf()
    private val inPlayTennis: MutableList<EventsData> = mutableListOf()
    private val inPlayFootball: MutableList<EventsData> = mutableListOf()

    private val cricketData: MutableList<EventsData> = mutableListOf()
    private val tennisData: MutableList<EventsData> = mutableListOf()
    private val footballData: MutableList<EventsData> = mutableListOf()

    private val selectionMainList: MutableList<Selection?> = mutableListOf()

    private val backOdd: MutableList<BackOdd> = mutableListOf()
    private val layOdd: MutableList<LayOdd> = mutableListOf()

    val adapterBothMatch: AdapterBothMatch = AdapterBothMatch(true, ArrayList(),
        ArrayList(), ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val jsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("indicator", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }

        val gson = Gson()
        val market: EventRes = gson.fromJson(jsonData, EventRes::class.java)

        setData(market)
        val socketJsonData = applicationContext.resources.openRawResource(applicationContext
            .resources
            .getIdentifier("socket", "raw", applicationContext.packageName))
            .bufferedReader().use { it.readText() }

        val socketRes: SocketRes = gson.fromJson(socketJsonData, SocketRes::class.java)

        binding.connectBtn.setOnClickListener {
            selectionMainList.clear()
            backOdd.clear()
            layOdd.clear()
            socketRes.message!!.BF?.SL?.forEach { it ->
                selectionMainList.add(Selection(it?.BO as List<BackOdd>,
                    it.LO as List<LayOdd>,
                    it.I,
                    "ACTIVE"))
            }

            Log.d("Tag", selectionMainList.toString())
            for (i in inPlayFootball.indices) if (inPlayFootball[i].id == socketRes.MI) {
                val selection: MutableList<Selection?>? = selectionMainList
                for (j in selection!!.indices) {
                    inPlayFootball[i].market_odds?.selections?.set(j, selection[j])
                }

                Log.d("TAG",
                    " ${inPlayFootball[i].market_odds} \n ${
                        inPlayFootball[i].market_odds?.selections?.toString().toString()
                    }")
            }

        }

        binding.rvBothGames.adapter = adapterBothMatch
        adapterBothMatch.notifyDataSetChanged()
    }

    private fun setData(market: EventRes) {
        if (market != null) {
            for (i in market!!.events_data.indices) {
                if (market!!.events_data[i].sport_id == AppConstants.SportType.CRICKET && market!!.events_data[i].currently_live == 1) {
                    inPlayCricket.add(market!!.events_data[i])
                } else if (market!!.events_data[i].sport_id == AppConstants.SportType.FOOTBALL && market!!.events_data[i].currently_live == 1) {
                    inPlayFootball.add(market!!.events_data[i])
                } else if (market!!.events_data[i].sport_id == AppConstants.SportType.TENNIS && market!!.events_data[i].currently_live == 1) {
                    inPlayTennis.add(market!!.events_data[i])
                } else if (market!!.events_data[i].sport_id == AppConstants.SportType.CRICKET && market!!.events_data[i].currently_live == 0) {
                    cricketData.add(market!!.events_data[i])
                } else if (market!!.events_data[i].sport_id == AppConstants.SportType.FOOTBALL && market!!.events_data[i].currently_live == 0) {
                    footballData.add(market!!.events_data[i])
                } else if (market!!.events_data[i].sport_id == AppConstants.SportType.TENNIS && market!!.events_data[i].currently_live == 0) {
                    tennisData.add(market!!.events_data[i])
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
            itemList?.add(0)
            itemList?.add(2)

            adapterBothMatch.updateAdapter(itemList!!, itemsArrayList!!, mostPopularList!!)


        }
    }

    override fun onItemClick() {

    }
}