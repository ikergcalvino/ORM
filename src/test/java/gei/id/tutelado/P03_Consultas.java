package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.*;
import gei.id.tutelado.model.Actividade;
import gei.id.tutelado.model.Socio;
import gei.id.tutelado.model.Traballador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P03_Consultas {

    private final Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static final Configuracion cfg = new ConfiguracionJPA();
    private static final PersoaDao perDao = new PersoaDaoJPA();
    private static final SocioDao socDao = new SocioDaoJPA();
    private static final TraballadorDao traDao = new TraballadorDaoJPA();
    private static final ActividadeDao actDao = new ActividadeDaoJPA();

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("");
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("Iniciando test: " + description.getMethodName());
            log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        protected void finished(Description description) {
            log.info("");
            log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
            log.info("Finalizado test: " + description.getMethodName());
            log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
        }
    };

    @BeforeClass
    public static void init() throws Exception {
        cfg.start();

        perDao.setup(cfg);
        socDao.setup(cfg);
        traDao.setup(cfg);
        actDao.setup(cfg);

        produtorDatos.setup(cfg);
    }

    @AfterClass
    public static void endclose() throws Exception {
        cfg.endUp();
    }

    @Before
    public void setUp() throws Exception {
        log.info("");
        log.info("Limpando BD --------------------------------------------------------------------------------------------");
        produtorDatos.limpaBD();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test06_RecuperaActividadesConMinSocios() {

        List<Actividade> actividades;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaActividadesConSocios();
        produtorDatos.gravaActividades();
        produtorDatos.gravaSocios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación das actividades cun minumo de socios");

        actividades = actDao.recuperaActividadesConMinSocios(0);
        Assert.assertEquals(actividades.get(0).getNome(), produtorDatos.a0.getNome());
        Assert.assertEquals(actividades.get(1).getNome(), produtorDatos.a1.getNome());
        Assert.assertEquals(actividades.get(2).getNome(), produtorDatos.a2.getNome());
        Assert.assertEquals(actividades.size(), 3);

        actividades = actDao.recuperaActividadesConMinSocios(1);
        Assert.assertEquals(actividades.get(0).getNome(), produtorDatos.a0.getNome());
        Assert.assertEquals(actividades.get(1).getNome(), produtorDatos.a1.getNome());
        Assert.assertEquals(actividades.size(), 2);

        actividades = actDao.recuperaActividadesConMinSocios(2);
        Assert.assertEquals(actividades.get(0).getNome(), produtorDatos.a0.getNome());
        Assert.assertEquals(actividades.size(), 1);

    }

    @Test
    public void test06_RecuperaSalarioMedio() {

        Double salario;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaTraballadoresSoltos();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación del salario medio");

        salario = traDao.recuperaSalarioMedio();
        Assert.assertEquals((Double) 1750D, salario);

    }


    @Test
    public void test06_RecuperaTraballadoresDePiscina() {

        List<Traballador> traballadores;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaActividadesConTraballadores();
        produtorDatos.gravaActividades();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación dos traballadores dunha piscina");

        traballadores = traDao.recuperaTraballadoresDePiscina("Piscina cero");
        Assert.assertEquals(traballadores.get(0).getDni(), produtorDatos.t0.getDni());
        Assert.assertEquals(traballadores.get(1).getDni(), produtorDatos.t2.getDni());
        Assert.assertEquals(traballadores.size(), 2);
    }

    @Test
    public void test06_RecuperaSociosConActividadesEn() {

        List<Socio> socios;

        List<Actividade> actividades = new ArrayList<>();

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaActividadesConSocios();
        produtorDatos.gravaActividades();
        produtorDatos.gravaSocios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación dos socios dunha actividade que se realiza nunha piscina, os que teñen asignada outra ou os que non teñen actividade asignada");

        socios = socDao.recuperaSociosConActividadesEn("Piscina cero");

        Assert.assertEquals(socios.get(0).getDni(), produtorDatos.s0.getDni());
        Assert.assertEquals(socios.get(1).getDni(), produtorDatos.s1.getDni());

        Assert.assertEquals(socios.get(0).getActividades().first().getPiscina(),
                produtorDatos.s0.getActividades().first().getPiscina(), "Piscina cero");
        Assert.assertEquals(socios.get(1).getActividades().first().getPiscina(),
                produtorDatos.s1.getActividades().first().getPiscina(), "Piscina cero");

        Assert.assertNotEquals(socios.get(2).getActividades(), actividades);

        Assert.assertEquals(socios.size(), 3);

    }

}
