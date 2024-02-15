package utilitaires;

/*
 * Pour cette classe, vu que je ne savais pas pour quelles données nous avions a appeler la file,
 * j'ai crée un classe générique. Il suffit juste de préciser quelles données cette file prend.
 * Par exemple: FileSimplementChainer<Type> file = new FileSimplementChainer<Type>();
 */
public class FileSimplementChainer <T>
{
	/*
	 * C'est à l'intérieur de cette objet que la donnée sera mise. 
	 * Contient la valeur en temps que tel et un objet (Noeud) qui vas pointer la prochaine donnée
	 */
	class Noeud
	{
		public T valeur;
		public Noeud suivant;
		
		/*
		 * L'objet est null car il sert juste à pointer 
		 */
		public Noeud(T v) 
		{
			valeur = v;
			suivant = null;
		}
	}
	
	public Noeud tete;
	public int nbElement;
	
	/*
	 * C'est comme notre première donnée de la file mais reste toujours la
	 */
	public FileSimplementChainer() 
	{
		tete = null;
		nbElement = 0;
	}
	
	/*
	 * Notre noeud tête vas pointer maintenant vers le prochaine noeud qu'on ajoute
	 * Si la file n'est pas vide, le nouvelle noeud(.suivant) vas pointer vers le noeud de la tête
	 * Sant que la tête pointe déjà vers un noeud, le nouveau noeud vas pointer vers l'avant 
	 * dernier élément.
	 * Une fois se lien établie, la tête peut maintenant pointer vers les nouveau noeud
	 */
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
	
	/*
	 * La tête vas simplement pointer vers l'avant dernière valeur et ignore la première valeur
	 */
	public void enleverElement() 
	{
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

