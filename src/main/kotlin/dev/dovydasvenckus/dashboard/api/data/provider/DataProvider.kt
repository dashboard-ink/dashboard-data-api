package dev.dovydasvenckus.dashboard.api.data.provider

import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.scrapper.client.model.ScrapeResult

interface DataProvider {
    fun provide(): Data

    fun getFieldValue(scrapedFields: ScrapeResult, fieldName: String): String? {
        return scrapedFields.data.find { field -> field.name == fieldName }?.value
    }
}