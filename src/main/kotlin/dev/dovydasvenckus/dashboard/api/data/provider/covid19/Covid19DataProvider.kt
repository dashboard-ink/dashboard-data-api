package dev.dovydasvenckus.dashboard.api.data.provider.covid19

import dev.dovydasvenckus.dashboard.api.data.provider.DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.scrapper.client.WebScrapperClient
import dev.dovydasvenckus.scrapper.client.model.ScrapeRequest
import dev.dovydasvenckus.scrapper.client.model.ScrapeResult
import dev.dovydasvenckus.scrapper.client.model.ScrapeStep
import dev.dovydasvenckus.scrapper.client.model.StepType.SCRAPE_TEXT

private object FieldSelectors {
    val DATE = ScrapeStep(SCRAPE_TEXT, "date", null, "#w-statistika-lietuvoje > div > h3")
    val YESTERDAY_CASES =
        ScrapeStep(SCRAPE_TEXT, "yesterdayCases", null, "#w-statistika-lietuvoje .stats_item > .value:eq(0)")
    val YESTERDAY_DEATHS = ScrapeStep(
        SCRAPE_TEXT, "yesterdayDeaths", null, "#w-statistika-lietuvoje .stats_list > .stats_item .value:eq(1)"
    )
    val NEW_CASES_PER_100K = ScrapeStep(
        SCRAPE_TEXT, "newCasesPer100K", null, "#w-statistika-lietuvoje .stats_list > .stats_item .value:eq(2)"
    )
}

class Covid19DataProvider(private val scrapingClient: WebScrapperClient) : DataProvider {

    override fun provide(): Data {
        val result = scrapingClient.scrape(
            ScrapeRequest(
                "https://koronastop.lrv.lt/",
                listOf(
                    FieldSelectors.DATE,
                    FieldSelectors.YESTERDAY_CASES,
                    FieldSelectors.YESTERDAY_DEATHS,
                    FieldSelectors.NEW_CASES_PER_100K,
                )
            )
        )

        return mapScrapingResult(result)
    }

    private fun mapScrapingResult(result: ScrapeResult) = CovidData(
        date = getFieldValue(result, FieldSelectors.DATE.fieldName),
        yesterdayCases = getFieldValue(result, FieldSelectors.YESTERDAY_CASES.fieldName),
        yesterdayDeaths = getFieldValue(result, FieldSelectors.YESTERDAY_DEATHS.fieldName),
        newCasesPer100K = getFieldValue(result, FieldSelectors.NEW_CASES_PER_100K.fieldName)
    )
}