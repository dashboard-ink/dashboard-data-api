package dev.dovydasvenckus.dashboard.api.data.provider.covid19

import dev.dovydasvenckus.dashboard.api.data.provider.DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.scrapper.client.WebScrapperClient
import dev.dovydasvenckus.scrapper.client.model.ScrapeRequest
import dev.dovydasvenckus.scrapper.client.model.ScrapeResult
import dev.dovydasvenckus.scrapper.client.model.ScrapeStep

private object FieldSelectors {
    val DATE = ScrapeStep(fieldName = "date", selector = "#w-statistika-lietuvoje > div > h3")
    val YESTERDAY_CASES =
        ScrapeStep(fieldName = "yesterdayCases", selector = "#w-statistika-lietuvoje .stats_item > .value:eq(0)")
    val YESTERDAY_DEATHS = ScrapeStep(
        fieldName = "yesterdayDeaths",
        selector = "#w-statistika-lietuvoje .stats_list > .stats_item .value:eq(1)"
    )
    val NEW_CASES_PER_100K = ScrapeStep(
        fieldName = "newCasesPer100K",
        selector = "#w-statistika-lietuvoje .stats_list > .stats_item .value:eq(2)"
    )
    val PERCENTAGE_OF_POSITIVE_TESTS = ScrapeStep(
        fieldName = "percentageOfPositiveTests",
        selector = "#w-statistika-lietuvoje .stats_list > .stats_item .value:eq(3)"
    )
}

class Covid19DataProvider(private val scrapingClient: WebScrapperClient) : DataProvider {

    override fun provide(): Data {
        val result = scrapingClient.scrape(
            ScrapeRequest(
                url = "https://koronastop.lrv.lt/",
                steps = listOf(
                    FieldSelectors.DATE,
                    FieldSelectors.YESTERDAY_CASES,
                    FieldSelectors.YESTERDAY_DEATHS,
                    FieldSelectors.NEW_CASES_PER_100K,
                    FieldSelectors.PERCENTAGE_OF_POSITIVE_TESTS
                )
            )
        )

        return mapScrapingResult(result)
    }

    private fun mapScrapingResult(result: ScrapeResult) = CovidData(
        date = getFieldValue(result, FieldSelectors.DATE.fieldName),
        yesterdayCases = getFieldValue(result, FieldSelectors.YESTERDAY_CASES.fieldName),
        yesterdayDeaths = getFieldValue(result, FieldSelectors.YESTERDAY_DEATHS.fieldName),
        newCasesPer100K = getFieldValue(result, FieldSelectors.NEW_CASES_PER_100K.fieldName),
        percentageOfPositiveTests = getFieldValue(result, FieldSelectors.PERCENTAGE_OF_POSITIVE_TESTS.fieldName)
    )
}