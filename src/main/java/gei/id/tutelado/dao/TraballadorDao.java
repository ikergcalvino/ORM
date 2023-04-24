package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Traballador;

import java.util.List;

public interface TraballadorDao {

    void setup(Configuracion config);

    // QUERIES ADICIONAIS
    List<Traballador> recuperaTraballadoresDePiscina(String piscina);

    Double recuperaSalarioMedio();

}
