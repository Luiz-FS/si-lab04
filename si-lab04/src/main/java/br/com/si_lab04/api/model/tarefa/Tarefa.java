package br.com.si_lab04.api.model.tarefa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.si_lab04.api.model.tarefa.subtarefa.SubTarefa;

@Entity
public class Tarefa {
	
	public enum Prioridade {
		ALTA,MEDIA,BAIXA;
	}
	
	@Id @GeneratedValue
	private Integer id;
	private String descricao;
	private boolean concluida;
	private Prioridade prioridade;
	private String categoria;
	private String comentario;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	private List<SubTarefa> subTarefas;
	
	public Tarefa() {
		this.concluida = false;
		this.subTarefas = new ArrayList<>();
	}
	
	public void adicionarSubTarefa(SubTarefa subTarefa) {
		this.subTarefas.add(subTarefa);
	}
	
	public SubTarefa buscarSubTarefa(SubTarefa subTarefa) {
		
		int index = this.subTarefas.indexOf(subTarefa);
		
		return this.subTarefas.get(index);
	}
	
	public SubTarefa buscarSubTarefaPorId(Integer idSubTarefa) {
		
		for (SubTarefa subTarefa : this.subTarefas) {
			
			if (subTarefa.getId().equals(idSubTarefa))
				return subTarefa;
		}
		
		return null;
	}
	
	public boolean deletarSubTarefa(Integer idSubTarefa) {
		
		SubTarefa subTarefaEncontrada = buscarSubTarefaPorId(idSubTarefa);
		
		if (subTarefaEncontrada != null) {
			this.subTarefas.remove(subTarefaEncontrada);
			return true;
		} else {
			return false;
		}
	}
	
	public Tarefa(String descricao) {
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean isConcluida() {
		return concluida;
	}

	public void setConcluida(boolean concluida) {
		this.concluida = concluida;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	@Override
	public String toString() {
		
		String saida = this.descricao + "-" + this.id;
		
		return saida;
	}

	public List<SubTarefa> getSubTarefas() {
		return subTarefas;
	}

	public void setSubTarefas(List<SubTarefa> subTarefas) {
		this.subTarefas = subTarefas;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		Tarefa other = (Tarefa) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
}
