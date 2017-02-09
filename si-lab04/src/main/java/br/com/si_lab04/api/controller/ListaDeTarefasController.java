package br.com.si_lab04.api.controller;

import java.util.List;

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
import br.com.si_lab04.api.model.lista.ListaDeTarefas;
import br.com.si_lab04.api.model.tarefa.Tarefa.Prioridade;

@RestController
public class ListaDeTarefasController {

	@Autowired
	private OperacoesComBanco operacoesComBanco;

	public ListaDeTarefasController() {
	}

	@RequestMapping(method=RequestMethod.GET, value="/listas", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ListaDeTarefas>> buscarTodasAsListas() {

		List<ListaDeTarefas> listas = operacoesComBanco.buscarTodasAsListas();

		return new ResponseEntity<>(listas, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.GET, value="/listas/prioridades", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prioridade[]> buscarListasPrioridades() {
	
		Prioridade[] listas = Prioridade.values();
	
		return new ResponseEntity<>(listas, HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST, value="/listas", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ListaDeTarefas> salvarLista(@RequestBody ListaDeTarefas listaDeTarefas) {
		
		ListaDeTarefas listaSalva = operacoesComBanco.salvarListaDeTarefa(listaDeTarefas);

		return new ResponseEntity<>(listaSalva, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/listas/{idLista}/{novoNome}")
	public ResponseEntity<ListaDeTarefas> alterarNomeLista(@PathVariable Integer idLista, @PathVariable String novoNome) {

		ListaDeTarefas listaAlterada = operacoesComBanco.alterarNomeDaLista(idLista, novoNome);

		if (listaAlterada != null)
			return new ResponseEntity<>(listaAlterada, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/listas/tarefas/{idLista}")
	public ResponseEntity<ListaDeTarefas> deletarTodasAsTarefas(@PathVariable Integer idLista) {

		boolean deletou = operacoesComBanco.deletarTodasAsTarefas(idLista);

		if (deletou)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/listas/{id}")
	public ResponseEntity<ListaDeTarefas> deletarLista(@PathVariable Integer id) {
	
		boolean deletou = operacoesComBanco.deletarListaDeTarefa(id);
	
		if (deletou)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/listas")
	public ResponseEntity<ListaDeTarefas> deletarTodasAsListas() {

		operacoesComBanco.deletarTodasAsListas();

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
