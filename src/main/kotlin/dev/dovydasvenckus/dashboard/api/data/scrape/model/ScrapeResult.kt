package dev.dovydasvenckus.dashboard.api.data.scrape.model

data class ScrapeResult(
    val status: ScrapeStatus,
    val data: List<ScrapedField>
)