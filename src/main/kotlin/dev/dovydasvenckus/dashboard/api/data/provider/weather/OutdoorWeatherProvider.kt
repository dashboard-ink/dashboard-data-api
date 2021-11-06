package dev.dovydasvenckus.dashboard.api.data.provider.weather

import dev.dovydasvenckus.dashboard.api.data.provider.DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.scrapper.client.WebScrapperClient
import dev.dovydasvenckus.scrapper.client.model.ScrapeRequest
import dev.dovydasvenckus.scrapper.client.model.ScrapeResult
import dev.dovydasvenckus.scrapper.client.model.ScrapeStep
import dev.dovydasvenckus.scrapper.client.model.StepType

private object FieldSelectors {
    val CITY = ScrapeStep(fieldName = "city", selector = "tr.town_list > td.town:eq(0)")
    val DESCRIPTION =
        ScrapeStep(
            fieldName = "description",
            type = StepType.SCRAPE_ATTRIBUTE,
            attributeName = "title",
            selector = "table.weather_block > tbody > tr:nth-child(2) > td:nth-child(2) > span:nth-child(1)"
        )
    val TEMPERATURE = ScrapeStep(
        fieldName = "temperature",
        selector = "table.weather_block > tbody > tr:nth-child(2) > td:nth-child(2) > span:nth-child(2)"
    )
    val WIND = ScrapeStep(
        fieldName = "newCasesPer100K",
        selector = "table.weather_block > tbody > tr:nth-child(2) > td:nth-child(2) > span:nth-child(3)"
    )
}

class OutdoorWeatherProvider(private val scrapingClient: WebScrapperClient) : DataProvider {
    override fun provide(): Data {
        val result = scrapingClient.scrape(
            ScrapeRequest(
                url = "http://www.meteo.lt/lt/",
                steps = listOf(
                    FieldSelectors.CITY,
                    FieldSelectors.DESCRIPTION,
                    FieldSelectors.TEMPERATURE,
                    FieldSelectors.WIND,
                )
            )
        )

        return mapScrapingResult(result)
    }

    private fun mapScrapingResult(result: ScrapeResult) = OutdoorTemperature(
        city = getFieldValue(result, FieldSelectors.CITY.fieldName),
        description = getFieldValue(result, FieldSelectors.DESCRIPTION.fieldName),
        temperature = getFieldValue(result, FieldSelectors.TEMPERATURE.fieldName),
        wind = getFieldValue(result, FieldSelectors.WIND.fieldName)
    )
}