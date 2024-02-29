package rover;

import modele.communication.Message;
import modele.communication.TransporteurMessage;
import modele.satelliteRelai.SatelliteRelai;
public class Rover extends TransporteurMessage
{
	private SatelliteRelai RefSatelliteR;
	
	public Rover(SatelliteRelai s) 
	{
		RefSatelliteR = s;
	}
	public void envoyerMessage(Message msg) 
	{
		RefSatelliteR.envoyerMessageVersCentrOp(msg);
		messageEnvoyer.add(msg);
	}
	
	public void gestionnaireMessage(Message msg) 
	{
		System.out.println("Nom: "+this.getClass().getSimpleName()+" Compteur:" +msg.getCompte());
	}
}
