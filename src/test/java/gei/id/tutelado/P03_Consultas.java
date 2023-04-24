package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.Actividade;
import gei.id.tutelado.model.Persoa;
import gei.id.tutelado.model.Socio;
import gei.id.tutelado.model.Traballador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P03_Consultas {

    private final Logger log = LogManager.getLogger("gei.id.tutelado");

    private static final ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static final Configuracion cfg = new ConfiguracionJPA();
    private static final PersoaDao perDao = new PersoaDaoJPA();
    private static final ActividadeDao actDao = new ActividadeDaoJPA();
    private static final SocioDao socDao = new SocioDaoJPA();
    private static final TraballadorDao traDao = new TraballadorDaoJPA();

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("");
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("Iniciando test: " + description.getMethodName());
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        protected void finished(Description description) {
            log.info("");
            log.info("--------------------------------------------------------------------------------------------");
            log.info("Finalizado test: " + description.getMethodName());
            log.info("--------------------------------------------------------------------------------------------");
        }
    };

    @BeforeClass
    public static void init() {
        cfg.start();

        perDao.setup(cfg);
        actDao.setup(cfg);

        produtorDatos.setup(cfg);
    }

    @AfterClass
    public static void endclose() {
        cfg.endUp();
    }

    @Before
    public void setUp() {
        log.info("");
        log.info("Limpando BD ------------------------------------------------------------------------------------");
        produtorDatos.limpaBD();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test06_todasP() {

        List<Persoa> listaP;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaPersoasConActividades();
        produtorDatos.gravaSocios();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Persoa.recuperaTodas\n");

        // Situación de partida:

        listaP = perDao.recuperaTodas();

        Assert.assertEquals(4, listaP.size());
        Assert.assertTrue(listaP.contains(produtorDatos.s0));
        Assert.assertTrue(listaP.contains(produtorDatos.s1));
        Assert.assertTrue(listaP.contains(produtorDatos.t0));
        Assert.assertTrue(listaP.contains(produtorDatos.t1));

    }

    @Test
    public void test06_todasA() {

        List<Actividade> listaA;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaPersoasConActividades();
        produtorDatos.gravaSocios();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Actividade.recuperaTodas\n");

        // Situación de partida:

        listaA = actDao.recuperaTodas();

        Assert.assertEquals(2, listaA.size());
        Assert.assertTrue(listaA.contains(produtorDatos.a0));
        Assert.assertTrue(listaA.contains(produtorDatos.a1));

    }

    @Test
    public void test06_INNER() {

        List<Traballador> listaT;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaPersoasConActividades();
        produtorDatos.gravaSocios();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Traballador.recuperaTraballadoresDePiscina\n");

        // Situación de partida:

        listaT = traDao.recuperaTraballadoresDePiscina("Grande");

        Assert.assertEquals(1, listaT.size());
        Assert.assertTrue(listaT.contains(produtorDatos.t1));

    }

    @Test
    public void test06_OUTER() {

        List<Socio> listaS;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaPersoasConActividades();
        produtorDatos.gravaSocios();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Socio.recuperaSociosConActividadesEn\n");

        // Situación de partida:

        listaS = socDao.recuperaSociosConActividadesEn("Pequena");

        Assert.assertEquals(2, listaS.size());
        Assert.assertTrue(listaS.contains(produtorDatos.s0));
        Assert.assertTrue(listaS.contains(produtorDatos.s1));

    }

    @Test
    public void test06_Subconsulta() {

        List<Actividade> listaA;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaPersoasConActividades();
        produtorDatos.gravaSocios();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Actividade.recuperaActividadesConMinSocios\n");

        // Situación de partida:

        listaA = actDao.recuperaActividadesConMinSocios(2);

        Assert.assertEquals(1, listaA.size());
        Assert.assertTrue(listaA.contains(produtorDatos.a1));

    }

    @Test
    public void test06_Agregacion() {

        Double salarioMedio;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaPersoasConActividades();
        produtorDatos.gravaSocios();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Traballador.recuperaSalarioMedio\n");

        // Situación de partida:

        salarioMedio = traDao.recuperaSalarioMedio();

        Assert.assertEquals(Double.valueOf(1500.0), salarioMedio);

    }

}
