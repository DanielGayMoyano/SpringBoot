package curso.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import curso.java.model.Usuario;

@Controller
@RequestMapping("")
public class EjerController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/que")
	public String que() {
		return "que";
	}
	
	@GetMapping("/contacto")
	public String contacto() {
		return "contacto";
	}
	@GetMapping("/saludoParametro")
	public String saludoParametro(@RequestParam String nombre,@RequestParam(required = false,defaultValue = "password")String pass,Model model) {
		model.addAttribute("nombre",nombre);
		model.addAttribute("pass",pass);
		return "saludoParametro";
	}
	@GetMapping("/datos")
	public String datos(@RequestParam(required=false)String nombre, @RequestParam String pass,Model model) {
		Usuario usuario=new Usuario();
		usuario.setNombre(nombre);
		usuario.setPass(pass);
		model.addAttribute(usuario);
		return "datos";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
