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
import br.com.si_lab04.api.model.tarefa.Tarefa;

@RestController
public class TarefaController {

	@Autowired
	private OperacoesComBanco operacoesComBanco;
	
	
	@RequestMapping(method=RequestMethod.POST, value="/listas/{idLista}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tarefa> salvarTarefa(@PathVariable Integer idLista, @RequestBody Tarefa tarefa) {

		Tarefa tarefaSalva = operacoesComBanco.salvarTarefa(idLista, tarefa);

		if (tarefaSalva != null)
			return new ResponseEntity<>(tarefaSalva, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/listas/tarefa", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tarefa> alterarTarefa(@RequestBody Tarefa tarefa) {

		Tarefa tarefaAlterada = operacoesComBanco.alterarTarefa(tarefa);

		return new ResponseEntity<>(tarefaAlterada, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/listas/{idLista}/{idTarefa}")
	public ResponseEntity<Tarefa> deletarTarefa(@PathVariable Integer idLista, @PathVariable Integer idTarefa) {
	
		boolean deletou = operacoesComBanco.deletarTarefa(idLista, idTarefa);
	
		if (deletou)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
