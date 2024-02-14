package modele.satelliteRelai;

/**
 * Classe simulant le satellite relai
 * 
 * Le satellite ne se contente que de transferer les messages du Rover vers le centre de contrôle
 * et vice-versa.
 * 
 * Le satellite exécute des cycles à intervale de TEMPS_CYCLE_MS. Période à
 * laquelle tous les messages en attente sont transmis. Ceci est implémenté à
 * l'aide d'une tâche (Thread).
 * 
 * Le relai satellite simule également les interférence dans l'envoi des messages.
 * 
 * Services offerts:
 *  - lierCentrOp
 *  - lierRover
 *  - envoyerMessageVersCentrOp
 *  - envoyerMessageVersRover
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import utilitaires.FileSimplementChainer;

import modele.communication.Message;

public class SatelliteRelai extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.15;
	
	ReentrantLock lock = new ReentrantLock();

	private Random rand = new Random();
	
	private FileSimplementChainer<Message> msgRover = new FileSimplementChainer<Message>();
	private FileSimplementChainer<Message> msgCentreControle = new FileSimplementChainer<Message>();
	
	//private ArrayList<Message> msgRover = new ArrayList<Message>();
	//private ArrayList<Message> msgCentreControle = new ArrayList<Message>();
	/**
	 * Méthode permettant d'envoyer un message vers le centre d'opération
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersCentrOp(Message msg) 
	{	
		lock.lock();
		
		try 
		{
			double nbTirer = rand.nextDouble();
			if(nbTirer > PROBABILITE_PERTE_MESSAGE) 
			{
				msgCentreControle.add(msg);
			}
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Méthode permettant d'envoyer un message vers le rover
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersRover(Message msg) {
		lock.lock();
		
		try 
		{
			double nbTirer = rand.nextDouble();
			if(nbTirer > PROBABILITE_PERTE_MESSAGE) 
			{
				msgRover.add(msg);
			}	
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) 
		{
			if(msgCentreControle.size() > 0) 
			{
				msgCentreControle.remove(0);
			}
			else if(msgRover.size() > 0) {
				msgRover.remove(0);
			}
			
			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
