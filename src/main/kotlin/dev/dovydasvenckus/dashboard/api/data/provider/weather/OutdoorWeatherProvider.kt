package dev.dovydasvenckus.dashboard.api.data.provider.weather

import dev.dovydasvenckus.dashboard.api.data.provider.DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.scrapper.client.WebScrapperClient
import dev.dovydasvenckus.scrapper.client.model.ScrapeRequest
import dev.dovydasvenckus.scrapper.client.model.ScrapeResult
import dev.dovydasvenckus.scrapper.client.model.ScrapeStep
import dev.dovydasvenckus.scrapper.client.model.StepType
import dev.dovydasvenckus.scrapper.client.model.StepType.SCRAPE_TEXT

private object FieldSelectors {
    val CITY = ScrapeStep(SCRAPE_TEXT, "city", null, "tr.town_list > td.town:eq(0)")
    val DESCRIPTION =
        ScrapeStep(
            StepType.SCRAPE_ATTRIBUTE,
            "description",
            "title",
            "table.weather_block > tbody > tr:nth-child(2) > td:nth-child(2) > span:nth-child(1)"
        )
    val TEMPERATURE = ScrapeStep(
        SCRAPE_TEXT,
        "temperature",
        null,
        "table.weather_block > tbody > tr:nth-child(2) > td:nth-child(2) > span:nth-child(2)"
    )
    val WIND = ScrapeStep(
        SCRAPE_TEXT,
        "newCasesPer100K",
        null,
        "table.weather_block > tbody > tr:nth-child(2) > td:nth-child(2) > span:nth-child(3)"
    )
}

class OutdoorWeatherProvider(private val scrapingClient: WebScrapperClient) : DataProvider {
    override fun provide(): Data {
        val result = scrapingClient.scrape(
            ScrapeRequest(
                "http://www.meteo.lt/lt/",
                listOf(
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