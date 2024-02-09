package Programme;

import java.io.IOException;

import modele.satelliteRelai.SatelliteRelai;
import utilitaires.Vect2D;

public class ProgrammePrincipal {

	/**
	 * Programme principale, instancie les éléments de la simulation,
	 * les lie entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args){
	
		Vect2D v = new Vect2D(5,4);
		System.out.println("Longueur vecteur: "+v.getLongueur());
		//SatelliteRelai satellite = new SatelliteRelai();
		//satellite.start();
		
	}

}
