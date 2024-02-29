package modele.communication;
/**
 * Classe qui implémente le protocol de communication entre le Rover
 * et le Centre d'opération.
 * 
 * Il se base sur une interprétation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyés sont mémorisé. À l'aide du compte unique
 * le transporteur de message peut identifier les Messages manquant
 * dans la séquence et demander le renvoi d'un Message à l'aide du Nack.
 * 
 * Pour contourner la situation ou le Nack lui-même est perdu, le Nack
 * est renvoyé periodiquement, tant que le Message manquant n'a pas été reçu.
 * 
 * C'est également cette classe qui gère les comptes unique.
 * 
 * Les messages reçu sont mis en file pour être traité.
 * 
 * La gestion des messages reçu s'effectue comme une tâche s'exécutant indépendamment (Thread)
 * 
 * Quelques détails:
 *  - Le traitement du Nack a priorité sur tout autre message.
 *  - Un message NoOp est envoyé périodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de séquence.
 * 
 * Services offerts:
 *  - TransporteurMessage
 *  - receptionMessageDeSatellite
 *  - run
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2024
 */

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import modele.communication.Nack;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	// lock qui protège la liste de messages reçu
	private ReentrantLock lock = new ReentrantLock();
	
	public ArrayList<Message> messageRecu = new ArrayList <Message>();
	public ArrayList<Message> messageEnvoyer = new ArrayList <Message> ();
	

	/**
	 * Constructeur, initialise le compteur de messages unique
	 */
	public TransporteurMessage() {
		compteurMsg = new CompteurMessage();		
	}
	
	/**
	 * Méthode gérant les messages reçu du satellite. La gestion se limite
	 * à l'implémentation du Nack, les messages spécialisé sont envoyés
	 * aux classes dérivés
	 * @param msg, message reçu
	 */
	public void receptionMessageDeSatellite(Message msg) 
	{
		int compteurNack = 0;
		lock.lock();
		
		try 
		{
			//Si la liste est vide ou que le message recu est un Nack, il est placé
			//directement au début
			//-Hichem
			if(equals(msg) || messageRecu == null) 
			{
				messageRecu.add(0,msg);
			}
			else
			{
				//Si la liste n'est pas vide, on par à la recherche du nombre de Nack 
				//pour voir par la suite ou il faut commencer à trier
				//Hichem
				int i = 0;
				while(i < messageRecu.size()) 
				{
					if(equals(messageRecu.get(i))) 
					{
						++compteurNack;
						++i;
					}
				}
				//Méthode permettant de trier à partir d'un message nonNack
				//-Hichem
				trier(compteurNack, msg);
			}
		}
		finally 
		{
			lock.unlock();
		}
	}
	
	public void trier (int comp, Message msg) 

	{
		//Si la liste n'as que des Nack on ajoute le message à la fin
		//-Hichem
		if(messageRecu.size() == comp)
		{
			messageRecu.add(msg);
		}
		else 
		{
			//Si la liste ne contient que des message Nack et 1 nonNack
			//-Hichem
			if(comp == messageRecu.size() -1) 
			{
				//Si le message à ajouter est < au message déjà présent, il prend sa place
				//-Hichem
				if(msg.getCompte() < messageRecu.get(comp).getCompte()) 
				{
					messageRecu.add(comp,msg);
				}
				//Sinon il l'ajoute à la fin
				//-Hichem
				else 
				{
					messageRecu.add(msg);
				}
			}
			else
			{
				//Sinon on commence à balayer à partir de la ou il y a des message nonNack pour 
				//mettre notre message à la bonne position
				//-Hichem
				
				int i = comp;
				while(i < messageRecu.size()) 
				{
					if(msg.getCompte() < messageRecu.get(i).getCompte()) 
					{
						messageRecu.add(i, msg);
						++i;
					}
					++i;
				}
			}
		}
	}
	
	//Permet de comparer Si le message envoyer est en fait un Nack. Si c'est le cas, envoie True
	//-Hichem
	@Override
	public boolean equals(Object obj) 
	{
		boolean etat;
		if(obj instanceof Nack) 
		{
			etat = true;
		}
		else 
		{
			etat = false;
		}
		return etat;
	}

	@Override
	/**
	 * Tâche effectuant la gestion des messages reçu
	 */
	public void run() 
	{	
		int compteCourant = 0;
		int indiceListe = 0;
		boolean nackEnvoyer = true;
		while(true) 
		{
			
			lock.lock();
			
			try 
			{
				Message typeNack = messageRecu.get(0);
				
				if(equals(typeNack))
				{
					nackEnvoyer = true;
					int i = 0;
					ArrayList<Message> temp = new ArrayList<Message>();
					
					//Chercher dans la liste des messages envoyer (selon le compte)
					//et vas garder dans une liste temporaire tout les éléments >= compteur nack
					//-Hichem
					while(i < messageEnvoyer.size()) 
					{
						if(typeNack.getCompte() <= messageEnvoyer.get(i).getCompte()) 
						{
							temp.add(messageEnvoyer.get(i));
							++i;
						}
						++i;
					}
					
					//Ensuite il vas peek au debut de cette liste temporaire et renvoie le message
					//-Hichem
					envoyerMessage(temp.get(0));
					
					//enlever le nack de la liste
					//-Hichem
					messageRecu.remove(0);
					
					compteCourant = 0;
					indiceListe = 0;
				}

				while(!messageRecu.isEmpty() && nackEnvoyer && indiceListe < messageRecu.size()) 
				{
					Message msgATraiter = messageRecu.get(indiceListe);

					//Détecte si dans la liste des message reçus s'il y a des message manquant
					if(msgATraiter.getCompte() != compteCourant) 
					{

							envoyerMessage(new Nack(compteCourant));
							nackEnvoyer = false;
					}
					else 
					{
						//Si le message à traiter est en fait un double
						//-Hichem
						if(msgATraiter.getCompte() == messageRecu.get(compteCourant+1).getCompte() && messageRecu.get(compteCourant+1) != null ) 
						{
							messageRecu.remove(compteCourant+1);
						}
						else 
						{
							//Enfin, si c'est un message il est envoyer au gestionnaire 
							//-Hichem
							System.out.println("Message gérer");
							gestionnaireMessage(msgATraiter);
							++indiceListe;
							++compteCourant;
						}	
					}
					compteurMsg.getCompteActuel();
					//Par sûr de cette partie 
					//-Hichem
					envoyerMessage(new NoOp(compteCourant));
				}
			}
			finally
			{
				lock.unlock();
			}
			
			// pause, cycle de traitement de messages
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * méthode abstraite utilisé pour envoyer un message
	 * @param msg, le message à envoyer
	 */
	abstract protected void envoyerMessage(Message msg);

	/**
	 * méthode abstraite utilisé pour effectuer le traitement d'un message
	 * @param msg, le message à traiter
	 */
	abstract protected void gestionnaireMessage(Message msg);

	

}
