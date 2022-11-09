package Soleluna;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
// importo l'eccezione che può dare il metodo parse() della classe DateFormat
import java.text.ParseException;


public class gestisci_prenotazioni {
	
	//lista in cui mettere le prenotazioni
	LinkedList<affitto> pren = new LinkedList<affitto>();
	//nome del file in cui inserire la lista delle prenotazioni
	String filename = "prenotazioni_soleluna.dat";
	//Scanner per ricevere da tastiera
	Scanner input = new Scanner(System.in);
	//oggetto DateFormat specifico per l'Italia
	DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
	
	
	//costruttore della classe che gestisce le prenotazioni, invoca il metodo deserializza() al fine di ottenere le prenotazioni salvate
	public gestisci_prenotazioni() {
		deserializza();
	}
	
	//metodi per verificare la disponibilità di una data e trovare la prima disponibile
	private boolean verifica_data(String data) throws ParseException{
		//creo una prenotazione(che poi rimuovo) al fine di poter usufruire dell'iteratore anche la prima volta che si usa il progamma, o se non si avessero prenotazioni.
		pren.addFirst(new affitto("Eleonora", "26/08/2021"));
		//iteratore per lista prenotazioni, lo creo all'interno del metodo affinchè venga ricreato ogni volta che si chiama il metodo
		ListIterator <affitto> it = pren.listIterator();
		//istanza della classe calendar che cattura la data odierna
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		//creo un oggetto Date con la data di oggi
		Date oggi = calendar.getTime();
		// trasformo la stringa inserita in un oggetto Date
		Date inserita = df.parse(data);
		//variabile che definisce la correttezza della data 
		boolean ok;
		//itero nella lista delle prenotazioni
		do {
			//il metodo before() dà true solo se è strettamente antecedente quindi anche la data di oggi non va bene (non voglio che si possa prenortare per il giorno stesso)
			if (!inserita.before(oggi)) {
				affitto a = it.next();
				//ottengo la data della prenotazione con cui devo confrontare la data inserita
				String d = a.data;
				//la trasformo in oggetto Date
				Date da = df.parse(d);
				//chiamo il meteodo del pacchetto util compareTo() per confrontare le date.
				int v1 = inserita.compareTo(da);
				//se la data è diversa da quella della prenotazione si continua il ciclo
				if (v1 != 0) {
					//la variabile va a true perché il confronto con questa data è andato bene
					ok = true;
				} else {
					//se la data coincide con quella della prenotazione si assegna alla  varibile false(si esce dal ciclo)
					ok = false;
				}
			}else {
				//se la data è antecedente o uguale a quella odierna, il metodo può direttamente ritonare false 
				return false;
			}
		} while (it. hasNext () && ok);
		//quando esce dal ciclo controlla la variabile ok che indica sa la data va bene
		if (ok) {
			return true;
		}else {
			return false;
		}
	}
	
	public String trova_data() throws ParseException{
		//istanza della classe calendar che cattura la data odierna
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		Date tom;
		String domani;
		// il ciclo parte dalla data di domani e s'interrompe quando trova la prima data disponibile
		do{
			//utilizzo calendar per traformare la data in quella di domani
			calendar.add(Calendar.DATE, 1);
			// creo l'oggetto Date
			tom = calendar.getTime();
			//lo rendo una stringa
			domani = df.format(tom);
			
		} while(!verifica_data(domani));
		//rimuovo la prenotazione di default creata da verifica_data() 
		pren.remove();
		return domani;
	}
	
