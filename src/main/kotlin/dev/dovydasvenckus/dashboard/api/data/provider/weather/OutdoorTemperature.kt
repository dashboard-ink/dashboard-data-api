package dev.dovydasvenckus.dashboard.api.data.provider.weather

import dev.dovydasvenckus.dashboard.api.data.provider.model.Data
import dev.dovydasvenckus.dashboard.api.data.provider.model.DataType

data class OutdoorTemperature(
    val city: String?,
    val description: String?,
    val temperature: String?,
    val wind: String?
) : Data(DataType.OUTDOORS_TEMPERATURE)