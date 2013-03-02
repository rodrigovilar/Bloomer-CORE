package br.ufpb.dce.bloomer.core.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Jogo {

    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull
    @ManyToOne
    private TipoJogo tipo;

    @ManyToOne
    private Usuario configurador;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo")
    private Set<Questao> questoes = new HashSet<Questao>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo")
    private Set<Partida> partidas = new HashSet<Partida>();
}
