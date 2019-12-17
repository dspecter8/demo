package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.ClientRepository;
import com.example.demo.dao.CompteRepository;
import com.example.demo.dao.OperationRepository;
import com.example.demo.entity.Client;
import com.example.demo.entity.Compte;
import com.example.demo.entity.CompteCourant;
import com.example.demo.entity.CompteEpargne;
import com.example.demo.entity.Operation;
import com.example.demo.entity.Retrait;
import com.example.demo.entity.Versement;
import com.example.demo.metier.IBanqueMetier;
/*
 * First Commit  : 16-12-2019 -->
 * Author and Committer changed
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBanqueMetier banqueMetier;

	public static void main(String[] args) {
		 SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		//Test Client
		Client client = clientRepository.save(new Client("Nom 1", "email1@gmail.com"));
		Client client2 = clientRepository.save(new Client("Nom 2", "email2@gmail.com"));
		
		//Test compte
		Compte compte = compteRepository.save(new CompteCourant("CC001", new Date(), 9999, client2, 0.0));
		Compte compte2 =compteRepository.save(new CompteEpargne("CE001", new Date(), 1000, client, 0.0));
		
		//Test Operation
		Operation operation = operationRepository.save(new Versement(new Date(), 2500, compte2));
		Operation operation2 = operationRepository.save(new Retrait(new Date(), 200, compte));
		
		//Test Couche metier
		banqueMetier.verser(compte.getCodeCompte(), 11111111);
		banqueMetier.virement(compte.getCodeCompte(), compte2.getCodeCompte(), 800);
		banqueMetier.retirer(compte2.getCodeCompte(), 45);

	}

}
