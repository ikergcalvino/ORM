package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.EntradaLog;
import gei.id.tutelado.model.Usuario;

import java.util.List;

public interface EntradaLogDao {

    void setup(Configuracion config);

    // OPERACIONS CRUD BASICAS
    EntradaLog almacena(EntradaLog log);

    EntradaLog modifica(EntradaLog log);

    void elimina(EntradaLog log);

    EntradaLog recuperaPorCodigo(String codigo);

    //QUERIES ADICIONAIS
    List<EntradaLog> recuperaTodasUsuario(Usuario u);

}
