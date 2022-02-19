package dev.dovydasvenckus.dashboard.api.data.provider.trello

import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.dashboard.api.data.provider.model.DataType

data class TrelloData(val cards: List<Card>) : Data(DataType.TRELLO)