import javax.persistence.EntityManager;

import br.ufpb.dce.bloomer.core.model.TipoJogo;
import br.ufpb.dce.bloomer.core.web.TipoJogoController;


public class MainTestControllers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TipoJogoController controller = new TipoJogoController();
		System.out.println(controller.toString());
		
		System.err.println(controller.deleteFromJson((long) 1));
	}

}
