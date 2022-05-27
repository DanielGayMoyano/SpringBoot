package curso.java;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class Servlet {

	@GetMapping("")
	public String entrar() {
		return "login";
	}
	@PostMapping("/login")
	public String resultado(@ModelAttribute Usuario usuario,@RequestParam String nombre, @RequestParam String pass){
		Usuario user=usuario;
		return "resultados";
	}
	
}
