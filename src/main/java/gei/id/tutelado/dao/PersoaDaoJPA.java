package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;

public class PersoaDaoJPA implements PersoaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Persoa almacena(Persoa persoa) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(persoa);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return persoa;
    }

    @Override
    public Persoa modifica(Persoa persoa) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            persoa = em.merge(persoa);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return persoa;
    }

    @Override
    public void elimina(Persoa persoa) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Persoa persoaTmp = em.find(Persoa.class, persoa.getId());
            em.remove(persoaTmp);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
    }

    @Override
    public Persoa recuperaPorDni(String dni) {
        List<Persoa> persoas = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            persoas = em.createNamedQuery("Persoa.recuperaPorDni", Persoa.class).setParameter("dni", dni).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return (Objects.requireNonNull(persoas).size() != 0 ? persoas.get(0) : null);
    }

}
