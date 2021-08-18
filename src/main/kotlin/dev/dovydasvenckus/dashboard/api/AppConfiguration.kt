package dev.dovydasvenckus.dashboard.api

import javax.validation.Valid
import javax.validation.constraints.NotNull
import com.fasterxml.jackson.annotation.JsonProperty
import dev.dovydasvenckus.dashboard.api.config.ScraperConfig
import io.dropwizard.Configuration
import io.dropwizard.client.JerseyClientConfiguration

class AppConfiguration : Configuration() {
    val jerseyClient: @Valid @NotNull JerseyClientConfiguration? = JerseyClientConfiguration()

    @get:JsonProperty("scraper")
    val scraperConfig: @Valid ScraperConfig = ScraperConfig("empty")
}