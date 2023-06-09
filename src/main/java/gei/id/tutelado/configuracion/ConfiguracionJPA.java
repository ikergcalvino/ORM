package gei.id.tutelado.configuracion;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConfiguracionJPA implements Configuracion {

    private EntityManagerFactory emf = null;

    @Override
    public void start() {
        emf = Persistence.createEntityManagerFactory("TuteladoPU");
    }

    @Override
    public Object get(String artifact) {
        if (artifact.equals("EMF")) {
            return emf;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void endUp() {
        emf.close();
    }
}
