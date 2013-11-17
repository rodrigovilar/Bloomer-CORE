package br.ufpb.dce.bloomer.core.test;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.ufpb.dce.bloomer.core.model.Sexo;
import br.ufpb.dce.bloomer.core.model.Usuario;

public class TestesRest {


	private UsuarioResource resource;

	@Before
	public void init() {
		resource = new UsuarioResource();
	}

	@Test
	public void testingRestMethods() {
		assertEquals(0, resource.get().size());

		Usuario user = criarUsuarioPadrao();
		assertEquals(201, resource.post(user));

		List<Usuario> usuarios = resource.get();

		Long ID = usuarios.get(0).getId();
		Usuario newUser = resource.get(ID.toString());
		assertEquals(user, newUser);

		Usuario editedUser = editarUsuario(newUser);
		assertEquals(200, resource.put(user));

		List<Usuario> usuarios2 = resource.get();
		Usuario editedUser2 = resource.get(ID.toString());
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
