package dev.dovydasvenckus.dashboard.api.data.provider

import dev.dovydasvenckus.dashboard.api.data.provider.model.Data

interface DataProvider {
    fun provide(): Data
}