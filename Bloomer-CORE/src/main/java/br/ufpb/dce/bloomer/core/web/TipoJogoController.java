package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.TipoJogo;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tipojogoes")
@Controller
@RooWebScaffold(path = "tipojogoes", formBackingObject = TipoJogo.class)
@RooWebJson(jsonObject = TipoJogo.class)
public class TipoJogoController {
}
