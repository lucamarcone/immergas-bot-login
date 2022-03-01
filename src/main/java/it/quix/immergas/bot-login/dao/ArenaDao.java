package it.quix.immergas.bot-login.dao;

import io.quarkus.security.identity.SecurityIdentity;
import it.quix.immergas.bot-login.api.v1.dto.Arena;
import it.quix.quake.sdk.data.time.QDateTime;
import org.jdbi.v3.core.Jdbi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ArenaDao {

    @Inject
    Jdbi jdbi;

    @Inject
    SecurityIdentity securityIdentity;

    public List<Arena> listArena() {

        return jdbi.withHandle(handle ->

                handle.createQuery("SELECT name, seats FROM qa_arena ORDER BY name")
                        .mapToBean(Arena.class)
                        .list()
        );
    }

    public void createArena(Arena arena) {

        final QDateTime now = new QDateTime();
        final UUID uuid = UUID.randomUUID();

        jdbi.useHandle(handle ->

                handle.createUpdate("INSERT INTO qa_arena (id, name, seats, createUser, createDateTime) " +
                        "VALUES (:uuid, :name, :seats, :user, :now)")
                        .bindBean(arena)
                        .bind("uuid", uuid)
                        .bind("now", now)
                        .bind("user", securityIdentity.getPrincipal().getName())
                        .execute()
        );
    }

    public void deleteArena(String name) {

        jdbi.useHandle(handle ->

                handle.createUpdate("DELETE FROM qa_arena WHERE name = :name")
                        .bind("name", name)
                        .execute()
        );
    }

    public Boolean existsArena(String name) {

        return jdbi.withHandle(handle -> {

            Optional<Integer> existing = handle.createQuery("SELECT 1 FROM qa_arena " +
                    "WHERE name = :name")
                    .bind("name", name)
                    .mapTo(Integer.class)
                    .findFirst();

            return existing.isPresent();
        });
    }
}
