package com.chivisgt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.chivisgt.models.entity.Cliente;
import com.chivisgt.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	@Autowired
	private IClienteService clienteService;
	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("cliente", clienteService.findAll());
		return"listar";
	}
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Crear Cliente");
		return"form";

	}
	
	@RequestMapping(value="/form",method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente,BindingResult result, SessionStatus status, Model model ) {
		
		if(result.hasErrors()) {
	  model.addAttribute("titulo", "Crear Cliente");
	  return"/form";
			
			
		}
		clienteService.save(cliente);
		status.setComplete();
		return "listar";
	}
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable (value="id") Long id,Model model) {
		Cliente cliente = new Cliente();
		if(id>0) {
			cliente = clienteService.findOne(id);
			model.addAttribute("cliente", cliente);
			
			
			
		}else {
			
			return"/listar";
		}
		
		return "form";
		
		
	}
	@RequestMapping("/delete/{id}")
	public String eliminar(@PathVariable(value="id")Long id,Model model) {
	if(id>0) {
		clienteService.delete(id);
			
		}
		return"redirect:/listar";
	}

}
