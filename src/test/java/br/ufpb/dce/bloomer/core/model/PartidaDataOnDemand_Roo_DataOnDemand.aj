// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.JogoDataOnDemand;
import br.ufpb.dce.bloomer.core.model.Partida;
import br.ufpb.dce.bloomer.core.model.PartidaDataOnDemand;
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

privileged aspect PartidaDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PartidaDataOnDemand: @Component;
    
    private Random PartidaDataOnDemand.rnd = new SecureRandom();
    
    private List<Partida> PartidaDataOnDemand.data;
    
    @Autowired
    JogoDataOnDemand PartidaDataOnDemand.jogoDataOnDemand;
    
    @Autowired
    UsuarioDataOnDemand PartidaDataOnDemand.usuarioDataOnDemand;
    
    public Partida PartidaDataOnDemand.getNewTransientPartida(int index) {
        Partida obj = new Partida();
        setAcerto(obj, index);
        setConcluiu(obj, index);
        setDataHora(obj, index);
        setEscore(obj, index);
        return obj;
    }
    
    public void PartidaDataOnDemand.setAcerto(Partida obj, int index) {
        int acerto = index;
        if (acerto < 0 || acerto > 100) {
            acerto = 100;
        }
        obj.setAcerto(acerto);
    }
    
    public void PartidaDataOnDemand.setConcluiu(Partida obj, int index) {
        Boolean concluiu = Boolean.TRUE;
        obj.setConcluiu(concluiu);
    }
    
    public void PartidaDataOnDemand.setDataHora(Partida obj, int index) {
        Calendar dataHora = Calendar.getInstance();
        obj.setDataHora(dataHora);
    }
    
    public void PartidaDataOnDemand.setEscore(Partida obj, int index) {
        int escore = index;
        obj.setEscore(escore);
    }
    
    public Partida PartidaDataOnDemand.getSpecificPartida(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Partida obj = data.get(index);
        Long id = obj.getId();
        return Partida.findPartida(id);
    }
    
    public Partida PartidaDataOnDemand.getRandomPartida() {
        init();
        Partida obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Partida.findPartida(id);
    }
    
    public boolean PartidaDataOnDemand.modifyPartida(Partida obj) {
        return false;
    }
    
    public void PartidaDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Partida.findPartidaEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Partida' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Partida>();
        for (int i = 0; i < 10; i++) {
            Partida obj = getNewTransientPartida(i);
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
