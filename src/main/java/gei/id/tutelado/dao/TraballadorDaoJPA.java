package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Traballador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TraballadorDaoJPA implements TraballadorDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public List<Traballador> recuperaTraballadoresDePiscina(String piscina) {
        List<Traballador> traballadores = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            traballadores = em.createNamedQuery("Traballador.recuperaTraballadoresDePiscina", Traballador.class)
                    .setParameter("piscina", piscina).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return traballadores;
    }

    @Override
    public Double recuperaSalarioMedio() {
        Double salario = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            salario = em.createNamedQuery("Traballador.recuperaSalarioMedio", Double.class)
                    .getSingleResult();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return salario;
    }

}
