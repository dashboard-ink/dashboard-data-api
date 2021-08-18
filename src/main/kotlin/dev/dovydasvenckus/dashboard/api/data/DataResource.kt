package dev.dovydasvenckus.dashboard.api.data

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import dev.dovydasvenckus.dashboard.api.data.provider.DataProvider
import dev.dovydasvenckus.dashboard.api.data.provider.model.DataResponse

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
class DataResource(private val providers: List<DataProvider>) {

    @GET
    fun get(): Response {
        return Response.ok(DataResponse(providers.map { dataProvider -> dataProvider.provide() })).build()
    }
}