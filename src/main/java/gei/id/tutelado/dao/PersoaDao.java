package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persoa;

public interface PersoaDao {

    void setup(Configuracion config);

    // OPERACIONS CRUD BASICAS
    Persoa almacena(Persoa persoa);

    Persoa modifica(Persoa persoa);

    void elimina(Persoa persoa);

    Persoa recuperaPorDni(String dni);

}
