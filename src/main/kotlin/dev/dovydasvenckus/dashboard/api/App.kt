package dev.dovydasvenckus.dashboard.api

import com.fasterxml.jackson.module.kotlin.KotlinModule
import dev.dovydasvenckus.dashboard.api.data.DataResource
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
        bootstrap.objectMapper.registerModule(KotlinModule())
        bootstrap.configurationSourceProvider = SubstitutingSourceProvider(
            bootstrap.configurationSourceProvider,
            EnvironmentVariableSubstitutor(false)
        )
    }

    override fun run(configuration: AppConfiguration, environment: Environment) {
        environment.jersey().register(DataResource())
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            App().run(*args)
        }
    }
}