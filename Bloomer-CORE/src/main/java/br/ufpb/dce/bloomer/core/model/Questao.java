package br.ufpb.dce.bloomer.core.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Questao {

    @Lob
    private String gabarito;

    @ManyToOne
    private Jogo jogo;

    @ManyToOne
    private TipoQuestao tipo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questao")
    private Set<Resposta> respostas = new HashSet<Resposta>();
}
