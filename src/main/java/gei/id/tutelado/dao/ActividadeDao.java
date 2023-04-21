package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Actividade;

public interface ActividadeDao {

    void setup(Configuracion config);

    // OPERACIONS CRUD BASICAS
    Actividade almacena(Actividade actividade);

    Actividade modifica(Actividade actividade);

    void elimina(Actividade actividade);

    Actividade recuperaPorNome(String nome);

}
