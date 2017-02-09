package br.com.si_lab04.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.si_lab04.api.controller.operacoes.OperacoesComBanco;
import br.com.si_lab04.api.model.tarefa.subtarefa.SubTarefa;

@RestController
public class SubTarefaController {

	@Autowired
	private OperacoesComBanco operacoesComBanco;
	
	@RequestMapping(method=RequestMethod.POST, value="/listas/tarefa/{idTarefa}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SubTarefa> salvarSubTarefa(@PathVariable Integer idTarefa, @RequestBody SubTarefa subTarefa) {

		SubTarefa subTarefaSalva = operacoesComBanco.salvarSubTarefa(idTarefa, subTarefa);

		if (subTarefaSalva != null)
			return new ResponseEntity<>(subTarefaSalva, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/listas/tarefa/subTarefa", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SubTarefa> alterarSubTarefa(@RequestBody SubTarefa subTarefa) {
		
		SubTarefa subTarefaAlterada = operacoesComBanco.alterarSubTarefa(subTarefa);
		
		if (subTarefaAlterada != null)
			return new ResponseEntity<>(subTarefaAlterada, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/listas/tarefa/{idTarefa}/{idSubTarefa}")
	public ResponseEntity<SubTarefa> deletarSubTarefa(@PathVariable Integer idTarefa, @PathVariable Integer idSubTarefa) {
		
		boolean deletou = operacoesComBanco.deletarSubTarefa(idTarefa, idSubTarefa);
		
		if (deletou)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
