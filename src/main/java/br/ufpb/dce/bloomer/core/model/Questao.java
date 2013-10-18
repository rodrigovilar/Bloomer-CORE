package br.ufpb.dce.bloomer.core.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Questao {

    @Size(max = 1000)
    private String pergunta;
    
    @Size(max = 4000)
    private String gabarito;

    @ManyToOne
    private Jogo jogo;

    @ManyToOne
    private TipoQuestao tipo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questao")
    private Set<Resposta> respostas = new HashSet<Resposta>();
    
    public String toJson() {
		ObjectNode noQuestao = questao2json(this);
		return noQuestao.toString();
    }
    
    public static String toJsonArray(Collection<Questao> collection) {
		ArrayNode arrayDeQuestoes = JsonNodeFactory.instance.arrayNode();

		for (Questao questao : collection) {
			ObjectNode noQuestao = questao2json(questao);
			arrayDeQuestoes.add(noQuestao);
		}

    	return arrayDeQuestoes.toString();
    }

	private static ObjectNode questao2json(Questao questao) {
		ObjectNode noQuestao = JsonNodeFactory.instance.objectNode();
		
		noQuestao.put("id", questao.getId());
		noQuestao.put("pergunta", questao.getPergunta());
		noQuestao.put("gabarito", questao.getGabarito());
		noQuestao.put("jogo", questao.getJogo().getNome());
		noQuestao.put("tipoquestao", questao.getTipo().getNome());
		noQuestao.put("version", questao.getVersion());	
		
		return noQuestao;
	}
	
    public static Questao fromJsonToQuestao(String json) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonFactory factory = objectMapper.getJsonFactory();
    	try {
			JsonNode questaoJSON = objectMapper.readTree(factory.createJsonParser(json));
			
			Questao questao = new Questao();
			
			if (questaoJSON.has("id")) {
				questao.setId(questaoJSON.get("id").asLong());
			}
			
			if (questaoJSON.has("gabarito")) {
				questao.setGabarito(questaoJSON.get("gabarito").asText());
			}

			if (questaoJSON.has("pergunta")) {
				questao.setPergunta(questaoJSON.get("pergunta").asText());
			}
			
			if (questaoJSON.has("jogo")) {
				Jogo jogo = Jogo.findJogo(questaoJSON.get("jogo").asLong());
				questao.setJogo(jogo);
			}

			if (questaoJSON.has("tipoquestao")) {
				TipoQuestao tipo = TipoQuestao.findTipoQuestao(questaoJSON.get("tipoquestao").asLong());
				questao.setTipo(tipo);
			}

			if (questaoJSON.has("version")) {
				questao.setVersion(questaoJSON.get("version").asInt());
			}

			return questao;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
