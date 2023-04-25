package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.PersoaDao;
import gei.id.tutelado.dao.PersoaDaoJPA;
import gei.id.tutelado.dao.SocioDao;
import gei.id.tutelado.dao.SocioDaoJPA;
import gei.id.tutelado.model.Socio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P01_Socios {

    private final Logger log = LogManager.getLogger("gei.id.tutelado");

    private static final ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static final Configuracion cfg = new ConfiguracionJPA();
    private static final PersoaDao perDao = new PersoaDaoJPA();
    private static final SocioDao socDao = new SocioDaoJPA();

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

        socDao.setup(cfg);
        perDao.setup(cfg);

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

        Socio s;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaSociosSoltos();
        produtorDatos.gravaSocios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de socio (sen actividades asociadas) por dni\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por dni existente\n"
                + "\t\t\t\t b) Recuperacion por dni inexistente\n");

        log.info("Probando recuperacion por dni EXISTENTE --------------------------------------------------");

        s = (Socio) perDao.recuperaPorDni(produtorDatos.s0.getDni());
        Assert.assertEquals(produtorDatos.s0.getDni(), s.getDni());
        Assert.assertEquals(produtorDatos.s0.getNome(), s.getNome());
        Assert.assertEquals(produtorDatos.s0.getDataNacemento(), s.getDataNacemento());
        Assert.assertEquals(produtorDatos.s0.getTelefono(), s.getTelefono());
        Assert.assertEquals(produtorDatos.s0.getDataAlta(), s.getDataAlta());

        log.info("");
        log.info("Probando recuperacion por dni INEXISTENTE -----------------------------------------------");

        s = (Socio) perDao.recuperaPorDni("iwbvyhuebvuwebvi");
        Assert.assertNull(s);

    }


    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaSociosSoltos();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo socio (sen actividades asociadas)\n");

        Assert.assertNull(produtorDatos.s0.getId());
        perDao.almacena(produtorDatos.s0);
        Assert.assertNotNull(produtorDatos.s0.getId());

    }

    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaSociosSoltos();
        produtorDatos.gravaSocios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de socio sen actividades asociadas\n");

        Assert.assertNotNull(perDao.recuperaPorDni(produtorDatos.s0.getDni()));
        perDao.elimina(produtorDatos.s0);
        Assert.assertNull(perDao.recuperaPorDni(produtorDatos.s0.getDni()));

    }

    @Test
    public void test04_Modificacion() {

        Socio s0, s1;
        String novoNome;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaSociosSoltos();
        produtorDatos.gravaSocios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dun socio sen actividades\n");

        novoNome = "Nome novo";

        s0 = (Socio) perDao.recuperaPorDni(produtorDatos.s0.getDni());
        Assert.assertNotEquals(novoNome, s0.getNome());
        s0.setNome(novoNome);

        perDao.modifica(s0);

        s1 = (Socio) perDao.recuperaPorDni(produtorDatos.s0.getDni());
        Assert.assertEquals(novoNome, s1.getNome());

    }

}
