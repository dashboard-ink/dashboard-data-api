package dev.dovydasvenckus.dashboard.api.config

import javax.validation.constraints.NotEmpty

class ScraperConfig(val url: @NotEmpty String)