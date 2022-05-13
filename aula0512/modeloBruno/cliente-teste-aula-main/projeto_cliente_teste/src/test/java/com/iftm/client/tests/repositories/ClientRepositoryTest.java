package com.iftm.client.tests.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;

@DataJpaTest
public class ClientRepositoryTest {
		
		@Autowired
		private ClientRepository repositorio;
		
		/**
		 * Cenário de teste 1
		 * Objetivo: Verificar se a exclusão realmente apaga um registro existente.
			monta o cenário, 
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- definir o id de um cliente que exista em import.sql
			executa a ação
				- executar o método de exclusão por id
				- executar o método de buscar por id
			e valida a saída.
				- verificar se o resultado do método de busca é falso
		 */
		@Test
		public void testarSeDeleteApagaClienteComIdExistente() {
			//cenário
			long id = 1;
			repositorio.deleteById(id);
			Optional<Client> resposta = repositorio.findById(id);
			Assertions.assertFalse(resposta.isPresent());
			
		}
		
		/**
		 * Cenário de teste 2
		 * Objetivo: Verificar se a exclusão retorna um erro quando um id não existente é informado.
			monta o cenário, 
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- definir o id de um cliente que não exista em import.sql
			executa a ação
				- executar o método de exclusão por id
			e valida a saída.
				- verificar se ocorre o erro: EmptyResultDataAccessException
		 */	
		@Test
		public void testarSeDeleteRetornaExceptionCasoIdNaoExiste() {
			long id = 10000;
			Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{repositorio.deleteById(id);});
		}

		/**
		  Cenário de teste 3.
		  Objetivo: Verificar se a exclusão de todos elementos realmente apaga todos os registros do Banco de dados.
			monta o cenário,
				- arquivo import.sql carrega o cenário (clientes cadastrados)
			executa a ação
				- executar o método de exclusão de todos registros
			e valida a saída.
				- consultar todos os registros do banco e verificar se retorna vazio.
		 */
		@Test 
		public void testarSeDeleteApagaTodosRegistros() {
			repositorio.deleteAll();
			List<Client> resultado = repositorio.findAll();			
			Assertions.assertTrue(resultado.isEmpty());
		}
		
		/**
		 Cenário de teste 4
		 Objetivo: Verificar se a exclusão de uma entidade existente no banco de dados realmente ocorre.
			monta o cenário,
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- id de um cliente que existe em import.sql
			executa a ação
				- executa o método encontrar por id para retornar a entidade do	cliente com id informado.
				- executa o método apagar a entidade
				- executar novamente o método encontrar por id e verificar se o retorno dele é vazio
			e valida a saída.
				- verifica se o retorno do método encontrar por id é vazio.
		 */
		@Test
		public void testarSeDeleteApagaUmRegistroComIDExistente() {
			long id = 4;
			Optional<Client> cliente = repositorio.findById(id);
			repositorio.delete(cliente.get());
			Optional<Client> resultado = repositorio.findById(id);
			Assertions.assertTrue(resultado.isEmpty());
		}
		
		/*
		 Cenário de Teste 5
		 Objetivo: Verificar se um cliente pode ser excluído pelo cpf.
			monta o cenário,
				- arquivo import.sql carrega o cenário (clientes cadastrados)
				- cpf de um cliente cadastrado (10919444522)
			executa a ação
				- executar um método para excluir um cliente pelo cpf (não existe ainda).
				- buscar um cliente pelo cpf (não existe)
			e valida a saída.
				- a busca deve retornar vazia.	
		 */
		@Test
		public void testarSeDeleteApagaUmClientePorCpfExiste() {
			String cpfExistente = "10619244881";
			repositorio.deleteByCpf(cpfExistente);
			System.out.println(repositorio.findAll().size());
			Optional<Client> resultado = repositorio.findByCpf(cpfExistente);
			Assertions.assertTrue(resultado.isEmpty());
		}
		
		@Test
		public void testar() {
			double salarioI=2000;
			repositorio.deleteByIncomeGreaterThan(salarioI);
			List<Client> resultado = repositorio.findByIncomeGreaterThan(salarioI);
			Assertions.assertTrue(resultado.isEmpty());
		}
		
		//exemplo Bruno em sala
		@Test 	
		void testeBuscaClientesInicioCPFqueExiste() {
			String ptCpf = "104%";
			List <Client> listaClient;
			int tamanhoResp = 2;
			String cpfEsperado [] = {"10419244771","10419344882"};
			
			//execução do metodo que esta sendo testado
			listaClient = repositorio.findByCpfLike(ptCpf);
			
			//Comparação 
			//existe elemento na lista
			Assertions.assertFalse(listaClient.isEmpty());
			Assertions.assertEquals(tamanhoResp, listaClient.size() );
			Assertions.assertEquals(cpfEsperado [0], listaClient.get(0).getCpf() );
			Assertions.assertEquals(cpfEsperado [1], listaClient.get(1).getCpf() );
			
		}
		
		@Test
		void buscarClientName() {
			String buscaName = "Jose Saramago"; //usar o lower no repositorie com sql para não haver problemas com letra maiuscula ou minuscula
			List <Client> listaNome;
			int tamanhoEsperado = 1;
			String nomeEsperado [] = {"Jose Saramago"};
			
			listaNome =  repositorio.findByName(buscaName);
			
			Assertions.assertFalse(listaNome.isEmpty());
			Assertions.assertEquals(tamanhoEsperado, listaNome.size() );
			Assertions.assertEquals(nomeEsperado [0],listaNome.get(0).getName() );
			
			
		}
		
		@Test
		void buscarSalAcimaDoisMil500() {
			double buscaSalario = 2500;
			List<Client> listaClientes;
			int tamanhoEsperado = 5;
			String clientesEsperados [] = {"Silvio Almeida", "Toni Morrison", "Jose Saramago", "Djamila Ribeiro", "Carolina Maria de Jesus"};
		
			
			listaClientes = repositorio.findByIncomeGreaterThan(buscaSalario);
			
			Assertions.assertFalse(listaClientes.isEmpty());
			//Assertions.assertEquals(tamanhoEsperado, listaClientes.size());
			
		}
		
		@Test
		void abaixoDeMile600() {
			double buscaSalario = 1600;
			List<Client> listaClientes;
			int tamanhoEsperado = 3;
			String clientesEsperados [] = {"Silvio Almeida", "Toni Morrison", "Jose Saramago"};
		
			
			listaClientes = repositorio.findByIncomeLessThan(buscaSalario);
			
			Assertions.assertFalse(listaClientes.isEmpty());
			
		}
		
		@Test
		void salarioEntre1500_e_2500() {
			double buscaMin = 1500;
			double buscaMax = 2500;
			
			List<Client> listaClientes;
			int tamanhoEsperado = 5;
			String clientesEsperados [] = {"Silvio Almeida", "Toni Morrison", "Jose Saramago", "Djamila Ribeiro", "Carolina Maria de Jesus"};
		
			
			listaClientes = repositorio.findByIncomeBetween(buscaMin, buscaMax);
			
			Assertions.assertFalse(listaClientes.isEmpty());
			
		}
		
		@Test
		void buscarIntervaloData() {
			Instant dtIncio = Instant.parse("2020-07-13T20:50:00Z");		
			Instant dtAtual = Instant.now();
			
			List<Client> listaClientes = repositorio.findByBirthDateBetween(dtIncio, dtAtual );

			
			Assertions.assertFalse(listaClientes.isEmpty());
			
		}
		
		
		@Test
		void updateCliente() {
			long id = 1;
			Optional <Client> resultado = repositorio.findById(id);  //resultado recebe o numero da memoria da entidade
			resultado.get().setIncome(2000.0);						//faz o set do novo salario
			repositorio.save(resultado.get());
			Optional <Client> testeSalario = repositorio.findById(id);
			double resultadoEsperado = 2000.0;
			Assertions.assertEquals(resultadoEsperado, testeSalario.get().getIncome());
			
		}
		
		
}
