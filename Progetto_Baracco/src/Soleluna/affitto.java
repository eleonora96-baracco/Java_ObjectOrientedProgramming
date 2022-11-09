package Soleluna;

import java.io.Serializable;

public class affitto implements Serializable {
	// variabili d'istanza, data è senza modificatore al fine di poterla utilizzare in tutto il package
	private String nome;
	protected String data;
	//variabile statica
	private static final int prezzo_base = 70 ;
	static final long serialVersionUID =  7142719863032980414L;
	
	// costruttore
	protected affitto (String nome, String data) {
		this.nome = nome;
		this.data = data;
	}
	//permette di modificare il nome
	protected void setNome (String nome) {
		this.nome = nome;
	}
	//permette di visualizzare il nome
	protected String getNome() {
		return nome;
	}
	//permette di visualizzare il prezzo
	protected static int getPrezzo_base() {
		return prezzo_base;
	}
	//permette di visualizzare la prenotazione,
	protected void visualizza() {
		System.out.println("PRENOTAZIONE AFFITTO");
		System.out.println("Nome:\t" + nome);
		System.out.println("Data: \t" + data);
		System.out.println("Prezzo: \t" + prezzo_base);	
	}

}