	//meteodo per aggiungere una prenotazione 
	public boolean aggiungi() throws eccezioni{
		// chiamo il metodo che setta il mio formattatore di date in modo che non accetti date non reali
		df.setLenient(false);
		//variabile che indica la correttezza dell'input
		boolean verifica ;
		//ciclo che termina solo se l'input dato è corretto
		do {
			//prova a fare il parse della data, se c'è una ParseException il programma si sospende e si chiede un nuovo input.
			try {
				System.out.println("Inserire la data della prenotazione (dd/mm/aaaa)");
				String data = input.nextLine();
				Date inserita = df.parse(data); 
				//se non incorre in nessun errore la variabile va a true
				verifica = true;
				//si controlla che la data sia disponibile
				if (verifica_data(data)) {
					System.out.println("Inserire un nominativo");
					String nome = input.nextLine();
					//variabile che indica la correttezza dell'input
					boolean ok;
					//variabile che indica se si è aggiunta la prenotazione
					boolean fatto = false;
					//ciclo che termina solo se l'input è corretto
					do {
						System.out.println("Scegli il tipo di prenotazione:\n [A]affitto,\n [B]catering,\n [C]animazione");
						char scelta = input.next().charAt(0);
						input.nextLine();
						//si controlla l'input
						if (scelta =='A'|| scelta =='B'|| scelta == 'C') {
							ok = true;
							ListIterator <affitto> it = pren.listIterator();
							//si itera nelle prenotazioni per capire dove inserire la nuova prenotazione
							do {
								affitto a = it.next();
								String d = a.data;
								Date da = df.parse(d);
								int v = inserita.compareTo(da);
								//se è antecedente si sposta il cursore e s'inserisce prima della prenotazione con cui si sta facendo il confronto
								if (v<0) {
									it.previous();
									it.add(crea(nome,data,scelta));
									//quando si aggiunge un prenotazione si esce dal ciclo
									fatto = true;
								//se non ci sono ulteriori prenotazioni e la data non è antecedente s'inserisce dopo la prenotazione con cui si sta facendo il confronto. Altrimenti si continua il ciclo!
								} else if(!it.hasNext()) {
									it.add(crea(nome,data,scelta));
									fatto = true;
								}
							}while (it.hasNext () && !fatto);
						}
						// se non sono stati inseriti i caratteri corretti si rientra nel ciclo e si chiede di nuovo il tipo di prenotazione
						else {
							System.out.println("Input errato, riprova");
							ok = false;
						}
					} while(!ok);
					//rimuovo la prenotazione di default creata da verifica_data() 
					pren.remove();
					if (fatto) {
						return true;
					} else {
						return false;
					}
				//se la data non è disponibile il metodo ritorna false 	
				} else {
					//rimuovo la prenotazione di default creata da verifica_data() 
					pren.remove();
					return false;
				}
			//se l'input data non è corretto si rientra nel ciclo e si chiede di nuovo la data	
			} catch(ParseException e) {
				verifica = false;
				System.out.println("Input errato. Riprova (dd/mm/yyyy)");
			}
		}while(!verifica);
		//teoricamente si rimane nel ciclo fino a che non s'inserisce una data corretta, quindi i veri return solo quelli dentro al try. questo if serve per tranquillizzre il compilatore
		if (!verifica) {
			return false;
		}else {
			return true;
		}
	}
	
	//questo metodo può essere utilizzato solo da questa classe, crea l'oggetto affitto da aggiungere nella lista prenotazioni
	private affitto crea(String nome, String data, char scelta) throws eccezioni {
		if (scelta == 'A') {
			return new affitto(nome, data);
		} else if (scelta == 'B') {
			int n= 0;
			boolean ok;
			do {
				try {
					System.out.println("Inserisci il numero di bambini:");
					n = input.nextInt();
					input.nextLine();
					ok = true;
				}catch (InputMismatchException e) {
					input.nextLine();
					System.out.println("Hai sbagliato, rirpova.");
					ok = false;
				}
			} while(!ok);
			return new catering(nome, data, n);
		} else {
			int num = 0;
			boolean ok2;
			do {
				try {
					System.out.println("Inserisci il numero di bambini:");
					num = input.nextInt();
					input.nextLine();
					ok2 = true;
				}catch (InputMismatchException e) {
					input.nextLine();
					System.out.println("Hai sbagliato, rirpova.");
					ok2 = false;
				}
			} while(!ok2);
			boolean ok;
			char tipo;
			do {
				System.out.println("Inserisci il tipo di animazione:\n [G]organizzazione giochi, \n [B]spettacolo di burattini, \n [M]spettacolo di magia");
				tipo = input.next().charAt(0);
				input.nextLine();
				if (tipo == 'G'|| tipo == 'B'||tipo == 'M') {
					ok= true;
				} else {
					ok = false;
					System.out.println("Hai sbagliato, rirpova.");
				}
			} while(!ok);
			//avendo gestisto i possibili errori dell'input prima, non si dovrebbe mai entrare nell'else, che fa terminare il programma. 
			if (ok) {
				return new animazione(nome, data, num, tipo);
			 } else {
				throw new eccezioni ("Hai sbagliato l'input!");
			 }
		}
	}
	
// metodo che permette di visualizzare una prenotaione in base al nominativo, se con uno stesso nominativo ci sono più prenotazioni si visualizzano tutte.
	public void visualizza_prenotazione(){
		System.out.println("Inserire un nominativo");
		String nome = input.nextLine();
		ListIterator <affitto> it = pren.listIterator();
		//variabile che indica se la prenotazione esiste
		boolean trovata = false;
		do {
			//se la lista è vuota it.next() dà un errore, quindi l'ho usato per segnalare che non ci sono prenotazioni
			try {
				affitto a = it.next();
				String n = a.getNome();
				if (n.equals(nome)) {
					//chiamo il metodo visualizza() che è sovrascritto nelle estensioni di affitto, quindi per ogni tipo di prenotazione viene chiamto il rispettivo metodo di visualizzazione 
					a.visualizza();
					trovata = true;	
				}
			} catch (NoSuchElementException e) {
				System.out.println("Non abbiamo prenotazioni al momento");
			}
		} while (it. hasNext ());
		if (!trovata) {
			System.out.println("Prenotazione non trovata");
		}
	}
	
