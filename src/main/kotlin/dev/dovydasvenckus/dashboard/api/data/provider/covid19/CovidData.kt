package dev.dovydasvenckus.dashboard.api.data.provider.covid19

import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.dashboard.api.data.provider.model.DataType

data class CovidData(
    val date: String?,
    val yesterdayCases: String?,
    val yesterdayDeaths: String?,
    val newCasesPer100K: String?,
) : Data(DataType.LOCAL_COVID19_STATISTICS)