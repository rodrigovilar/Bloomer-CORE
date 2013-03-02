package br.ufpb.dce.bloomer.core.model;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class Resposta {

    @Lob
    private String conteudo;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Questao questao;
}
