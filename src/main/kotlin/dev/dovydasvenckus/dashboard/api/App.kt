package dev.dovydasvenckus.dashboard.api

import java.net.http.HttpClient
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.gson.Gson
import dev.dovydasvenckus.dashboard.api.data.DataResource
import dev.dovydasvenckus.dashboard.api.data.provider.trello.TrelloDataProvider
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
            bootstrap.configurationSourceProvider, EnvironmentVariableSubstitutor(false)
        )
    }

    override fun run(configuration: AppConfiguration, environment: Environment) {
        val scrapingClient = WebScrapperClient(configuration.scraperConfig.url)

        environment.jersey().register(
            DataResource(
                listOf(
                    OutdoorWeatherProvider(scrapingClient),
                    TrelloDataProvider(
                        client = HttpClient.newBuilder().build(),
                        gson = Gson(),
                        baseUrl = configuration.trelloConfig.baseUrl,
                        appKey = configuration.trelloConfig.appKey,
                        userToken = configuration.trelloConfig.userToken,
                        listId = configuration.trelloConfig.listId
                    )
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