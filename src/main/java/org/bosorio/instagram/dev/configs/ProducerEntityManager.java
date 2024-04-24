package org.bosorio.instagram.dev.configs;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@RequestScoped
public class ProducerEntityManager {

    @PersistenceContext(name = "persistenceJpa")
    private EntityManager em;

    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        return em;
    }

}
