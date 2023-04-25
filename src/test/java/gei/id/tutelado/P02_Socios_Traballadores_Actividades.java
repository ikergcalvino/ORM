package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.ActividadeDao;
import gei.id.tutelado.dao.ActividadeDaoJPA;
import gei.id.tutelado.dao.PersoaDao;
import gei.id.tutelado.dao.PersoaDaoJPA;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P02_Socios_Traballadores_Actividades {

    private final Logger log = LogManager.getLogger("gei.id.tutelado");

    private static final ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static final Configuracion cfg = new ConfiguracionJPA();
    private static final ActividadeDao actDao = new ActividadeDaoJPA();
    private static final PersoaDao perDao = new PersoaDaoJPA();

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
    public void test01_RecuperacionTodas() {

        Actividade a0, a1;
        Socio s0, s1;
        Traballador t0, t1, t2;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaActividadesConTraballadoresESocios();
        produtorDatos.gravaActividades();
        produtorDatos.gravaSocios();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de actividade (con socios e traballadores asociados) por nome\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por nome existente\n");

        log.info("Probando recuperacion relación socios por nome EXISTENTE ---------------------------------------");

        a0 = actDao.recuperaPorNome(produtorDatos.a0.getNome());
        a1 = actDao.recuperaPorNome(produtorDatos.a1.getNome());

        Assert.assertEquals(produtorDatos.a0.getNome(), a0.getNome());
        Assert.assertEquals(produtorDatos.a0.getPiscina(), a0.getPiscina());

        Assert.assertEquals(produtorDatos.a1.getNome(), a1.getNome());
        Assert.assertEquals(produtorDatos.a1.getPiscina(), a1.getPiscina());

        s0 = (Socio) perDao.recuperaPorDni(produtorDatos.s0.getDni());
        s1 = (Socio) perDao.recuperaPorDni(produtorDatos.s1.getDni());

        Assert.assertEquals(produtorDatos.s0.getNome(), s0.getNome());
        Assert.assertEquals(produtorDatos.s0.getTelefono(), s0.getTelefono());
        Assert.assertEquals(produtorDatos.s0.getDni(), s0.getDni());
        Assert.assertEquals(produtorDatos.s0.getDataNacemento(), s0.getDataNacemento());
        Assert.assertEquals(produtorDatos.s0.getDataAlta(), s0.getDataAlta());

        Assert.assertEquals(produtorDatos.s1.getNome(), s1.getNome());
        Assert.assertEquals(produtorDatos.s1.getTelefono(), s1.getTelefono());
        Assert.assertEquals(produtorDatos.s1.getDni(), s1.getDni());
        Assert.assertEquals(produtorDatos.s1.getDataNacemento(), s1.getDataNacemento());
        Assert.assertEquals(produtorDatos.s1.getDataAlta(), s1.getDataAlta());

        t0 = (Traballador) perDao.recuperaPorDni(produtorDatos.t0.getDni());
        t1 = (Traballador) perDao.recuperaPorDni(produtorDatos.t1.getDni());
        t2 = (Traballador) perDao.recuperaPorDni(produtorDatos.t2.getDni());

        Assert.assertEquals(produtorDatos.t0.getNome(), t0.getNome());
        Assert.assertEquals(produtorDatos.t0.getDni(), t0.getDni());
        Assert.assertEquals(produtorDatos.t0.getDataNacemento(), t0.getDataNacemento());
        Assert.assertEquals(produtorDatos.t0.getPosto(), t0.getPosto());
        Assert.assertEquals(produtorDatos.t0.getSalario(), t0.getSalario());

        Assert.assertEquals(produtorDatos.t1.getNome(), t1.getNome());
        Assert.assertEquals(produtorDatos.t1.getDni(), t1.getDni());
        Assert.assertEquals(produtorDatos.t1.getDataNacemento(), t1.getDataNacemento());
        Assert.assertEquals(produtorDatos.t1.getPosto(), t1.getPosto());
        Assert.assertEquals(produtorDatos.t1.getSalario(), t1.getSalario());

        Assert.assertEquals(produtorDatos.t2.getNome(), t2.getNome());
        Assert.assertEquals(produtorDatos.t2.getDni(), t2.getDni());
        Assert.assertEquals(produtorDatos.t2.getDataNacemento(), t2.getDataNacemento());
        Assert.assertEquals(produtorDatos.t2.getPosto(), t2.getPosto());
        Assert.assertEquals(produtorDatos.t2.getSalario(), t2.getSalario());

        Assert.assertEquals(a0.getNome(), s0.getActividades().first().getNome());
        Assert.assertEquals(a1.getNome(), s0.getActividades().last().getNome());

        Assert.assertEquals(a0.getNome(), s1.getActividades().last().getNome());

        Assert.assertEquals(t0.getActividade().getNome(), a0.getNome());
        Assert.assertEquals(t2.getActividade().getNome(), a0.getNome());

        Assert.assertEquals(t1.getActividade().getNome(), a1.getNome());

    }

}
