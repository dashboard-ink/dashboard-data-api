package dev.dovydasvenckus.dashboard.api.data

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/data")
class DataResource {

    @GET
    fun get(): Response {
        return Response.ok("Ok").build()
    }
}