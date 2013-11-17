package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.ufpb.dce.bloomer.core.model.Sexo;
import br.ufpb.dce.bloomer.core.model.Usuario;

public class TestesRest {

	private static String USUARIOS = "http://localhost:8080/Bloomer-CORE/usuarios";

	private Resource resource;

	@Before
	public void init() {
		resource = new Resource(USUARIOS);
	}

	@Test
	public void testingRestMethods() {
		assertEquals("[]", resource.get());

		Usuario user = criarUsuarioPadrao();
		assertEquals(201, resource.post(user.toJson()));

		String jsonArray = resource.get();
		List<Usuario> usuarios = (List<Usuario>) Usuario
				.fromJsonArrayToUsuarios(jsonArray);

		Long ID = usuarios.get(0).getId();
		String json = resource.get(ID.toString());
		Usuario newUser = Usuario.fromJsonToUsuario(json);
		assertEquals(user, newUser);

		Usuario editedUser = editarUsuario(newUser);
		assertEquals(200, resource.put(user.toJson()));

		String jsonArray2 = resource.get();
		List<Usuario> usuarios2 = (List<Usuario>) Usuario
				.fromJsonArrayToUsuarios(jsonArray2);
		String json2 = resource.get(ID.toString());
		Usuario editedUser2 = Usuario.fromJsonToUsuario(json2);
		assertEquals(editedUser, editedUser2);

		assertEquals(200, resource.delete(ID.toString()));
	}

	private Usuario editarUsuario(Usuario newUser) {
		Usuario editedUser = newUser;
		editedUser.setNome("Rodrigo");
		editedUser.setDataNascimento(new GregorianCalendar(1981, 02, 05)); // YYYYMMDD
		editedUser.setSexo(Sexo.Masculino);
		editedUser.setLogin("rodrigovilar");
		editedUser.setSenha("654321");
		return editedUser;
	}

	private Usuario criarUsuarioPadrao() {
		Usuario user = new Usuario();
		user.setNome("Rafael");
		user.setDataNascimento(new GregorianCalendar(1992, 01, 22)); // yyyyMMdd
		user.setSexo(Sexo.Masculino);
		user.setLogin("marcusrafael");
		user.setSenha("123456");
		return user;
	}
}
