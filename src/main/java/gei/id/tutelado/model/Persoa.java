package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;

@TableGenerator(name = "xeradorIdsPersoas", table = "taboa_ids",
        pkColumnName = "nome_id", pkColumnValue = "idPersoa",
        valueColumnName = "ultimo_valor_id",
        allocationSize = 1)

@NamedQueries({
        @NamedQuery(name = "Persoa.recuperaPorDni",
                query = "SELECT p FROM Persoa p where p.dni = :dni"),
        @NamedQuery(name = "Persoa.recuperaTodas",
                query = "SELECT p FROM Persoa p ")
})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persoa {

    @Id
    @GeneratedValue(generator = "xeradorIdsPersoas")
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nome;

    private LocalDate dataNacemento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNacemento() {
        return dataNacemento;
    }

    public void setDataNacemento(LocalDate dataNacemento) {
        this.dataNacemento = dataNacemento;
    }

}
