package br.ufpb.dce.bloomer.core.model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findUsuariosByPartidas" })
public class Usuario {

    @NotNull
    @Size(min = 2, max = 150)
    private String nome;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar dataNascimento;

    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;

    @NotNull
    @Size(min = 6, max = 30)
    private String senha;

    @NotNull
    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configurador")
    private Set<Jogo> jogos = new HashSet<Jogo>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Set<Partida> partidas = new HashSet<Partida>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destino")
    private Set<Relacao> quemMeSegue = new HashSet<Relacao>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "origem")
    private Set<Relacao> quemEuSigo = new HashSet<Relacao>();
    
    public String toJson() {
		ObjectNode noUsuario = usuario2json(this);
		return noUsuario.toString();
    }
    
    public static String toJsonArray(Collection<Usuario> collection) {
    	ArrayNode arrayDeUsuarios = JsonNodeFactory.instance.arrayNode();

		for (Usuario usuario : collection) {
			ObjectNode noUsuario = usuario2json(usuario);
			arrayDeUsuarios.add(noUsuario);
		}

    	return arrayDeUsuarios.toString();
    }
    
    private static ObjectNode usuario2json(Usuario usuario) {
		ObjectNode noUsuario = JsonNodeFactory.instance.objectNode();
		
		noUsuario.put("id", usuario.getId());
		noUsuario.put("nome", usuario.getNome());	
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(usuario.getDataNascimento().get(Calendar.DATE) + "/");
		strBuilder.append(getMonthName((usuario.getDataNascimento().get(Calendar.MONTH))) + "/");		
		strBuilder.append(usuario.getDataNascimento().get(Calendar.YEAR));
		
		noUsuario.put("dataNascimento", strBuilder.toString());
		noUsuario.put("sexo", usuario.getSexo().name());
		noUsuario.put("login", usuario.getLogin());
		noUsuario.put("senha", usuario.getSenha());
		noUsuario.put("version", usuario.getVersion());
		
		return noUsuario;
	}
    
    public static Usuario fromJsonToUsuario(String json) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonFactory factory = objectMapper.getJsonFactory();
    	try {
			JsonNode usuarioJSON = objectMapper.readTree(factory.createJsonParser(json));
			
			Usuario usuario = new Usuario();
			
			if (usuarioJSON.has("id")) {
				usuario.setId(usuarioJSON.get("id").asLong());
			}
			
			if (usuarioJSON.has("nome")) {
				usuario.setNome(usuarioJSON.get("nome").asText());
			}
			
			if (usuarioJSON.has("dataNascimento")) {
				
				String data_str = usuarioJSON.get("dataNascimento").asText();
				String[] data = data_str.split("/"); 
				
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[0]));
				cal.set(Calendar.MONTH, getMonthNumber(data[1]));  
				cal.set(Calendar.YEAR, Integer.parseInt(data[2])); 
				
				usuario.setDataNascimento(cal);
			}
			
			if (usuarioJSON.has("sexo")) {
				usuario.setSexo(Sexo.valueOf(usuarioJSON.get("sexo").asText()));
			}

			if (usuarioJSON.has("login")) {
				usuario.setLogin(usuarioJSON.get("login").asText());
			}
			
			if (usuarioJSON.has("senha")) {
				usuario.setSenha(usuarioJSON.get("senha").asText());
			}
			
			if (usuarioJSON.has("version")) {
				usuario.setVersion(usuarioJSON.get("version").asInt());
			}

			return usuario;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    // Retorna o nome dos meses: 0-11 to January-December
    public static String getMonthName(int month) {
        return new java.text.DateFormatSymbols().getMonths()[month];
    }
    
    // Retorna o número dos meses: January-December to 0-11
    public static int getMonthNumber(String month) {
        Calendar cal = Calendar.getInstance();
        try {
			cal.setTime(new java.text.SimpleDateFormat("MMM", java.util.Locale.ENGLISH).parse(month));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        return cal.get(Calendar.MONTH);
    }
}
