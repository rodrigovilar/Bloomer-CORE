// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.ufpb.dce.bloomer.core.model;

import br.ufpb.dce.bloomer.core.model.TipoJogo;
import flexjson.JSONDeserializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect TipoJogo_Roo_Json {
    
    public static Collection<TipoJogo> TipoJogo.fromJsonArrayToTipoJogoes(String json) {
        return new JSONDeserializer<List<TipoJogo>>().use(null, ArrayList.class).use("values", TipoJogo.class).deserialize(json);
    }
    
}
