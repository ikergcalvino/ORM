package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.EntradaLogDao;
import gei.id.tutelado.dao.EntradaLogDaoJPA;
import gei.id.tutelado.dao.UsuarioDao;
import gei.id.tutelado.dao.UsuarioDaoJPA;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Usuario;
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

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();
    private static Configuracion cfg;
    private static UsuarioDao usuDao;
    private static EntradaLogDao logDao;
    private Logger log = LogManager.getLogger("gei.id.tutelado");
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
        cfg = new ConfiguracionJPA();
        cfg.start();

        usuDao = new UsuarioDaoJPA();
        logDao = new EntradaLogDaoJPA();
        usuDao.setup(cfg);
        logDao.setup(cfg);

        produtorDatos = new ProdutorDatosProba();
        produtorDatos.Setup(cfg);
    }

    @AfterClass
    public static void endclose() throws Exception {
        cfg.endUp();
    }


    @Before
    public void setUp() throws Exception {
        log.info("");
        log.info("Limpando BD -----------------------------------------------------------------------------------------------------");
        produtorDatos.limpaBD();
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void test06_todosU() {

        List<Usuario> listaU;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaUsuariosConEntradasLog();
        produtorDatos.gravaUsuarios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Usuario.recuperaTodos\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        listaU = usuDao.recuperaTodos();

        Assert.assertEquals(2, listaU.size());
        Assert.assertEquals(produtorDatos.u0, listaU.get(0));
        Assert.assertEquals(produtorDatos.u1, listaU.get(1));

    }

    @Test
    public void test06_entradasU() {

        List<EntradaLog> listaE;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaUsuariosConEntradasLog();
        produtorDatos.gravaUsuarios();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta EntradaLog.recuperaTodasUsuario\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        listaE = logDao.recuperaTodasUsuario(produtorDatos.u0);
        Assert.assertEquals(0, listaE.size());

        listaE = logDao.recuperaTodasUsuario(produtorDatos.u1);
        Assert.assertEquals(2, listaE.size());
        Assert.assertEquals(produtorDatos.e1A, listaE.get(0));
        Assert.assertEquals(produtorDatos.e1B, listaE.get(1));

    }

}