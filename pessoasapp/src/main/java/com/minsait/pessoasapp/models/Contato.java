package com.minsait.pessoasapp.models;

import com.minsait.pessoasapp.models.enums.TipoContato;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_contato")
public class Contato implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer tipoContato;
    @Column(nullable = false)
    @NotBlank
    private String contato;

    public Contato() {}

    public Contato(TipoContato tipoContato, String contato) {
        setTipoContato(tipoContato);
        this.contato = contato;
    }

    public Contato(Long id, TipoContato tipoContato, String contato) {
        this.id = id;
        setTipoContato(tipoContato);
        this.contato = contato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoContato getTipoContato() {
        return TipoContato.valorCodigo(tipoContato);
    }

    public void setTipoContato(TipoContato tipoContato) {
        if(tipoContato != null) {
            this.tipoContato = tipoContato.getCodigo();
        }
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
