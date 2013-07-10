// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.Partida;
import br.ufpb.dce.bloomer.core.model.Usuario;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect Usuario_Roo_Finder {
    
    public static TypedQuery<Usuario> Usuario.findUsuariosByLoginEqualsAndSenhaEquals(String login, String senha) {
        if (login == null || login.length() == 0) throw new IllegalArgumentException("The login argument is required");
        if (senha == null || senha.length() == 0) throw new IllegalArgumentException("The senha argument is required");
        EntityManager em = Usuario.entityManager();
        TypedQuery<Usuario> q = em.createQuery("SELECT o FROM Usuario AS o WHERE o.login = :login  AND o.senha = :senha", Usuario.class);
        q.setParameter("login", login);
        q.setParameter("senha", senha);
        return q;
    }
    
    public static TypedQuery<Usuario> Usuario.findUsuariosByPartidas(Set<Partida> partidas) {
        if (partidas == null) throw new IllegalArgumentException("The partidas argument is required");
        EntityManager em = Usuario.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Usuario AS o WHERE");
        for (int i = 0; i < partidas.size(); i++) {
            if (i > 0) queryBuilder.append(" AND");
            queryBuilder.append(" :partidas_item").append(i).append(" MEMBER OF o.partidas");
        }
        TypedQuery<Usuario> q = em.createQuery(queryBuilder.toString(), Usuario.class);
        int partidasIndex = 0;
        for (Partida _partida: partidas) {
            q.setParameter("partidas_item" + partidasIndex++, _partida);
        }
        return q;
    }
    
}
