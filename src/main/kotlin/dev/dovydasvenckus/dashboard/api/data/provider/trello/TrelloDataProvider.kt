package dev.dovydasvenckus.dashboard.api.data.provider.trello

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import com.google.gson.Gson
import dev.dovydasvenckus.dashboard.api.data.provider.DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.dashboard.api.data.provider.trello.dto.TrelloCard

class TrelloDataProvider(
    private val client: HttpClient,
    private val gson: Gson,
    private val baseUrl: String,
    private val appKey: String,
    private val userToken: String,
    private val listId: String
) : DataProvider {

    override fun provide(): Data {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseUrl/lists/$listId/cards?key=$appKey&token=$userToken"))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        val cards = gson.fromJson(response.body(), Array<TrelloCard>::class.java)

        val mappedCards = mapTrelloModel(cards)
        return TrelloData(cards = mappedCards)
    }

    private fun mapTrelloModel(cards: Array<TrelloCard>) =
        cards.map { Card(name = it.name, labels = mapLabels(it)) }.toList()

    private fun mapLabels(card: TrelloCard) =
        card.labels.map { trelloLabel -> trelloLabel.name }.toList()
}