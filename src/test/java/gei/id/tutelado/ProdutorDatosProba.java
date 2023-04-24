package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Actividade;
import gei.id.tutelado.model.Persoa;
import gei.id.tutelado.model.Socio;
import gei.id.tutelado.model.Traballador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProdutorDatosProba {

    // Crea un conxunto de obxectos para utilizar nos casos de proba

    private EntityManagerFactory emf = null;

    public final Socio s0 = new Socio();
    public final Socio s1 = new Socio();
    private final List<Socio> listaxeS = new ArrayList<>();

    public final Traballador t0 = new Traballador();
    public final Traballador t1 = new Traballador();
    private final List<Traballador> listaxeT = new ArrayList<>();

    public final Actividade a0 = new Actividade();
    public final Actividade a1 = new Actividade();
    private final List<Actividade> listaxeA = new ArrayList<>();

    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    public void creaPersoasSoltas() {

        // Crea dous socios e dous traballadores EN MEMORIA: s0, s1, t0, t1
        // SEN actividades

        this.s0.setDni("000S");
        this.s0.setNome("Socio cero");
        this.s0.setDataNacemento(LocalDate.now().minusYears(20));
        this.s0.setTelefono("600000001");
        this.s0.setDataAlta(LocalDate.now());

        this.s1.setDni("111S");
        this.s1.setNome("Socia un");
        this.s1.setDataNacemento(LocalDate.now().minusYears(13));
        this.s1.setTelefono("600000002");
        this.s1.setDataAlta(LocalDate.now());

        this.listaxeS.add(0, s0);
        this.listaxeS.add(1, s1);

        this.t0.setDni("000T");
        this.t0.setNome("Traballador cero");
        this.t0.setPosto("Monitor");
        this.t0.setSalario(1000.0);
        this.t0.setDataContratacion(LocalDate.now());

        this.t1.setDni("111T");
        this.t1.setNome("Traballadora un");
        this.t1.setPosto("Socorrista");
        this.t1.setSalario(2000.0);
        this.t1.setDataContratacion(LocalDate.now());

        this.listaxeT.add(0, t0);
        this.listaxeT.add(1, t1);

    }

    public void creaActividadesSoltas() {

        // Crea duas actividades EN MEMORIA: a0, a1
        // Sen socio nin traballador asignado (momentaneamente)

        this.a0.setNome("A001");
        this.a0.setPiscina("Grande");
        this.a0.engadirMaterial("Flotador");
        this.a0.engadirMaterial("Flotador");
        this.a0.engadirMaterial("Silbato");

        this.a1.setNome("A002");
        this.a1.setPiscina("Pequena");
        this.a1.engadirMaterial("Churro");
        this.a1.engadirMaterial("Gorro");
        this.a1.engadirMaterial("Gafas");

        this.listaxeA.add(0, this.a0);
        this.listaxeA.add(1, this.a1);

    }

    public void creaPersoasConActividades() {

        this.creaPersoasSoltas();
        this.creaActividadesSoltas();

        this.s0.engadirActividade(this.a0);
        this.s0.engadirActividade(this.a1);
        this.s1.engadirActividade(this.a1);

        this.t0.setActividade(this.a1);
        this.t1.setActividade(this.a0);

    }

    public void gravaSocios() {
        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            for (Socio s : this.listaxeS) {
                em.persist(s);
            }

            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }

    }

    public void gravaTraballadores() {
        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            for (Traballador t : this.listaxeT) {
                em.persist(t);
                // DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
                em.persist(t.getActividade());
            }

            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }

    }

    public void limpaBD() {
        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            for (Persoa persoa : em.createNamedQuery("Persoa.recuperaTodas", Persoa.class).getResultList())
                em.remove(persoa);
            for (Actividade actividade : em.createNamedQuery("Actividade.recuperaTodas", Actividade.class).getResultList())
                em.remove(actividade);

            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idPersoa'").executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idActividade'").executeUpdate();

            em.getTransaction().commit();
            em.close();

        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }

    }

}
