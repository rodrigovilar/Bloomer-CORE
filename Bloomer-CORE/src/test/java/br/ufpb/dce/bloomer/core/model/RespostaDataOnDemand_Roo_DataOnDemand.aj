// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.PartidaDataOnDemand;
import br.ufpb.dce.bloomer.core.model.QuestaoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.Resposta;
import br.ufpb.dce.bloomer.core.model.RespostaDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect RespostaDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RespostaDataOnDemand: @Component;
    
    private Random RespostaDataOnDemand.rnd = new SecureRandom();
    
    private List<Resposta> RespostaDataOnDemand.data;
    
    @Autowired
    PartidaDataOnDemand RespostaDataOnDemand.partidaDataOnDemand;
    
    @Autowired
    QuestaoDataOnDemand RespostaDataOnDemand.questaoDataOnDemand;
    
    public Resposta RespostaDataOnDemand.getNewTransientResposta(int index) {
        Resposta obj = new Resposta();
        setConteudo(obj, index);
        return obj;
    }
    
    public void RespostaDataOnDemand.setConteudo(Resposta obj, int index) {
        String conteudo = "conteudo_" + index;
        if (conteudo.length() > 4000) {
            conteudo = conteudo.substring(0, 4000);
        }
        obj.setConteudo(conteudo);
    }
    
    public Resposta RespostaDataOnDemand.getSpecificResposta(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Resposta obj = data.get(index);
        Long id = obj.getId();
        return Resposta.findResposta(id);
    }
    
    public Resposta RespostaDataOnDemand.getRandomResposta() {
        init();
        Resposta obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Resposta.findResposta(id);
    }
    
    public boolean RespostaDataOnDemand.modifyResposta(Resposta obj) {
        return false;
    }
    
    public void RespostaDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Resposta.findRespostaEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Resposta' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Resposta>();
        for (int i = 0; i < 10; i++) {
            Resposta obj = getNewTransientResposta(i);
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
