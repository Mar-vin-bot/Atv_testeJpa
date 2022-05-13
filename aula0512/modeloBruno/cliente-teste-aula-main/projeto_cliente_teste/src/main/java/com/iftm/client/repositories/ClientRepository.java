package com.iftm.client.repositories;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	public void deleteByCpf(String cpfExistente);

	public Optional<Client> findByCpf(String string);

	public void deleteByIncomeGreaterThan(double salarioI);

	public List<Client> findByIncomeGreaterThan(double salarioI);
	
	public List<Client> findByCpfLike (String cpf);
	
	public List<Client> findByName (String buscaName);
	
	public List<Client> findByIncomeGreaterThan (String buscaSalario);
	
	public List<Client> findByIncomeLessThan (double buscaSalario);
	
	public List<Client> findByIncomeBetween (double buscaMax,double buscaMin);
	
	public List<Client> findByBirthDateBetween (Instant dtIncio ,Instant dtAtual);
	
	
	
	
	
}
