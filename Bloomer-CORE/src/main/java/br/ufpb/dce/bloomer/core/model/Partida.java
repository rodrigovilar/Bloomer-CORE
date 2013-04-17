package br.ufpb.dce.bloomer.core.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findPartidasByJogoAndUsuario", "findPartidasByJogo" })
public class Partida {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar dataHora;

    @NotNull
    @Min(0L)
    @Max(100L)
    private int acerto;

    @NotNull
    private Boolean concluiu;

    @NotNull
    private int escore;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Jogo jogo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partida")
    private Set<Resposta> respostas = new HashSet<Resposta>();
}
