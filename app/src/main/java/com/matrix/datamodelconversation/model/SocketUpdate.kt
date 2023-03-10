package com.matrix.datamodelconversation.model

import com.matrix.datamodelconversation.model.eventres.EventsData
import com.matrix.datamodelconversation.model.eventres.Selection

data class SocketUpdate(val pos: Int, val eventsData: EventsData)