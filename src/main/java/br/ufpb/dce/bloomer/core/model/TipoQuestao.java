package br.ufpb.dce.bloomer.core.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
public class TipoQuestao {

    @NotNull
    @Size(max = 100)
    private String nome;

    @NotNull
    @Size(max = 2000)
    private String descricao;

    @ManyToOne
    private TipoJogo jogo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private Set<Questao> questoes = new HashSet<Questao>();
    
    public String toJson() {
		ObjectNode noTipoQuestao = tipoquestao2json(this);
		return noTipoQuestao.toString();    	
    }
    
    public static String toJsonArray(Collection<TipoQuestao> collection) {
		ArrayNode arrayDeTipoQuestoes = JsonNodeFactory.instance.arrayNode();

		for (TipoQuestao tipoquestao : collection) {
			ObjectNode noTipoQuestao = tipoquestao2json(tipoquestao);
			arrayDeTipoQuestoes.add(noTipoQuestao);
		}

    	return arrayDeTipoQuestoes.toString();
    }
    
	private static ObjectNode tipoquestao2json(TipoQuestao tipoquestao) {
		ObjectNode noTipoQuestao = JsonNodeFactory.instance.objectNode();
		
		noTipoQuestao.put("id", tipoquestao.getId());
		noTipoQuestao.put("nome", tipoquestao.getNome());
		noTipoQuestao.put("descricao", tipoquestao.getDescricao());
		noTipoQuestao.put("tipojogo", tipoquestao.getJogo().getNome());
		noTipoQuestao.put("version", tipoquestao.getVersion());	
		
		return noTipoQuestao;
	}
    
    public static TipoQuestao fromJsonToTipoQuestao(String json) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonFactory factory = objectMapper.getJsonFactory();
    	try {
			JsonNode tipoquestaoJSON = objectMapper.readTree(factory.createJsonParser(json));
			
			TipoQuestao tipoquestao = new TipoQuestao();
			
			if (tipoquestaoJSON.has("id")) {
				tipoquestao.setId(tipoquestaoJSON.get("id").asLong());
			}
			
			if (tipoquestaoJSON.has("nome")) {
				tipoquestao.setNome(tipoquestaoJSON.get("nome").asText());
			}

			if (tipoquestaoJSON.has("descricao")) {
				tipoquestao.setDescricao(tipoquestaoJSON.get("descricao").asText());
			}

			if (tipoquestaoJSON.has("tipojogo")) {
				TipoJogo tipojogo = TipoJogo.findTipoJogo(tipoquestaoJSON.get("tipojogo").asLong());
				tipoquestao.setJogo(tipojogo);
			}

			if (tipoquestaoJSON.has("version")) {
				tipoquestao.setVersion(tipoquestaoJSON.get("version").asInt());
			}

			return tipoquestao;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
