package br.ufpb.dce.bloomer.core.test;

import java.util.List;

import br.ufpb.dce.bloomer.core.model.Usuario;

public class UsuarioResource extends TypedResource<Usuario> {

	
	private static String USUARIOS = "http://localhost:8080/Bloomer-CORE/usuarios";

	
	public UsuarioResource() {
		super(USUARIOS);
	}

	@Override
	protected List<Usuario> toList(String jsonArray) {
		return (List<Usuario>) Usuario.fromJsonArrayToUsuarios(jsonArray);
	}

	@Override
	protected Usuario toObject(String json) {
		return Usuario.fromJsonToUsuario(json);
	}

	@Override
	protected String toJson(Usuario t) {
		return t.toJson();
	}

}
