package br.com.si_lab04.api.model.lista;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.si_lab04.api.model.tarefa.Tarefa;

@Entity
public class ListaDeTarefas {

	@Id @GeneratedValue
	private Integer id;
	private String nome;

	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Tarefa> tarefas;

	public ListaDeTarefas() {
		this.tarefas = new ArrayList<>();
	}

	public void adicionarTarefa(Tarefa tarefa) {
		this.tarefas.add(tarefa);
	}

	public Tarefa buscarTarefa(Tarefa tarefa) {

		int indexTarefa = this.tarefas.indexOf(tarefa);

		if (indexTarefa >= 0)
			return this.tarefas.get(indexTarefa);	
		else 
			return null;

	}

	public Tarefa buscarTarefaPorId(Integer id) {

		for (Tarefa tarefa : this.tarefas) {

			if(tarefa.getId().equals(id))
				return tarefa;
		}

		return null;
	}

	public boolean deletarTarefa(Integer id) {

		Tarefa tarefaEncontrada = buscarTarefaPorId(id);

		if (tarefaEncontrada != null) {
			this.tarefas.remove(tarefaEncontrada);
			return true;
		} else {
			return false;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((tarefas == null) ? 0 : tarefas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListaDeTarefas other = (ListaDeTarefas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (tarefas == null) {
			if (other.tarefas != null)
				return false;
		} else if (!tarefas.equals(other.tarefas))
			return false;
		return true;
	}

	@Override
	public String toString() {

		String saida = this.nome + "-" + this.id +"\n";

		for(Tarefa tarefa : this.tarefas) {
			saida += tarefa + "\n";
		}

		return saida;
	}
}
