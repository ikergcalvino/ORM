package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.PersoaDao;
import gei.id.tutelado.dao.PersoaDaoJPA;
import gei.id.tutelado.dao.TraballadorDao;
import gei.id.tutelado.dao.TraballadorDaoJPA;
import gei.id.tutelado.model.Traballador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P01_Traballadores {

    private final Logger log = LogManager.getLogger("gei.id.tutelado");

    private static final ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static final Configuracion cfg = new ConfiguracionJPA();
    private static final TraballadorDao traDao = new TraballadorDaoJPA();
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
        traDao.setup(cfg);

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

        Traballador t;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaTraballadoresSoltos();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de trabajador (sen actividades asociadas) por dni\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por dni existente\n"
                + "\t\t\t\t b) Recuperacion por dni inexistente\n");

        log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------------");

        t = (Traballador) perDao.recuperaPorDni(produtorDatos.t0.getDni());
        Assert.assertEquals(produtorDatos.t0.getDni(), t.getDni());
        Assert.assertEquals(produtorDatos.t0.getNome(), t.getNome());
        Assert.assertEquals(produtorDatos.t0.getDataNacemento(), t.getDataNacemento());
        Assert.assertEquals(produtorDatos.t0.getPosto(), t.getPosto());
        Assert.assertEquals(produtorDatos.t0.getSalario(), t.getSalario());
        Assert.assertEquals(produtorDatos.t0.getDataContratacion(), t.getDataContratacion());

        log.info("");
        log.info("Probando recuperacion por dni INEXISTENTE ------------------------------------------------------");

        t = (Traballador) perDao.recuperaPorDni("iwbvyhuebvuwebvi");
        Assert.assertNull(t);

    }

    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaTraballadoresSoltos();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo trabajador (sen actividades asociadas)\n");

        Assert.assertNull(produtorDatos.t0.getId());
        perDao.almacena(produtorDatos.t0);
        Assert.assertNotNull(produtorDatos.t0.getId());

    }

    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaTraballadoresSoltos();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de trabajador sen actividades asociadas\n");

        Assert.assertNotNull(perDao.recuperaPorDni(produtorDatos.t0.getDni()));
        perDao.elimina(produtorDatos.t0);
        Assert.assertNull(perDao.recuperaPorDni(produtorDatos.t0.getDni()));

    }

    @Test
    public void test04_Modificacion() {

        Traballador t0, t1;
        String novoNome;

        log.info("");
        log.info("Configurando situación de partida do test ------------------------------------------------------");

        produtorDatos.creaTraballadoresSoltos();
        produtorDatos.gravaTraballadores();

        log.info("");
        log.info("Inicio do test ---------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dun trabajador sen actividades\n");

        novoNome = "Nome novo";

        t0 = (Traballador) perDao.recuperaPorDni(produtorDatos.t0.getDni());
        Assert.assertNotEquals(novoNome, t0.getNome());
        t0.setNome(novoNome);

        perDao.modifica(t0);

        t1 = (Traballador) perDao.recuperaPorDni(produtorDatos.t0.getDni());
        Assert.assertEquals(novoNome, t1.getNome());

    }

}
