package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@TableGenerator(name = "xeradorIdsActividades", table = "taboa_ids",
        pkColumnName = "nome_id", pkColumnValue = "idActividade",
        valueColumnName = "ultimo_valor_id",
        allocationSize = 1)

@NamedQueries({
        @NamedQuery(name = "Actividade.recuperaPorNome",
                query = "SELECT a FROM Actividade a where a.nome = :nome"),
        @NamedQuery(name = "Actividade.recuperaTodas",
                query = "SELECT a FROM Actividade a"),
        @NamedQuery(name = "Actividade.recuperaActividadesConMinSocios",
                query = "SELECT a FROM Actividade a WHERE " +
                        "(SELECT COUNT(s) FROM Socio s JOIN s.actividades sa WHERE sa.id = a.id) >= :minSocios")
})

@Entity
public class Actividade {

    @Id
    @GeneratedValue(generator = "xeradorIdsActividades")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String piscina;

    @ElementCollection
    private final List<String> material = new ArrayList<>();

    @OneToMany()
    private final SortedSet<Actividade> traballadores = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPiscina() {
        return piscina;
    }

    public void setPiscina(String piscina) {
        this.piscina = piscina;
    }

    public List<String> getMaterial() {
        return material;
    }

    public SortedSet<Actividade> getTraballadores() {
        return traballadores;
    }

}
