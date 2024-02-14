package utilitaires;

/*
 * Pour cette classe, vu que je ne savais pas pour quelles données nous avions a appeler la file,
 * j'ai crée un classe générique. Il suffit juste de préciser quelles données cette file prend.
 * Par exemple: FileSimplementChainer<Type> file = new FileSimplementChainer<Type>();
 */
public class FileSimplementChainer <T>
{
	class Noeud
	{
		public T valeur;
		public Noeud suivant;
		
		public Noeud(T v) 
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
	
	public void ajouterElement(T v) 
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
		T valeurDebut = tete.valeur;
		
		tete = tete.suivant;
		nbElement --;
	}
	
	public T elementDebut() 
	{
		return tete.valeur;
	}
	
	public boolean estVide() 
	{
		return tete == null;
	}
}

