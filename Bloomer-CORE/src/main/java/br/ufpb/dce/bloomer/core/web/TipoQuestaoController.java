package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.TipoQuestao;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tipoquestaos")
@Controller
@RooWebScaffold(path = "tipoquestaos", formBackingObject = TipoQuestao.class)
public class TipoQuestaoController {
}
