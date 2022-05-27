package curso.spring.condicional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/condicional")
public class UsuarioController {

	@GetMapping("")
	public String index() {
		return "condicional/index";
	}
	
	@GetMapping("/login1")
	public String login1(HttpSession session) {
		session.setAttribute("usuario", null);
		return "condicional/login";
	}
	
	@GetMapping("/login2")
	public String login2(HttpSession session) {
		Usuario usuario = new Usuario("Pepe", 1);
		session.setAttribute("usuario", usuario);
		return "condicional/login";
	}
	
	@GetMapping("/rol1")
	public String rol1(HttpSession session) {
		Usuario usuario = new Usuario("Pepe", 1); //admin
		session.setAttribute("usuario", usuario);
		return "condicional/rol";
	}
	
	@GetMapping("/rol2")
	public String rol2(HttpSession session) {
		Usuario usuario = new Usuario("Juan", 2); //cliente
		session.setAttribute("usuario", usuario);
		return "condicional/rol";
	}
	
	@GetMapping("/menu1")
	public String menu1(HttpSession session) {
		session.setAttribute("usuario", null);
		return "condicional/menu";
	}

	@GetMapping("/menu2")
	public String menu2(HttpSession session) {
		Usuario usuario = new Usuario("Pepe", 1);
		session.setAttribute("usuario", usuario);
		return "condicional/menu";
	}
	
}
