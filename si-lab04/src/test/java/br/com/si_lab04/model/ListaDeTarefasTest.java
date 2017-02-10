package br.com.si_lab04.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.si_lab04.api.model.lista.ListaDeTarefas;
import br.com.si_lab04.api.model.tarefa.Tarefa;
import br.com.si_lab04.api.repository.ListaDeTarefasRepositorio;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ListaDeTarefasTest {
	
	@Autowired
	private ListaDeTarefasRepositorio listaDeTarefasRepositorio;

	@Test
	public void testSalvar() {
		
		ListaDeTarefas listaDeTarefas = new ListaDeTarefas();
		listaDeTarefas.setNome("Luiz Fernando");
		
		List<Tarefa> list = new ArrayList<>();
		
		Tarefa t = new Tarefa("Teste");
		list.add(t);
		Tarefa t2 = new Tarefa("Teste2");
		list.add(t2);
		Tarefa t3 = new Tarefa("Teste3");
		list.add(t3);
		Tarefa t4 = new Tarefa("Teste4");
		list.add(t4);
		
		listaDeTarefas.setTarefas(list);
		
		ListaDeTarefas lista = listaDeTarefasRepositorio.save(listaDeTarefas);
		
		System.out.println(lista);
		
	}
}
