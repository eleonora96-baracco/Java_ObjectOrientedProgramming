package Soleluna;

import java.io.Serializable;


public class animazione extends catering implements Serializable{
	// variabili d'istanza
	protected char tipo;
	private int prezzo_tot;
	//variabili statiche. Prezzo per bambino 
	private static final int prezzo_giochi = 5;
	private static final int prezzo_burattini = 10;
	private static final int prezzo_magia = 15;
	static final long serialVersionUID = -3481561248535599519L;
	
	//costruttore
	protected animazione (String nome, String data, int num_bambini, char tipo) {
		super(nome, data, num_bambini);
		this.tipo = tipo;
		this.prezzo_tot = calcola(tipo, num_bambini);
		if (num_bambini > 40) {
			int prezzo = calcola(tipo,num_bambini) - 100;
			this.prezzo_tot= prezzo; 
		}
	}
	
	//metodo che calcola il prezzo totale di questo servizio
	private int calcola(char tipo, int num_bambini) {
		int prezzo=0;
		if (tipo == 'G') {
			prezzo = getPrezzo_base()+ (getPrezzo_cat()* num_bambini) + (prezzo_giochi * num_bambini);
		} else if (tipo == 'B') {
			prezzo = getPrezzo_base()+ (getPrezzo_cat()* num_bambini) + (prezzo_burattini * num_bambini);
		} else if (tipo == 'M') {
			prezzo = getPrezzo_base()+ (getPrezzo_cat()* num_bambini) + (prezzo_magia * num_bambini);
		}
		return prezzo;	
	}
	
	//metedo che permette di visualizzare il prezzo per bambino dei vari tipi di animazione
	protected static int getPrezzo_animazione(char t) {
		if (t=='G') {
			return prezzo_giochi;
		} else if (t=='B') {
			return prezzo_burattini;
		} else if (t=='M'){
			return prezzo_magia;
		} else {
			return -1;
		}
	}
	
	//permette di modificare il tipo di animazione
	protected void set_tipo (char t){
		if (t!=tipo) {
			this.tipo = t;
			this.prezzo_tot = calcola(t, num_bambini);
			if (num_bambini > 40) {
				int prezzo = calcola(t,num_bambini) - 100;
				this.prezzo_tot= prezzo; 
			}
		}
	}
	
	//permette di modificare il numero di bambini alla festa e di conseguenza il prezzo finale, sovrascrive quello di catering
	protected void setNumPrezzo(int nuovo) {
		if (nuovo!= getNumero()) {
			this.num_bambini = nuovo;
			int prezzo = calcola(tipo, nuovo);
			this.prezzo_tot= prezzo;
			this.num_bambini = nuovo;
		}
		if (nuovo > 40) {
			int prezzo = calcola(tipo,nuovo) - 100;
			this.prezzo_tot= prezzo; 
			this.num_bambini = nuovo;
		}	
	}
	
	//serve per stampare correttamente il tipo di animazione scelta
	private String descrivi_tipo(){
		if (tipo=='G') {
			return "Organizzazione di giochi per bambini.";
		} else if (tipo=='B') {
			return "Organizzazione di spettacolo di burattini ";
		} else if (tipo=='M'){
			return "Organizzazione di spettacolo di magia";
		} else {
			return "Qualcosa è andato storto!!!";
		}
	}
	
	//permette di visualizzare la prenotazione, sovrascrive il metodo di catering
	protected void visualizza() {
		System.out.println("PRENOTAZIONE AFFITTO + CATERING + ANIMAZIONE");
		System.out.println("Nome:\t" + getNome());
		System.out.println("Data: \t" + data);
		System.out.println("Numero bambini: \t" + getNumero());
		System.out.println("Tipo animazione: \t" + descrivi_tipo());
		System.out.println("Prezzo: \t" + prezzo_tot);	
	}
	

}
