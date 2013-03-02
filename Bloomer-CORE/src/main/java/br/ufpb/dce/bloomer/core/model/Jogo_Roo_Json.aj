// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.Jogo;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Jogo_Roo_Json {
    
    public String Jogo.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Jogo Jogo.fromJsonToJogo(String json) {
        return new JSONDeserializer<Jogo>().use(null, Jogo.class).deserialize(json);
    }
    
    public static String Jogo.toJsonArray(Collection<Jogo> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Jogo> Jogo.fromJsonArrayToJogoes(String json) {
        return new JSONDeserializer<List<Jogo>>().use(null, ArrayList.class).use("values", Jogo.class).deserialize(json);
    }
    
}
