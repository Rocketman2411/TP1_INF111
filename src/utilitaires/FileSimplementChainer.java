package utilitaires;

public class FileSimplementChainer 
{
	class Noeud
	{
		public double valeur;
		public Noeud suivant;
		
		public Noeud(double v) 
		{
			valeur = v;
			suivant = null;
		}
	}
	
	public Noeud tete;
	public int nbElement;
	
	public FileSimplementChainer() 
	{
		tete = null;
		nbElement = 0;
	}
	
	public void ajouterElement(double v) 
	{
		Noeud n = new Noeud(v);
		
		if(!estVide())
		{
			n.suivant = tete;
		}
		tete = n;
		nbElement ++;
	}
	
	public void enleverElement() 
	{
		double valeurDebut = tete.valeur;
		
		tete = tete.suivant;
		nbElement --;
	}
	
	public double elementDebut() 
	{
		return tete.valeur;
	}
	
	public boolean estVide() 
	{
		return tete == null;
	}
	
}

