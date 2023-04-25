package gei.id.tutelado;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({P01_Socios.class, P01_Traballadores.class, P01_Actividades.class,
        P02_Socios_Traballadores_Actividades.class, P03_Consultas.class})
public class AllTests {

}
