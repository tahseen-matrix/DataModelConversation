package com.matrix.datamodelconversation.model.eventres

data class MarketOdds(
    val betDelay: Int?,
    val inplay: Boolean?,
    val market_id: String?,
    val selections: List<Selection?>?,
    val status: String?
)