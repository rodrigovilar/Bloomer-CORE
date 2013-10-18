package br.ufpb.dce.bloomer.core.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
@RooJpaActiveRecord(finders = { "findPartidasByJogoAndUsuario",
		"findPartidasByJogo" })
public class Partida {

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Calendar dataHora;

	@NotNull
	@Min(0L)
	@Max(100L)
	private int acerto;

	@NotNull
	private Boolean concluiu;

	@NotNull
	private int escore;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Jogo jogo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "partida")
	private Set<Resposta> respostas = new HashSet<Resposta>();

	public String toJson() {
		ObjectNode noPartida = partida2json(this);
		return noPartida.toString();
	}

	public static String toJsonArray(Collection<Partida> collection) {
		ArrayNode arrayDePartidas = JsonNodeFactory.instance.arrayNode();

		for (Partida partida : collection) {
			ObjectNode noPartida = partida2json(partida);
			arrayDePartidas.add(noPartida);
		}

		return arrayDePartidas.toString();
	}

	private static ObjectNode partida2json(Partida partida) {
		ObjectNode noPartida = JsonNodeFactory.instance.objectNode();

		noPartida.put("id", partida.getId());

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(partida.getDataHora().get(Calendar.DATE) + "/");
		strBuilder.append(getMonthName((partida.getDataHora()
				.get(Calendar.MONTH))) + "/");
		strBuilder.append(partida.getDataHora().get(Calendar.YEAR));

		noPartida.put("dataHora", strBuilder.toString());
		noPartida.put("acerto", partida.getAcerto());
		noPartida.put("concluiu", partida.getConcluiu());
		noPartida.put("escore", partida.getEscore());
		noPartida.put("usuario", partida.getUsuario().getId());
		noPartida.put("jogo", partida.getJogo().getId());
		noPartida.put("version", partida.getVersion());

		return noPartida;
	}

	public static Partida fromJsonToPartida(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonFactory factory = objectMapper.getJsonFactory();
		try {
			JsonNode partidaJSON = objectMapper.readTree(factory
					.createJsonParser(json));

			Partida partida = new Partida();

			if (partidaJSON.has("id")) {
				partida.setId(partidaJSON.get("id").asLong());
			}

			if (partidaJSON.has("dataHora")) {

				String data_str = partidaJSON.get("dataHora").asText();
				String[] data = data_str.split("/");

				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[0]));
				cal.set(Calendar.MONTH, getMonthNumber(data[1]));
				cal.set(Calendar.YEAR, Integer.parseInt(data[2]));

				partida.setDataHora(cal);
			}

			if (partidaJSON.has("acerto")) {
				partida.setAcerto(partidaJSON.get("acerto").asInt());
			}

			if (partidaJSON.has("concluiu")) {
				partida.setConcluiu(partidaJSON.get("concluiu").asBoolean());
			}

			if (partidaJSON.has("escore")) {
				partida.setEscore(partidaJSON.get("escore").asInt());
			}

			if (partidaJSON.has("usuario")) {
				Usuario usuario = Usuario.findUsuario(partidaJSON
						.get("usuario").asLong());
				partida.setUsuario(usuario);
			}

			if (partidaJSON.has("jogo")) {
				Jogo jogo = Jogo.findJogo(partidaJSON.get("jogo").asLong());
				partida.setJogo(jogo);
			}

			if (partidaJSON.has("version")) {
				partida.setVersion(partidaJSON.get("version").asInt());
			}

			return partida;

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
			cal.setTime(new java.text.SimpleDateFormat("MMM",
					java.util.Locale.ENGLISH).parse(month));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cal.get(Calendar.MONTH);
	}
}
