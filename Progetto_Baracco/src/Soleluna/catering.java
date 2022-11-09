package Soleluna;

import java.io.Serializable;


public class catering extends affitto implements Serializable{
	//variabili d'istanza
	protected int num_bambini;
	private int prezzo_tot;
	// variabile statica che indica il prezzo del catering per bambino
	private static final int prezzo_cat = 15;
	static final long serialVersionUID = 9198417829920138291L;
	
	//costruttore
	protected catering(String nome, String data, int num_bambini) {
		super (nome, data);
		this.num_bambini = num_bambini;
		this.prezzo_tot = getPrezzo_base() + (prezzo_cat * num_bambini);
		//se il numero dei bambini è maggiore di 40 si riceve uno sconto di 30 euro
		if (this.num_bambini >= 40) {
			this.prezzo_tot = prezzo_tot - 50;
		}
	}
	
	//metodi per ottenere le variabili 
	protected static int getPrezzo_cat() {
		return prezzo_cat;
	}
	
	protected int getPrezzo_tot() {
		return prezzo_tot;
	}
	
	protected int getNumero() {
		return num_bambini;
	}
	
	//permette di modificare il numero di bambini alla festa e di conseguenza il prezzo finale
	protected void setNumPrezzo(int nuovo) {
		if (nuovo!= num_bambini) {
			int prezzo = getPrezzo_base() + (prezzo_cat * nuovo);
			this.prezzo_tot= prezzo;
			this.num_bambini = nuovo;
		}
		if (nuovo > 40) {
			int prezzo = (getPrezzo_base() + (prezzo_cat * nuovo))-50;
			this.prezzo_tot= prezzo;
			this.num_bambini = nuovo;
		}	
	}
	
	//permette di visualizzare la prenotazione, sovrascrive il metodo di affitto
	protected void visualizza() {
		System.out.println("PRENOTAZIONE AFFITTO + CATERING");
		System.out.println("Nome:\t" + getNome());
		System.out.println("Data: \t" + data);
		System.out.println("Numero bambini: \t" + getNumero());
		System.out.println("Prezzo: \t" + prezzo_tot);	
	}
	

}