	//metodo che permette di eliminare una prenotazione, strutturato in una maniera simile al precedente
	public void elimina() {
		System.out.println("Inserire un nominativo");
		String nome = input.nextLine();
		ListIterator <affitto> it = pren.listIterator();
		boolean ok = false;
		do {
			try {
				affitto a = it.next();
				String n = a.getNome();
				if (n.equals(nome)) {
					System.out.println("Vuoi eliminare questa prenotazione?\n [S/N]");
					a.visualizza();
					char sì = input.next().charAt(0);
					input.nextLine();
					if (sì=='S') {
						//elimino la prenotazione ed esco dal ciclo
						it.remove();
						System.out.println("Operazione riuscita");
						ok = true;
					}else {
						//continuo il ciclo cercando altre prenotazioni con lo stesso nominativo
						ok = false;
					}
				}
			} catch(NoSuchElementException e) {
				System.out.println("Non abbiamo prenotazioni al momento");
			}
		} while (it. hasNext () && !ok);
		//se si esce dal ciclo e ok è rimasto false non si è trovata la prenotazione o si è scelto di continuare a cercare ma non ci sono ulteriori prenotazioni con quel nominativo
		if (!ok) {
			System.out.println("Prenotazione non trovata!");
		}
	}
	
	//metodo che stampa la lista delle prenotazioni
	public void stampa_pren() {
		ListIterator <affitto> it = pren.listIterator();
		System.out.println("Inserire il tipo di prenotazione:\n [A]affitto,\n [B]catering,\n [C]animazione");
		char scelta = input.next().charAt(0);
		input.nextLine();
		try {
			if (scelta=='A') {
				do {
					affitto a = it.next();
					a.visualizza();
				}while (it. hasNext ());
			} else if(scelta == 'B') {
				do {
					affitto a = it.next();
					if (a instanceof catering) {
						a.visualizza();
					}
				}while (it. hasNext ());
			} else if (scelta == 'C') {
				do {
					affitto a = it.next();
					if (a instanceof animazione) {
						a.visualizza();
					}
				}while (it. hasNext ());
			} else {
				System.out.println("Input errato");
			}
		} catch(NoSuchElementException e) {
			System.out.println("Non abbiamo prenotazioni al momento");
		}
	}
	
