package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Actividade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Objects;

public class ActividadeDaoJPA implements ActividadeDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Actividade almacena(Actividade actividade) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(actividade);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return actividade;
    }

    @Override
    public Actividade modifica(Actividade actividade) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            actividade = em.merge(actividade);

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return actividade;
    }

    @Override
    public void elimina(Actividade actividade) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Actividade actividadeTmp = em.find(Actividade.class, actividade.getId());
            em.remove(actividadeTmp);

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
    public Actividade recuperaPorNome(String nome) {
        List<Actividade> actividades = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            actividades = em.createNamedQuery("Actividade.recuperaPorNome", Actividade.class).setParameter("nome", nome).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return (Objects.requireNonNull(actividades).size() != 0 ? actividades.get(0) : null);
    }

}
