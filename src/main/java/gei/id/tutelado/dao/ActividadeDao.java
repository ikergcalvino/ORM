package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Actividade;

import java.util.List;

public interface ActividadeDao {

    void setup(Configuracion config);

    // OPERACIONS CRUD BASICAS
    Actividade almacena(Actividade actividade);

    Actividade modifica(Actividade actividade);

    void elimina(Actividade actividade);

    Actividade recuperaPorNome(String nome);

    // OPERACIONS POR ATRIBUTOS LAZY
    Actividade restauraTraballadores(Actividade actividade);

    // QUERIES ADICIONAIS
    List<Actividade> recuperaTodas();

    List<Actividade> recuperaActividadesConMinSocios(Integer minSocios);

}
