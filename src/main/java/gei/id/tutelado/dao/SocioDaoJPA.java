package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Socio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

public class SocioDaoJPA implements SocioDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public List<Socio> recuperaSociosMayoresQue(Integer idade) {
        List<Socio> socios = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            LocalDate dataActual = LocalDate.now();
            socios = em.createNamedQuery("Socio.recuperaSociosMayoresQue", Socio.class)
                    .setParameter("dataActual", dataActual)
                    .setParameter("idade", idade)
                    .getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }

        return socios;
    }

}
