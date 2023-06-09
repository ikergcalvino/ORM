package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.*;

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
                        "(SELECT CAST(COUNT(s) AS int) FROM Socio s JOIN s.actividades sa WHERE sa.id = a.id) >= :minSocios")
})

@Entity
public class Actividade implements Comparable<Actividade> {

    @ElementCollection(fetch = FetchType.EAGER)
    private final List<String> material = new ArrayList<>();

    @OrderBy("dni ASC")
    @OneToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private final SortedSet<Traballador> traballadores = new TreeSet<>();

    @Id
    @GeneratedValue(generator = "xeradorIdsActividades")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String piscina;

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

    public void addMaterial(String material) {
        this.material.add(material);
    }

    public SortedSet<Traballador> getTraballadores() {
        return traballadores;
    }

    // Metodos de conveniencia para asegurarnos de que actualizamos os dous extremos da asociación ao mesmo tempo
    public void engadirTraballador(Traballador traballador) {
        if (traballador.getActividade() != null) throw new RuntimeException();
        traballador.setActividade(this);
        // É un sorted set, engadimos sempre por orde de dni
        this.traballadores.add(traballador);
    }

    public void eliminarTraballador(Traballador traballador) {
        if (traballador.getActividade() == null) throw new RuntimeException();
        traballador.setActividade(null);
        // É un sorted set, eliminamos sempre por orde de dni
        this.traballadores.remove(traballador);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actividade)) return false;
        Actividade that = (Actividade) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getPiscina(), that.getPiscina()) && Objects.equals(getMaterial(), that.getMaterial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getPiscina(), getMaterial());
    }

    @Override
    public int compareTo(Actividade other) {
        return this.getNome().compareToIgnoreCase(other.getNome());
    }

}
