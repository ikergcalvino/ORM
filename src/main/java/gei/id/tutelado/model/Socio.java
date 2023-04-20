package gei.id.tutelado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "Socio.recuperaSociosMayoresQue",
        query = "SELECT s FROM Socio s WHERE (CURRENT_DATE - s.dataNacemento) >= :dataAlta")

@Entity
public class Socio extends Persoa {

    @Column(unique = true)
    private String telefono;

    @Column(nullable = false)
    private LocalDate dataAlta;

    @ManyToMany()
    private List<Actividade> actividades = new ArrayList<>();

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getDataAlta() {
        return dataAlta;
    }

    public void setDataAlta(LocalDate dataAlta) {
        this.dataAlta = dataAlta;
    }

    public List<Actividade> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividade> actividades) {
        this.actividades = actividades;
    }

}