package br.ufpb.dce.bloomer.core.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findUsuariosByPartidas" })
public class Usuario {

    @NotNull
    @Size(min = 2, max = 150)
    private String nome;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar dataNascimento;

    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;

    @NotNull
    @Size(min = 6, max = 30)
    private String senha;

    @NotNull
    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configurador")
    private Set<Jogo> jogos = new HashSet<Jogo>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Set<Partida> partidas = new HashSet<Partida>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destino")
    private Set<Relacao> quemMeSegue = new HashSet<Relacao>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "origem")
    private Set<Relacao> quemEuSigo = new HashSet<Relacao>();
}
