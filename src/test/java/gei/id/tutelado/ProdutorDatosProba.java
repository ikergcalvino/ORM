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

    public Socio s0, s1, s2;
    public List<Socio> listaxeS;

    public Traballador t0, t1, t2;
    public List<Traballador> listaxeT;

    public List<Persoa> listaxeP;

    public Actividade a0, a1, a2;
    public List<Actividade> listaxeA;

    private EntityManagerFactory emf = null;

    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    public void creaTraballadoresSoltos() {

        this.t0 = new Traballador();
        this.t0.setDni("100A");
        this.t0.setNome("Trabajador cero");
        this.t0.setDataNacemento(LocalDate.of(1999, 1, 1));
        this.t0.setPosto("Socorrista");
        this.t0.setSalario(1500D);
        this.t0.setDataContratacion(LocalDate.of(2020, 1, 1));

        this.t1 = new Traballador();
        this.t1.setDni("101B");
        this.t1.setNome("Trabajador uno");
        this.t1.setDataNacemento(LocalDate.of(1999, 1, 1));
        this.t1.setPosto("Auxiliar");
        this.t1.setSalario(1250D);
        this.t1.setDataContratacion(LocalDate.of(2020, 1, 1));

        this.t2 = new Traballador();
        this.t2.setDni("102C");
        this.t2.setNome("Trabajador dos");
        this.t2.setDataNacemento(LocalDate.of(1999, 1, 1));
        this.t2.setPosto("Monitor");
        this.t2.setSalario(2500D);
        this.t2.setDataContratacion(LocalDate.of(2020, 1, 1));

        this.listaxeT = new ArrayList<>();
        this.listaxeT.add(0, t0);
        this.listaxeT.add(1, t1);
        this.listaxeT.add(2, t2);

    }

    public void creaSociosSoltos() {

        this.s0 = new Socio();
        this.s0.setDni("000A");
        this.s0.setNome("Socio cero");
        this.s0.setDataNacemento(LocalDate.of(1999, 1, 1));
        this.s0.setTelefono("+34 1234");
        this.s0.setDataAlta(LocalDate.now());

        this.s1 = new Socio();
        this.s1.setDni("001B");
        this.s1.setNome("Socio uno");
        this.s1.setDataNacemento(LocalDate.of(1999, 1, 1));
        this.s1.setTelefono("+34 12345");
        this.s1.setDataAlta(LocalDate.now());

        this.s2 = new Socio();
        this.s2.setDni("002C");
        this.s2.setNome("Socio dos");
        this.s2.setDataNacemento(LocalDate.of(1999, 1, 1));
        this.s2.setTelefono("+34 123456");
        this.s2.setDataAlta(LocalDate.now());

        this.listaxeS = new ArrayList<>();
        this.listaxeS.add(0, s0);
        this.listaxeS.add(1, s1);
        this.listaxeS.add(2, s2);

    }

    public void creaActividadesSoltas() {

        this.a0 = new Actividade();
        this.a0.setNome("Actividad cero");
        this.a0.setPiscina("Piscina cero");
        a0.addMaterial("Flotador");
        a0.addMaterial("Toalla");

        this.a1 = new Actividade();
        this.a1.setNome("Actividad uno");
        this.a1.setPiscina("Piscina uno");
        a1.addMaterial("Manguito");
        a1.addMaterial("Silla");

        this.a2 = new Actividade();
        this.a2.setNome("Actividad dos");
        this.a2.setPiscina("Piscina dos");
        a2.addMaterial(("Gorro"));
        a2.addMaterial("Banhador");

        this.listaxeA = new ArrayList<>();
        this.listaxeA.add(0, a0);
        this.listaxeA.add(1, a1);
        this.listaxeA.add(2, a2);

    }

    public void creaActividadesConSocios() {

        this.creaSociosSoltos();
        this.creaActividadesSoltas();

        this.s0.addActividade(this.a0);
        this.s1.addActividade(this.a0);
        this.s0.addActividade(this.a1);

    }

    public void creaActividadesConTraballadores() {

        this.creaTraballadoresSoltos();
        this.creaActividadesSoltas();

        this.a0.engadirTraballador(this.t0);
        this.a0.engadirTraballador(this.t2);
        this.a1.engadirTraballador(this.t1);

    }

    public void creaActividadesConTraballadoresESocios() {

        this.creaTraballadoresSoltos();
        this.creaSociosSoltos();
        this.creaActividadesSoltas();

        this.s0.addActividade(this.a0);
        this.s1.addActividade(this.a0);
        this.s0.addActividade(this.a1);

        this.a0.engadirTraballador(this.t0);
        this.a0.engadirTraballador(this.t2);
        this.a1.engadirTraballador(this.t1);

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

    public void gravaActividades() {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            for (Actividade a : this.listaxeA) {
                em.persist(a);
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

    public void gravaPersoas() {

        EntityManager em = null;

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            for (Persoa p : this.listaxeP) {
                em.persist(p);
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

            for (Actividade actividade : em.createNamedQuery("Actividade.recuperaTodas", Actividade.class).getResultList())
                em.remove(actividade);
            for (Persoa persoa : em.createNamedQuery("Persoa.recuperaTodas", Persoa.class).getResultList())
                em.remove(persoa);

            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idActividad'").executeUpdate();
            em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nome_id='idPersona'").executeUpdate();

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
