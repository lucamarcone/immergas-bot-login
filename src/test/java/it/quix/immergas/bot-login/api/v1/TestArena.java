package it.quix.immergas.bot-login.api.v1;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import it.quix.immergas.bot-login.api.v1.dto.Arena;
import it.quix.quake.test.PostgresResource;
import it.quix.quake.test.TestUser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static it.quix.immergas.bot-login.service.v1.ArenaResource.ARENA_ADMIN;
import static it.quix.quake.test.QuakeTestClientBuilder.client;
import static it.quix.quake.test.TestUser.uid;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;


@QuarkusTest
@QuarkusTestResource(PostgresResource.class)
public class TestArena {

    @TestHTTPResource("bot-login")
    URL baseUrl;

    @Test
    public void testArena() {
        TestUser administrator = uid("administrator")
                .roles(ARENA_ADMIN)
                .build();

        ArenaRest arenaResource = client(baseUrl, ArenaRest.class, administrator);

        // Devono esserci solo le 2 arene di impianto
        Arena[] arenas = arenaResource.listArena();
        assertThat(arenas, arrayWithSize(2));

        // Creo una nuova arena
        String arena1Name = "arena1";
        Arena arena1 = new Arena(arena1Name, 500);
        arenaResource.createArena(arena1);
        arenas = arenaResource.listArena();
        assertThat(arenas, arrayWithSize(3));
        assertThat(arenas, Matchers.hasItemInArray(arena1));

        // Verifico che esista
        assertThat(arenaResource.existsArena(arena1Name), is(true));

        // La cancello
        arenaResource.deleteArena(arena1Name);
        arenas = arenaResource.listArena();
        assertThat(arenas, arrayWithSize(2));
        assertThat(arenaResource.existsArena(arena1Name), is(false));

    }

}
