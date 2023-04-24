package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Socio;

import java.util.List;

public interface SocioDao {

    void setup(Configuracion config);

    // QUERIES ADICIONAIS
    List<Socio> recuperaSociosMayoresQue(Integer idade);

}
