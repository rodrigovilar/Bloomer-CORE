// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.Relacao;
import br.ufpb.dce.bloomer.core.model.RelacaoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.UsuarioDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect RelacaoDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RelacaoDataOnDemand: @Component;
    
    private Random RelacaoDataOnDemand.rnd = new SecureRandom();
    
    private List<Relacao> RelacaoDataOnDemand.data;
    
    @Autowired
    UsuarioDataOnDemand RelacaoDataOnDemand.usuarioDataOnDemand;
    
    public Relacao RelacaoDataOnDemand.getNewTransientRelacao(int index) {
        Relacao obj = new Relacao();
        setDataInicio(obj, index);
        return obj;
    }
    
    public void RelacaoDataOnDemand.setDataInicio(Relacao obj, int index) {
        Calendar dataInicio = Calendar.getInstance();
        obj.setDataInicio(dataInicio);
    }
    
    public Relacao RelacaoDataOnDemand.getSpecificRelacao(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Relacao obj = data.get(index);
        Long id = obj.getId();
        return Relacao.findRelacao(id);
    }
    
    public Relacao RelacaoDataOnDemand.getRandomRelacao() {
        init();
        Relacao obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Relacao.findRelacao(id);
    }
    
    public boolean RelacaoDataOnDemand.modifyRelacao(Relacao obj) {
        return false;
    }
    
    public void RelacaoDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Relacao.findRelacaoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Relacao' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Relacao>();
        for (int i = 0; i < 10; i++) {
            Relacao obj = getNewTransientRelacao(i);
            try {
                obj.persist();
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
