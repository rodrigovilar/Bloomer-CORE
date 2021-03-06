// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.JogoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.Questao;
import br.ufpb.dce.bloomer.core.model.QuestaoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.TipoQuestaoDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect QuestaoDataOnDemand_Roo_DataOnDemand {
    
    declare @type: QuestaoDataOnDemand: @Component;
    
    private Random QuestaoDataOnDemand.rnd = new SecureRandom();
    
    private List<Questao> QuestaoDataOnDemand.data;
    
    @Autowired
    private JogoDataOnDemand QuestaoDataOnDemand.jogoDataOnDemand;
    
    @Autowired
    private TipoQuestaoDataOnDemand QuestaoDataOnDemand.tipoQuestaoDataOnDemand;
    
    public Questao QuestaoDataOnDemand.getNewTransientQuestao(int index) {
        Questao obj = new Questao();
        setGabarito(obj, index);
        setPergunta(obj, index);
        return obj;
    }
    
    public void QuestaoDataOnDemand.setGabarito(Questao obj, int index) {
        String gabarito = "gabarito_" + index;
        if (gabarito.length() > 4000) {
            gabarito = gabarito.substring(0, 4000);
        }
        obj.setGabarito(gabarito);
    }
    
    public void QuestaoDataOnDemand.setPergunta(Questao obj, int index) {
        String pergunta = "pergunta_" + index;
        if (pergunta.length() > 1000) {
            pergunta = pergunta.substring(0, 1000);
        }
        obj.setPergunta(pergunta);
    }
    
    public Questao QuestaoDataOnDemand.getSpecificQuestao(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Questao obj = data.get(index);
        Long id = obj.getId();
        return Questao.findQuestao(id);
    }
    
    public Questao QuestaoDataOnDemand.getRandomQuestao() {
        init();
        Questao obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Questao.findQuestao(id);
    }
    
    public boolean QuestaoDataOnDemand.modifyQuestao(Questao obj) {
        return false;
    }
    
    public void QuestaoDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Questao.findQuestaoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Questao' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Questao>();
        for (int i = 0; i < 10; i++) {
            Questao obj = getNewTransientQuestao(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
