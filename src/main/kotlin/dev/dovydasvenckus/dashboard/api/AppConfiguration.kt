package dev.dovydasvenckus.dashboard.api

import javax.validation.Valid
import com.fasterxml.jackson.annotation.JsonProperty
import dev.dovydasvenckus.dashboard.api.config.ScraperConfig
import dev.dovydasvenckus.dashboard.api.config.TrelloConfig
import io.dropwizard.Configuration

class AppConfiguration : Configuration() {

    @get:JsonProperty("scraper")
    val scraperConfig: @Valid ScraperConfig = ScraperConfig("empty")

    @get:JsonProperty("trello")
    val trelloConfig: @Valid TrelloConfig = TrelloConfig("empty", "empty", "empty", "empty")
}