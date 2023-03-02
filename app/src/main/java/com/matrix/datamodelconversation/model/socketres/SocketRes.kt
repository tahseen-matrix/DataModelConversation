package com.matrix.datamodelconversation.model.socketres

data class SocketRes(
    val MI: Int?,
    val MT: String?,
    val channel: String?,
    val event: String?,
    val message: Message?
)