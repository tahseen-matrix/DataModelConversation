package com.matrix.datamodelconversation.model.eventres

data class EventsData(
    val currently_live: Int?,
    val event_name: String?,
    val freeze_odds_class: String?,
    val id: Int?,
    val market_id: String?,
    val market_odds: MarketOdds?,
    val market_status: String?,
    val market_type: String?,
    val selection_list: List<SelectionX?>?,
    val sport_id: Int?,
    val start_date: String?,
    val tournament_id: Int?,
    val video_channel_id: String?
)