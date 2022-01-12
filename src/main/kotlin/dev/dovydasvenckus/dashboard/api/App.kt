package dev.dovydasvenckus.dashboard.api

import com.fasterxml.jackson.module.kotlin.KotlinModule
import dev.dovydasvenckus.dashboard.api.data.DataResource
import dev.dovydasvenckus.dashboard.api.data.provider.covid19.Covid19DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.weather.OutdoorWeatherProvider
import dev.dovydasvenckus.scrapper.client.WebScrapperClient
import io.dropwizard.Application
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class App : Application<AppConfiguration>() {
    override fun getName(): String {
        return "dashboard-data-api"
    }

    override fun initialize(bootstrap: Bootstrap<AppConfiguration>) {
        bootstrap.objectMapper.registerModule(KotlinModule.Builder().build())
        bootstrap.configurationSourceProvider = SubstitutingSourceProvider(
            bootstrap.configurationSourceProvider,
            EnvironmentVariableSubstitutor(false)
        )
    }

    override fun run(configuration: AppConfiguration, environment: Environment) {
        val scrapingClient = WebScrapperClient(configuration.scraperConfig.url)
        environment.jersey().register(
            DataResource(
                listOf(
                    Covid19DataProvider(scrapingClient),
                    OutdoorWeatherProvider(scrapingClient)
                )
            )
        )
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            App().run(*args)
        }
    }
}