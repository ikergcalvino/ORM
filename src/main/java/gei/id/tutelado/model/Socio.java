package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@NamedQuery(name = "Socio.recuperaSociosConActividadesEn",
        query = "SELECT s FROM Socio s LEFT OUTER JOIN s.actividades a ON a.piscina = :piscina")

@Entity
public class Socio extends Persoa {

    @OrderBy("nome ASC")
    @ManyToMany(fetch = FetchType.EAGER)
    private final SortedSet<Actividade> actividades = new TreeSet<>();

    @Column(unique = true)
    private String telefono;

    @Column(nullable = false)
    private LocalDate dataAlta;

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

    public void addActividade(Actividade actividade) {
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
