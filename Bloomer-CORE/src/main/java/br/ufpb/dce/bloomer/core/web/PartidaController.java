package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.Partida;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/partidas")
@Controller
@RooWebScaffold(path = "partidas", formBackingObject = Partida.class)
@RooWebJson(jsonObject = Partida.class)
public class PartidaController {
}
