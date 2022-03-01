package it.quix.immergas.bot-login.api.v1;

import it.quix.immergas.bot-login.api.v1.dto.Arena;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rest/")
@Tag(name = "Arena",
        description = "Arena API"
)
@RegisterRestClient
@RegisterClientHeaders
public interface ArenaRest {

    String ARENA_ADMIN = "arena_admin";

    @GET
    @Path("arena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "listArena")
    Arena[] listArena();

    @OPTIONS
    @Path("arena/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "existsArena")
    Boolean existsArena(
            @PathParam("name") String name
    );

    @POST
    @Path("arena")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "createArena")
    Response createArena(
            Arena arena
    );

    @DELETE
    @Path("arena/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "deleteArena")
    Response deleteArena(
            @PathParam("name") String name
    );
}
