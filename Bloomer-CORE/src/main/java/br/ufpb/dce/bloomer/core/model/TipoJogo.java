package br.ufpb.dce.bloomer.core.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Enumerated;
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
@RooJson(deepSerialize = true)
public class TipoJogo {

    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull
    @Size(max = 2000)
    private String descricao;

    @NotNull
    @Size(max = 100)
    private String autor;

    @Enumerated
    private Plafatorma plataforma;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private Set<Jogo> jogos = new HashSet<Jogo>();

    @ElementCollection
    private Set<NivelTaxonomia> niveisTaxonomia = new HashSet<NivelTaxonomia>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo")
    private Set<TipoQuestao> questoes = new HashSet<TipoQuestao>();
}
