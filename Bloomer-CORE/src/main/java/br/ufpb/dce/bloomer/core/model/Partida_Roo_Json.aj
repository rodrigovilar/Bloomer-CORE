// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.Partida;
import flexjson.JSONDeserializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Partida_Roo_Json {
    
    public static Collection<Partida> Partida.fromJsonArrayToPartidas(String json) {
        return new JSONDeserializer<List<Partida>>().use(null, ArrayList.class).use("values", Partida.class).deserialize(json);
    }
    
}
