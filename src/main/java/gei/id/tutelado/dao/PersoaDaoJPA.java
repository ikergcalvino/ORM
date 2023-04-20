package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class PersoaDaoJPA implements PersoaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

}
