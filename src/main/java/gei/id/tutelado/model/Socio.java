package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@NamedQuery(name = "Socio.recuperaSociosMayoresQue",
        query = "SELECT s FROM Socio s WHERE (:dataActual - s.dataNacemento) >= :idade")

@Entity
public class Socio extends Persoa {

    @Column(unique = true)
    private String telefono;

    @Column(nullable = false)
    private LocalDate dataAlta;

    @ManyToMany(fetch = FetchType.LAZY)
    private final SortedSet<Actividade> actividades = new TreeSet<>();

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

    public SortedSet<Actividade> getActividades() {
        return actividades;
    }

    public void engadirActividade(Actividade actividade) {
        // É un sorted set, engadimos sempre por orde de nome
        this.actividades.add(actividade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Socio)) return false;
        if (!super.equals(o)) return false;
        Socio socio = (Socio) o;
        return Objects.equals(getTelefono(), socio.getTelefono()) && Objects.equals(getDataAlta(), socio.getDataAlta()) && Objects.equals(getActividades(), socio.getActividades());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTelefono(), getDataAlta(), getActividades());
    }

}