	//metodo che permette di modificare una prenotazione
	public void modifica() throws ParseException, eccezioni {
		System.out.println("Inserire un nominativo");
		String nome = input.nextLine();
		ListIterator <affitto> it = pren.listIterator();
		boolean ok = false;
		do{
			try {
				affitto a = it.next();
				String n = a.getNome();
				if (n.equals(nome)) {
					System.out.println("Vuoi modificare questa prenotazione?\n [S/N]");
					a.visualizza();
					char sì = input.next().charAt(0);
					input.nextLine();
					if (sì=='S') {
						System.out.println("Inserire il tipo di modifica:\n [A]cambia tutta la prenotazione(unico modo per cambiare la data),\n [B]nome,\n [C]altro");
						char scelta = input.next().charAt(0);
						input.nextLine();
						if (scelta =='A') {
							//per cambiare la data di una prenotazione bisogna eliminare la prenotazione e crearla da zero
							it.remove();
							aggiungi();
						}else if (scelta =='B') {
							System.out.println("Inserire il nuovo nome");
							String nuovo_n = input.nextLine();
							a.setNome(nuovo_n);
						} else if (scelta=='C') {
							//se la prenotazione è di tipo animazione chiedo che modifica si vuole apportare
							if (a instanceof animazione) {
								System.out.println("Inserire tipo di modifica:\n [n]umero bambini,\n [t]ipo di animazione");
								char s = input.next().charAt(0);
								input.nextLine();
								if (s=='n') {
									System.out.println("Inserire il numero");
									int	num = input.nextInt();
									input.nextLine();
									((animazione) a).setNumPrezzo(num);
									System.out.println("Operazione riuscita");
								} else if (s=='t') {
									System.out.println("Inserisci il tipo di animazione:\n [G]organizzazione giochi, \n [B]spettacolo di burattini, \n [M]spettacolo di magia");
									char tipo = input.next().charAt(0);
									input.nextLine();
									((animazione) a).set_tipo(tipo);
									System.out.println("Operazione riuscita");
								}
							//se la prenotazione e di tipo catering si chiede il nuovo numero di bambini 	
							} else if(a instanceof catering) {
								System.out.println("Inserire il numero");
								int	num = input.nextInt();
								input.nextLine();
								((catering) a).setNumPrezzo(num);
								System.out.println("Operazione riuscita");
							} else {
								 System.out.println("Qualcosa è andato storto!");
							}
						}else {
							System.out.println("Input errato!");
						}
						//se sì è entrati in questo ramo if vuol dire che si è scelta la prenotazione da modificare e si è svolto il metodo quindi si esce dal ciclo.
						ok = true;
					} else {
						//altrimenti si è scelto di continuare a cercare altre prenotazioni con quel determinato nominativo.
						ok = false;
					}
				}
			} catch (NoSuchElementException e) {
				System.out.println("Non ci sono prenotazioni!");
			}
		}while (it. hasNext () && !ok);
		if (!ok) {
			System.out.println("Prenotazione non trovata!");
		}
		
	}
	//meteodo per visualizzare il prezzario, accesso a metodi statici
	public static void visualizza_prezzi() {
		System.out.println("Il prezzo per il solo affitto del locale è \t" + affitto.getPrezzo_base());
		System.out.println("Il prezzo del catering per bambino è \t" + catering.getPrezzo_cat());
		System.out.println("CATERING : da 40 bambini in su c'è uno sconto di 50 euro sul prezzo totale");
		System.out.println("Il prezzo dell' organizzazione giochi per bambino è \t" + animazione.getPrezzo_animazione('G'));
		System.out.println("Il prezzo dello spettacolo di burattini per bambino è \t" + animazione.getPrezzo_animazione('B'));
		System.out.println("Il prezzo dello spettacolo di magia per bambino è \t" + animazione.getPrezzo_animazione('M'));
		System.out.println("ANIMAZIONE : da 40 bambini in su c'è uno sconto di 100 euro sul prezzo totale \t");
	}
	
	//metodo che serializza la lista delle prenotazioni
	public void serializza() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(pren);
			out.close();
			//quando viene chiamato serializza() si sta salvando e uscendo, quindi si può chiudere lo scanner.
			input.close();
		} catch (IOException e) {
			System.out.println("Errore \n" + e);
		}
	}
	
	//metodo che deserializza il file creando la lista delle prenotazioni ed assegnandola alla variabile d'istanza della classe. Questo metodo viene chiamato dal costruttore.
	private void deserializza() {
		try {
			FileInputStream in = new FileInputStream("prenotazioni_soleluna.dat");
			ObjectInputStream r = new ObjectInputStream(in);
			try {
				@SuppressWarnings({"unchecked" })
				final LinkedList<affitto> pren = (LinkedList<affitto>) r.readObject();
				this.pren = pren;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			r.close();
		} catch(IOException e) {
			// non stampo anche l'errore al fine di non creare disagi all'utente
			System.out.print("\n");
		}
	}
	
}


