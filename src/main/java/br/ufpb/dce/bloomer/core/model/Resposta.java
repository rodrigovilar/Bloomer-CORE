package br.ufpb.dce.bloomer.core.model;

import java.util.Collection;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
public class Resposta {

    @Size(max = 4000)
    private String conteudo;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Questao questao;
    

    public String toJson() {
		ObjectNode noResposta = resposta2json(this);
		return noResposta.toString();
    }
    
    public static String toJsonArray(Collection<Resposta> collection) {
		ArrayNode arrayDeRespostas = JsonNodeFactory.instance.arrayNode();

		for (Resposta resposta : collection) {
			ObjectNode noResposta = resposta2json(resposta);
			arrayDeRespostas.add(noResposta);
		}

    	return arrayDeRespostas.toString();
    }
    
    private static ObjectNode resposta2json(Resposta resposta){
		ObjectNode noResposta = JsonNodeFactory.instance.objectNode();
		
		noResposta.put("id", resposta.getId());
		noResposta.put("conteudo", resposta.getConteudo());
		noResposta.put("partida", resposta.getPartida().getId());
		noResposta.put("questao", resposta.getQuestao().getPergunta());
		noResposta.put("version", resposta.getVersion());	
		
		return noResposta;
    }
    
    public static Resposta fromJsonToResposta(String json) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonFactory factory = objectMapper.getJsonFactory();
    	try {
			JsonNode respostaJSON = objectMapper.readTree(factory.createJsonParser(json));
			
			Resposta resposta = new Resposta();
			
			if (respostaJSON.has("id")) {
				resposta.setId(respostaJSON.get("id").asLong());
			}
			
			if (respostaJSON.has("conteudo")) {
				resposta.setConteudo(respostaJSON.get("conteudo").asText());
			}

			if (respostaJSON.has("partida")) {
				Partida partida = Partida.findPartida(respostaJSON.get("partida").asLong());
				resposta.setPartida(partida);
			}

			if (respostaJSON.has("questao")) {
				Questao questao = Questao.findQuestao(respostaJSON.get("questao").asLong());
				resposta.setQuestao(questao);
			}

			if (respostaJSON.has("version")) {
				resposta.setVersion(respostaJSON.get("version").asInt());
			}

			return resposta;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }    
}
