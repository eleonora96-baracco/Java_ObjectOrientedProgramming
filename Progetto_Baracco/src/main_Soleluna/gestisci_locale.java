package main_Soleluna;

import Soleluna.eccezioni;
import Soleluna.gestisci_prenotazioni;
import java.util.Scanner;
import java.text.ParseException;

public class gestisci_locale {
	
	

	public static void main(String[] args) throws ParseException, eccezioni {
		// TODO Auto-generated method stub
		
		Scanner input = new Scanner(System.in);
		//si crea l'oggetto che gestisce le prenotazioni
		gestisci_prenotazioni p = new gestisci_prenotazioni();
		char scelta;
		do {
			System.out.println("Buongiorno!");
			System.out.println("Scegli il tipo di operazione:");
			System.out.println();
			System.out.println("[A]ggiungi una prenotazione");
			System.out.println("[E]limina una prenotazione");
			System.out.println("[M]odifica una prenotazione");
			System.out.println("[V]isualizza una prenotazione");
			System.out.println("[P]assa al menù INFORMAZIONI");
			System.out.println("[S]alva e chiudi");
			System.out.println();
			System.out.println("Scelta:");
			scelta = input.next().charAt(0);
			input.nextLine();
			
			switch(scelta) {
			case 'A':
				if (p.aggiungi()) {
					System.out.println("Prenotazione aggiunta!");
				} else {
					System.out.println("La data inserita non è più disponibile");
				}
				break;
			
			case 'E':
				p.elimina();
				break;
			
			case 'M':
				p.modifica();
				break;
			
			case 'V':
				p.visualizza_prenotazione();
				break;
				
			case 'P':
				char scelta2;
				do {
					System.out.println("Scegli il tipo di operazione:");
					System.out.println();
					System.out.println("[T]rova la prima data disponibile");
					System.out.println("[V]isualizza prezzario");
					System.out.println("[S]tampa le liste di prenotazione");
					System.out.println("[U]scita");
					System.out.println();
					System.out.println("Scelta:");
					scelta2 = input.next().charAt(0);
					input.nextLine();
					
					switch (scelta2) {
					
					case 'T':
						System.out.println(p.trova_data());
						break;
					
					case 'V':
						// accesso ad un metodo statico
						gestisci_prenotazioni.visualizza_prezzi();
						break;
					
					case 'S':
						p.stampa_pren();
						break;
						
					}
					System.out.println(); // riga vuota tra una operazione e l'altra
					
				} while (scelta2 != 'U'); 
				break;
				
			case 'S':
				System.out.println("Salvo e chiudo");
				p.serializza();
				break;
			
			default : System.out.println("Input errato!");
			}
			System.out.println(); // riga vuota tra una operazione e l'altra
			
		} while(scelta != 'S');
		
		input.close();	

	}
	
	
	

}
