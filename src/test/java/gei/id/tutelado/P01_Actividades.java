package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.ActividadeDao;
import gei.id.tutelado.dao.ActividadeDaoJPA;
import gei.id.tutelado.model.Actividade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P01_Actividades {

    private final Logger log = LogManager.getLogger("gei.id.tutelado");

    private static final ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static final Configuracion cfg = new ConfiguracionJPA();
    private static final ActividadeDao actDao = new ActividadeDaoJPA();

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
    public void test01_Recuperacion() {

        Actividade a;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaActividadesSoltas();
        produtorDatos.gravaActividades();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de actividade (sen persoas asociadas) por nome\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por nome existente\n"
                + "\t\t\t\t b) Recuperacion por nome inexistente\n");

        log.info("Probando recuperacion por nome EXISTENTE -------------------------------------------------------");

        a = actDao.recuperaPorNome(produtorDatos.a0.getNome());
        Assert.assertEquals(produtorDatos.a0.getNome(), a.getNome());
        Assert.assertEquals(produtorDatos.a0.getPiscina(), a.getPiscina());

        log.info("");
        log.info("Probando recuperacion por nome INEXISTENTE -----------------------------------------------------");

        a = actDao.recuperaPorNome("iwbvyhuebvuwebvi");
        Assert.assertNull(a);

    }

    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaActividadesSoltas();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo actividade (sen persoas asociadas)\n");

        Assert.assertNull(produtorDatos.a0.getId());
        actDao.almacena(produtorDatos.a0);
        Assert.assertNotNull(produtorDatos.a0.getId());

    }

    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaActividadesSoltas();
        produtorDatos.gravaActividades();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de actividade sen persoas asociadas\n");

        Assert.assertNotNull(actDao.recuperaPorNome(produtorDatos.a0.getNome()));
        actDao.elimina(produtorDatos.a0);
        Assert.assertNull(actDao.recuperaPorNome(produtorDatos.a0.getNome()));

    }

    @Test
    public void test04_Modificacion() {

        Actividade a0, a1;
        String novaPiscina;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaActividadesSoltas();
        produtorDatos.gravaActividades();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dunha actividade sen persoas\n");

        novaPiscina = "Nova piscina";

        a0 = actDao.recuperaPorNome(produtorDatos.a0.getNome());
        Assert.assertNotEquals(novaPiscina, a0.getPiscina());
        a0.setPiscina(novaPiscina);

        actDao.modifica(a0);

        a1 = actDao.recuperaPorNome(produtorDatos.a0.getNome());
        Assert.assertEquals(novaPiscina, a1.getPiscina());

    }

    @Test
    public void test05_Propagacion_Persist() {

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaTraballadoresSoltos();
        produtorDatos.creaActividadesSoltas();
        produtorDatos.a0.engadirTraballador(produtorDatos.t0);
        produtorDatos.a0.engadirTraballador(produtorDatos.t1);

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da gravación de novo traballador con actividades (novas) asociadas\n");

        Assert.assertNull(produtorDatos.a0.getId());
        Assert.assertNull(produtorDatos.t0.getId());
        Assert.assertNull(produtorDatos.t1.getId());

        log.info("Gravando na BD usuario con entradas de log -----------------------------------------------------");

        actDao.almacena(produtorDatos.a0);

        Assert.assertNotNull(produtorDatos.a0.getId());
        Assert.assertNotNull(produtorDatos.t0.getId());
        Assert.assertNotNull(produtorDatos.t1.getId());

    }

}
