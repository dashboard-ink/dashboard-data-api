package dev.dovydasvenckus.dashboard.api.config

import javax.validation.constraints.NotEmpty

class TrelloConfig(
    val baseUrl: @NotEmpty String,
    val appKey: @NotEmpty String,
    val userToken: @NotEmpty String,
    val listId: @NotEmpty String
)