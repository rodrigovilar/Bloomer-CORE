package br.ufpb.dce.bloomer.core.model;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
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
public class Jogo {

    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @NotNull
    @ManyToOne
    private TipoJogo tipo;

    @ManyToOne
    private Usuario configurador;
    
    @ElementCollection
    private Set<NivelTaxonomia> niveisTaxonomia = new HashSet<NivelTaxonomia>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo")
    private Set<Questao> questoes = new HashSet<Questao>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jogo")
    private Set<Partida> partidas = new HashSet<Partida>();
    
    public String toJson() {
		ObjectNode noJogo = jogo2json(this);
		return noJogo.toString();
    }
    
    public static String toJsonArray(Collection<Jogo> collection) {
    	ArrayNode arrayDeJogos = JsonNodeFactory.instance.arrayNode();

		for (Jogo jogo : collection) {
			ObjectNode noJogo = jogo2json(jogo);
			arrayDeJogos.add(noJogo);
		}

    	return arrayDeJogos.toString();
    }
    
    private static ObjectNode jogo2json(Jogo jogo) {
		ObjectNode noJogo = JsonNodeFactory.instance.objectNode();
		
		noJogo.put("id", jogo.getId());
		noJogo.put("nome", jogo.getNome());			
		noJogo.put("tipojogo", jogo.getTipo().getId());
		noJogo.put("configurador", jogo.getConfigurador().getId());
		noJogo.put("niveisTaxonomia", jogo.getNiveisTaxonomia().toString());
		noJogo.put("version", jogo.getVersion());
		
		return noJogo;
	}
    
    public static Jogo fromJsonToJogo(String json) {
    	ObjectMapper objectMapper = new ObjectMapper();
		JsonFactory factory = objectMapper.getJsonFactory();
    	try {
			JsonNode jogoJSON = objectMapper.readTree(factory.createJsonParser(json));
			
			Jogo jogo = new Jogo();
			
			if (jogoJSON.has("id")) {
				jogo.setId(jogoJSON.get("id").asLong());
			}

			if (jogoJSON.has("nome")) {
				jogo.setNome(jogoJSON.get("nome").asText());
			}
			
			if (jogoJSON.has("configurador")) {
				Usuario conf = Usuario.findUsuario(jogoJSON.get("configurador").asLong());
				jogo.setConfigurador(conf);
			}

			if (jogoJSON.has("tipojogo")) {
				TipoJogo tipo = TipoJogo.findTipoJogo(jogoJSON.get("tipojogo").asLong());
				jogo.setTipo(tipo);
			}

			if (jogoJSON.has("version")) {
				jogo.setVersion(jogoJSON.get("version").asInt());
			}
			
			if (jogoJSON.has("niveisTaxonomia")) {
				Set<NivelTaxonomia> set = new HashSet<NivelTaxonomia>();
				for (int i = 0; i < jogoJSON.get("niveisTaxonomia").size(); i++){
					set.add(NivelTaxonomia.valueOf(jogoJSON.get("niveisTaxonomia").get(i).asText()));
				}
				jogo.setNiveisTaxonomia(set);
			}

			return jogo;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
