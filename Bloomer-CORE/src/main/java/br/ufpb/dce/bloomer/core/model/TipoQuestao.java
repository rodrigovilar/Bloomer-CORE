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
public class TipoQuestao {

    @NotNull
    @Size(max = 100)
    private String nome;

    @NotNull
    @Size(max = 2000)
    private String descricao;

    @ManyToOne
    private TipoJogo jogo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private Set<Questao> questoes = new HashSet<Questao>();
}
