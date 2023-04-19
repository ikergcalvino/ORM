package gei.id.tutelado.configuracion;

public interface Configuracion {
    void start();

    Object get(String artifact);

    void endUp();
}
