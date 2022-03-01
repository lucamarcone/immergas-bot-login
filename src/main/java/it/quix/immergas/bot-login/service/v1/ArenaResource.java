package it.quix.immergas.bot-login.service.v1;

import it.quix.immergas.bot-login.api.v1.dto.Arena;
import it.quix.immergas.bot-login.dao.ArenaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ArenaResource implements it.quix.immergas.bot-login.api.v1.ArenaRest {

    private static final Logger log = LoggerFactory.getLogger(ArenaResource.class);

    @Inject
    ArenaDao arenaDao;

    @Override
    public Arena[] listArena() {

        try {
            return arenaDao.listArena().toArray(new Arena[0]);

        } catch (Throwable throwable) {
            log.error("Error listing arena: " + throwable.getMessage(), throwable);
            throw new WebApplicationException(500);
        }
    }

    @Override
    public Boolean existsArena(String name) {

        try {
            return arenaDao.existsArena(name);

        } catch (Throwable throwable) {
            log.error("Error checking arena " + name + ": " + throwable.getMessage(), throwable);
            throw new WebApplicationException(500);
        }
    }

    @Override
    public Response createArena(Arena arena) {

        try {
            arenaDao.createArena(arena);

        } catch (Throwable throwable) {
            log.error("Error creating arena " + arena.getName() + ": " + throwable.getMessage(), throwable);
            throw new WebApplicationException(500);
        }

        return Response.status(201).build();
    }

    @Override
    public Response deleteArena(String name) {

        try {
            arenaDao.deleteArena(name);

        } catch (Throwable throwable) {
            log.error("Error deleting arena " + name + ": " + throwable.getMessage(), throwable);
            throw new WebApplicationException(500);
        }

        return Response.status(200).build();
    }

}