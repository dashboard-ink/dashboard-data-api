package dev.dovydasvenckus.dashboard.api

import com.fasterxml.jackson.module.kotlin.KotlinModule
import dev.dovydasvenckus.dashboard.api.data.DataResource
import dev.dovydasvenckus.dashboard.api.data.provider.covid19.Covid19DataProvider
import dev.dovydasvenckus.dashboard.api.data.scrape.ScrapingService
import io.dropwizard.Application
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.configuration.EnvironmentVariableSubstitutor
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

class App : Application<AppConfiguration>() {
    override fun getName(): String {
        return "dashboard-data-api"
    }

    override fun initialize(bootstrap: Bootstrap<AppConfiguration>) {
        bootstrap.objectMapper.registerModule(KotlinModule())
        bootstrap.configurationSourceProvider = SubstitutingSourceProvider(
            bootstrap.configurationSourceProvider,
            EnvironmentVariableSubstitutor(false)
        )
    }

    override fun run(configuration: AppConfiguration, environment: Environment) {
        val client = JerseyClientBuilder(environment).using(configuration.jerseyClient)
            .build(name)

        environment.jersey().register(
            DataResource(
                listOf(
                    Covid19DataProvider(ScrapingService(configuration.scraperConfig, client))
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