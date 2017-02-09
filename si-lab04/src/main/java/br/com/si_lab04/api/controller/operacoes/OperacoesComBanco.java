package br.com.si_lab04.api.controller.operacoes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.si_lab04.api.model.lista.ListaDeTarefas;
import br.com.si_lab04.api.model.tarefa.Tarefa;
import br.com.si_lab04.api.model.tarefa.subtarefa.SubTarefa;
import br.com.si_lab04.api.repository.ListaDeTarefasRepositorio;
import br.com.si_lab04.api.repository.SubTarefaRepositorio;
import br.com.si_lab04.api.repository.TarefaRepositorio;

@Component
public class OperacoesComBanco {

	@Autowired
	private ListaDeTarefasRepositorio listaDeTarefasRepositorio;

	@Autowired
	private TarefaRepositorio tarefaRepositorio;
	
	@Autowired
	private SubTarefaRepositorio subTarefaRepository;
	
	private static OperacoesComBanco operacoesComBanco;

	private OperacoesComBanco() {
		
	}
	
	public static OperacoesComBanco getOperacoesComBanco() {
		
		if (operacoesComBanco == null) {
			operacoesComBanco = new OperacoesComBanco();
		}
		
		return operacoesComBanco;
	}

	public List<ListaDeTarefas> buscarTodasAsListas() {
		return listaDeTarefasRepositorio.findAll();
	}

	public ListaDeTarefas buscarListaTarefaPorId(Integer id) {
		return listaDeTarefasRepositorio.findOne(id);
	}

	public ListaDeTarefas salvarListaDeTarefa(ListaDeTarefas listaDeTarefas) {
		return listaDeTarefasRepositorio.save(listaDeTarefas);
	}

	public Tarefa salvarTarefa(Integer idLista, Tarefa tarefa) {

		ListaDeTarefas listaEncontrada = buscarListaTarefaPorId(idLista);

		if(listaEncontrada != null) {

			listaEncontrada.adicionarTarefa(tarefa);

			ListaDeTarefas listaSalva = salvarListaDeTarefa(listaEncontrada);

			Tarefa tarefaSalva = listaSalva.buscarTarefa(tarefa);

			return tarefaSalva;
		} else {

			return null;

		}
	}
	
	public SubTarefa salvarSubTarefa(Integer idTarefa, SubTarefa subTarefa) {

		Tarefa tarefaEncontrada = this.tarefaRepositorio.findOne(idTarefa);

		if(tarefaEncontrada != null) {

			tarefaEncontrada.adicionarSubTarefa(subTarefa);;

			Tarefa tarefaSalva = alterarTarefa(tarefaEncontrada);

			SubTarefa subTarefaSalva = tarefaSalva.buscarSubTarefa(subTarefa);

			return subTarefaSalva;
		} else {

			return null;

		}
	}

	public boolean deletarListaDeTarefa(Integer id) {

		ListaDeTarefas lista = listaDeTarefasRepositorio.findOne(id);

		if (lista == null) {
			return false;
		} else {
			listaDeTarefasRepositorio.delete(id);
			return true;
		}
	}

	public boolean deletarTarefa(Integer idLista, Integer idTarefa) {

		ListaDeTarefas lista = listaDeTarefasRepositorio.findOne(idLista);

		if (lista == null) {
			return false;
		} else {

			boolean deletou = lista.deletarTarefa(idTarefa);

			if (deletou) {
				salvarListaDeTarefa(lista);
				return true;

			} else {
				return false;
			}
		}
	}
	
	public boolean deletarSubTarefa(Integer idTarefa, Integer idSubTarefa) {
		
		Tarefa tarefaEncontrada = tarefaRepositorio.findOne(idTarefa);

		if (tarefaEncontrada == null) {
			return false;
		} else {

			boolean deletou = tarefaEncontrada.deletarSubTarefa(idSubTarefa);

			if (deletou) {
				alterarTarefa(tarefaEncontrada);
				return true;

			} else {
				return false;
			}
		}
	}

	public void deletarTodasAsListas() {
		listaDeTarefasRepositorio.deleteAll();
	}

	public boolean deletarTodasAsTarefas(Integer idLista) {
	
		ListaDeTarefas listaEncontrada = listaDeTarefasRepositorio.findOne(idLista);
	
		if (listaEncontrada != null) {
	
			listaEncontrada.getTarefas().clear();
	
			listaDeTarefasRepositorio.save(listaEncontrada);
	
			return true;
	
		} else {
	
			return false;
		}
	}

	public ListaDeTarefas alterarNomeDaLista(Integer idLista, String novoNome) {
	
		ListaDeTarefas listaEncotrada = listaDeTarefasRepositorio.findOne(idLista);
	
		if (listaEncotrada != null) {
			listaEncotrada.setNome(novoNome);
			ListaDeTarefas listaSalva = listaDeTarefasRepositorio.save(listaEncotrada);
	
			return listaSalva;
	
		} else {
			return null;
		}
	}

	public Tarefa alterarTarefa(Tarefa tarefa) {
		return tarefaRepositorio.save(tarefa);
	}
	
	public SubTarefa alterarSubTarefa(SubTarefa subTarefa) {
		return subTarefaRepository.save(subTarefa);
	}
} 
