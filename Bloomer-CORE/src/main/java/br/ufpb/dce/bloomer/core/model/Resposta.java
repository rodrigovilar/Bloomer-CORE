package br.ufpb.dce.bloomer.core.model;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Resposta {

    @Size(max = 4000)
    private String conteudo;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Questao questao;
}
