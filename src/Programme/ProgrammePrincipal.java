package Programme;

import java.io.IOException;

import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Vect2D;
import utilitaires.FileSimplementChainer;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args)
	{
		FileSimplementChainer l = new FileSimplementChainer();
		l.ajouterElement(2);
		System.out.println(l.elementDebut());
		
		l.ajouterElement(8);
		System.out.println(l.elementDebut());
		
		l.enleverElement();
		System.out.println(l.elementDebut());
		
		//SatelliteRelai satellite = new SatelliteRelai();
		//satellite.start();
	}

}
