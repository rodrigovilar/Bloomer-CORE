// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.Jogo;
import br.ufpb.dce.bloomer.core.model.Partida;
import br.ufpb.dce.bloomer.core.model.Resposta;
import br.ufpb.dce.bloomer.core.model.Usuario;
import java.util.Calendar;
import java.util.Set;

privileged aspect Partida_Roo_JavaBean {
    
    public Calendar Partida.getDataHora() {
        return this.dataHora;
    }
    
    public void Partida.setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }
    
    public int Partida.getAcerto() {
        return this.acerto;
    }
    
    public void Partida.setAcerto(int acerto) {
        this.acerto = acerto;
    }
    
    public Boolean Partida.getConcluiu() {
        return this.concluiu;
    }
    
    public void Partida.setConcluiu(Boolean concluiu) {
        this.concluiu = concluiu;
    }
    
    public int Partida.getEscore() {
        return this.escore;
    }
    
    public void Partida.setEscore(int escore) {
        this.escore = escore;
    }
    
    public Usuario Partida.getUsuario() {
        return this.usuario;
    }
    
    public void Partida.setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Jogo Partida.getJogo() {
        return this.jogo;
    }
    
    public void Partida.setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
    
    public Set<Resposta> Partida.getRespostas() {
        return this.respostas;
    }
    
    public void Partida.setRespostas(Set<Resposta> respostas) {
        this.respostas = respostas;
    }
    
}
