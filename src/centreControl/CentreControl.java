package centreControl;

import modele.communication.Message;
import modele.communication.TransporteurMessage;
import modele.satelliteRelai.SatelliteRelai;
public class CentreControl extends TransporteurMessage
{
	private SatelliteRelai refSatelliteR;
	
	public CentreControl(SatelliteRelai s) 
	{
		refSatelliteR = s;
	}
	public void envoyerMessage(Message msg) 
	{
		refSatelliteR.envoyerMessageVersRover(msg);
		messageEnvoyer.add(msg);
	}
	
	public void gestionnaireMessage(Message msg) 
	{
		System.out.println("Nom: "+this.getClass().getSimpleName()+" Compteur:" +msg.getCompte());
	}
}
