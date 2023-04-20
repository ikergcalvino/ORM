package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = "Traballador.recuperaTraballadoresDePiscina",
                query = "SELECT t FROM Traballador t JOIN t.actividade a WHERE a.piscina = :piscina"),
        @NamedQuery(name = "Traballador.recuperaSalarioMedio",
                query = "SELECT AVG(t.salario) FROM Traballador t")
})

@Entity
public class Traballador extends Persoa {

    @Column(nullable = false)
    private String posto;

    private Double salario;

    @Column(nullable = false)
    private LocalDate dataContratacion;

    @ManyToOne()
    @JoinColumn(nullable = false)
    private Actividade actividade;

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getDataContratacion() {
        return dataContratacion;
    }

    public void setDataContratacion(LocalDate dataContratacion) {
        this.dataContratacion = dataContratacion;
    }

    public Actividade getActividade() {
        return actividade;
    }

    public void setActividade(Actividade actividade) {
        this.actividade = actividade;
    }

}
