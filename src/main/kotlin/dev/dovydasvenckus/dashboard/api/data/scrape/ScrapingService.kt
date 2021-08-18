package dev.dovydasvenckus.dashboard.api.data.scrape

import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import dev.dovydasvenckus.dashboard.api.config.ScraperConfig
import dev.dovydasvenckus.dashboard.api.data.scrape.model.ScrapeRequest
import dev.dovydasvenckus.dashboard.api.data.scrape.model.ScrapeResult

class ScrapingService(private val scraperConfig: ScraperConfig, private val client: Client) {

    fun scrape(request: ScrapeRequest): ScrapeResult {
        val response = client
            .target(scraperConfig.url)
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(request, MediaType.APPLICATION_JSON))

        return response.readEntity(ScrapeResult::class.java)
    }
}