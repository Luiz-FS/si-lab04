app.controller("agendaDetarefasCtrl", function ($scope, $http) {

	$scope.app = "Agenda de Tarefas";

	$scope.listaDeTarefasSelecionada = {id: null, nome:"Agenda de Tarefas", tarefas:[]};

	$scope.prioridades = [];

	$scope.tableFilter = [
		{filtro: "Todas"},
		{filtro: "Concluidas"},
		{filtro: "Não concluidas"}
		];
	
	$scope.categorias = [];

	$scope.listasDeTarefas = [];

	$scope.tarefaAtual = {};

	$scope.MAXIMO_PERCENTUAL = 100;
	
	$scope.modoEdicao = false;

	$scope.ordenarPor = "ordenar";

	$scope.ordenar = null;

	function ordenarPorPrioridade(value1, value2) {

	    if (value1.value === "ALTA")
	        return -1;
	    else if (value2.value === "ALTA")
	        return 1;
	    else if (value1.value === "MEDIA")
	        return -1;
	    else if (value2.value === "MEDIA")
	        return 1;
	    else
	        return -1;
    }

    $scope.selecionarOrdenacao = function () {

		if ($scope.ordenarPor === "prioridade") {
			$scope.ordenar = ordenarPorPrioridade;
		} else if ($scope.ordenarPor === "descricao") {
			$scope.ordenar = null;
		} else {
			$scope.ordenar = null;
		}
    }
	
	$scope.atualizaCategorias = function() {

		var categorias = ["Todas"];
		
		for (var i = 0; i < $scope.listaDeTarefasSelecionada.tarefas.length; i++) {
			
			var categoria = $scope.listaDeTarefasSelecionada.tarefas[i].categoria;
			
			if ((categorias.indexOf(categoria) === -1) && (categoria != null)) {
				categorias.push(categoria);
			}
		}

		$scope.categorias = categorias;
	}

	$scope.calculaQuantTarefasFiltro = function (categoria, conclusao) {
		var quantidadeConclusao = 0;

		if (typeof categoria !== 'undefined' && categoria !== 'Todas' && categoria !== null) {

            for (var i = 0; i < $scope.listaDeTarefasSelecionada.tarefas.length; i++) {

                var tarefa = $scope.listaDeTarefasSelecionada.tarefas[i];

                if (tarefa.categoria === categoria && tarefa.concluida === conclusao) {
                    quantidadeConclusao++;
                }

            }
        } else{

            for (var i = 0; i < $scope.listaDeTarefasSelecionada.tarefas.length; i++) {

                var tarefa = $scope.listaDeTarefasSelecionada.tarefas[i];

                if (tarefa.concluida === conclusao) {
                    quantidadeConclusao++;
                }
            }
        }

		return quantidadeConclusao;
    }

    $scope.calcQunatTarefasInconcluidas = function () {

		var quantidade = 0;

		for (var i = 0; i < $scope.listasDeTarefas.length; i++) {

			var listaDeTarefas = $scope.listasDeTarefas[i];

			for (var j = 0; j < listaDeTarefas.tarefas.length; j++) {

				var tarefa = listaDeTarefas.tarefas[j];

				if (tarefa.concluida === false) {
					quantidade++;
				}
			}
		}

		return quantidade;
    }

	$scope.adicionaTarefa = function (tarefa) {

		tarefa.concluida = false;

		$scope.salvarTarefa(tarefa);

		delete $scope.tarefa;
	}

	$scope.remove = function (tarefa) {

		var index = getIndexTarefa(tarefa);

		$scope.listaDeTarefasSelecionada.tarefas.splice(index, 1);
		
		$scope.deletarTarefa(tarefa);
	}

	$scope.limparTarefas = function () {

		$scope.listaDeTarefasSelecionada.tarefas = [];
		
		$scope.deletarTodasTarefas($scope.listaDeTarefasSelecionada);
	}
	
	$scope.editarTarefa = function() {
		$scope.modoEdicao = true;
	}
	
	$scope.cancelarEdicao = function() {
		$scope.modoEdicao = false;
	}
	
	$scope.salvarEdicao = function(tarefa) {
		
		$scope.modoEdicao = false;
		$scope.alterarTarefa(tarefa);
		fecharJanela();
	}

	$scope.calculaPorcentagem = function (tarefas) {

		var sum = 0;

		tarefas.forEach(function (tarefa) {

			if(tarefa.concluida) {
				sum += 1;
			}
		});

		var porcent = 0;

		if (tarefas.length > 0) {
			porcent = (sum/tarefas.length) * 100;
		}

		return Math.floor(porcent);
	}

	$scope.filtroTarefas = function (select) {

		if (select == null || select.filtro == "Todos") return '';

		if (select.filtro == "Concluidas") return true;
		else if (select.filtro == "Não concluidas") return false;
	}

    $scope.filtroCategorias = function (categoria) {

        if (categoria == null || categoria == "Todas")
        	return '';
        else return categoria;

    }

	$scope.marcarOudesmarcarConcluida = function (tarefa) {

		if (tarefa.concluida) {
			tarefa.concluida = false;
			$scope.marcarSubtarefasConcluidas(tarefa, false);
		} else {
			tarefa.concluida = true;
            $scope.marcarSubtarefasConcluidas(tarefa, true);
		}
		
		$scope.alterarTarefa(tarefa);
	}
	
	$scope.marcarSubtarefasConcluidas = function(tarefa, concluir) {
		
		for (var i = 0; i < tarefa.subTarefas.length; i++) {
			
			tarefa.subTarefas[i].concluida = concluir;
		}
	}

	var getIndexTarefa = function (tarefa) {

		return $scope.listaDeTarefasSelecionada.tarefas.indexOf(tarefa);
	}


	var visualizador = document.getElementById('tarefa-visualizacao');

	var fechar = document.getElementsByClassName("fechar")[0];

	fechar.onclick = function() {
		fecharJanela();
	};

	window.onclick = function (event) {

		if (event.target == visualizador) {

			fecharJanela();
		}
	}
	
	function fecharJanela () {
		visualizador.style.display = "none";
	}

	$scope.abrirTarefa = function (tarefa) {

		$scope.tarefaAtual = tarefa;
		visualizador.style.display = "block";
	}

	$http({method:'GET', url:'http://localhost:8080/listas'})
	.then(function(response){

		$scope.listasDeTarefas = response.data;

	}, function(response){
		console.log(response.data);
		console.log(response.status);
	});

	$http({method:'GET', url:'http://localhost:8080/listas/prioridades'})
	.then(function(response){

		$scope.prioridades = response.data; 

	}, function(response){
		console.log(response.data);
		console.log(response.status);
	});

	$scope.salvarTarefa = function(tarefa) {
		$http({method:'POST', url:'http://localhost:8080/listas/' + $scope.listaDeTarefasSelecionada.id, data:tarefa})
		.then(function(response){

			$scope.listaDeTarefasSelecionada.tarefas.push(response.data);

			if ($scope.categorias.indexOf(response.data.categoria) == -1 && response.data.categoria !== null) {
				$scope.categorias.push(response.data.categoria);
			} else if (response.data.categoria === null) {
                response.data.categoria = '';
			}

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.deletarTarefa = function(tarefa) {
		$http({method:'DELETE', url:'http://localhost:8080/listas/' + $scope.listaDeTarefasSelecionada.id + "/" + tarefa.id})
		.then(function(response){

			console.log(response.status);

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.alterarTarefa = function(tarefa) {
		$http({method:'PUT', url:'http://localhost:8080/listas/tarefa', data:tarefa})
		.then(function(response){
			
			console.log(response.status);

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.deletarTodasTarefas = function(listaDeTarefas) {
		$http({method:'DELETE', url:'http://localhost:8080/listas/tarefas/' + listaDeTarefas.id})
		.then(function(response){

			console.log(response.status);

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.adicionarNovaLista = function(listaDeTarefas) {
		$http({method:'POST', url:'http://localhost:8080/listas', data: listaDeTarefas})
		.then(function(response){

			$scope.listaDeTarefasSelecionada = response.data;
			$scope.listasDeTarefas.push(response.data);
			delete $scope.listaDeTarefas;

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.adicionarSubTarefa = function(tarefa, subtarefa) {
		
		subtarefa.concluida = false;
		
		$http({method:'POST', url:'http://localhost:8080/listas/tarefa/' + tarefa.id, data: subtarefa})
		.then(function(response){

			tarefa.subTarefas.push(response.data);
			delete $scope.subtarefa;

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.deletarListaTarefas = function(listaDeTarefas) {
		$http({method:'DELETE', url:'http://localhost:8080/listas/' + listaDeTarefas.id})
		.then(function(response){

			var index = $scope.listasDeTarefas.indexOf(listaDeTarefas);
			
			$scope.listasDeTarefas.splice(index, 1);
			$scope.listaDeTarefasSelecionada = {nome:"Agenda de Tarefas", tarefas:[]};

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.deletarTodasAsListas = function() {
		$http({method:'DELETE', url:'http://localhost:8080/listas'})
		.then(function(response){
			
			$scope.listasDeTarefas = [];
			$scope.listaDeTarefasSelecionada = {nome:"Agenda de Tarefas", tarefas:[]};

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.alterarNomeDaLista = function(novoNome) {
		$http({method:'PUT', url:'http://localhost:8080/listas/' + $scope.listaDeTarefasSelecionada.id + '/' + novoNome })
		.then(function(response){
			
			$scope.listaDeTarefasSelecionada.nome = response.data.nome;
			delete $scope.novoNome;

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	
	$scope.alterarSubTarefa = function(tarefa, subTarefa) {
		$http({method:'PUT', url:'http://localhost:8080/listas/tarefa/subTarefa', data: subTarefa})
		.then(function(response){
			
			verificaSubtarefasConcluidas(tarefa);

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	$scope.deletarSubTarefa = function(tarefa, subTarefa) {
		$http({method:'DELETE', url:'http://localhost:8080/listas/tarefa/' + tarefa.id + '/' + subTarefa.id})
		.then(function(response){
			
			var index = tarefa.subTarefas.indexOf(subTarefa);
			
			tarefa.subTarefas.splice(index, 1);
			
			verificaSubtarefasConcluidas(tarefa);

		}, function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	
	function verificaSubtarefasConcluidas(tarefa) {
		
		var concluidas = 0;
		
		for (var i = 0; i < tarefa.subTarefas.length; i++) {
			
			if (tarefa.subTarefas[i].concluida) {
				concluidas++;
			}
		}
		
		if (concluidas == tarefa.subTarefas.length) {
			tarefa.concluida = true;
			$scope.alterarTarefa(tarefa);
		} else {
			tarefa.concluida = false;
			$scope.alterarTarefa(tarefa);
		}
	}

	var download = document.getElementById('baixarLista');

	download.onclick = function () {

        var docDefinition = { content: criaStringPdf() };

        pdfMake.createPdf(docDefinition).download($scope.listaDeTarefasSelecionada.nome + '.pdf');
    }

    function criaStringPdf() {

		var saida = "Lista: " + $scope.listaDeTarefasSelecionada.nome + "\n"
					+ "----Tarefas:\n";

		for (var i = 0; i < $scope.listaDeTarefasSelecionada.tarefas.length; i++) {

			var tarefa = $scope.listaDeTarefasSelecionada.tarefas[i];

			saida += "----" + tarefa.descricao + "\n"
					+ "--------Subtarefas:\n";

			for (var j = 0; j < tarefa.subTarefas.length; j++) {

				var subTarefa = tarefa.subTarefas[j];

				saida += "--------" + subTarefa.descricao + "\n";
			}
		}

		return saida;
    }
});