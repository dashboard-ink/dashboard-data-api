package dev.dovydasvenckus.dashboard.api.data.scrape.model

data class ScrapeRequest(
    val url: String,
    val steps: List<ScrapeStep>
)