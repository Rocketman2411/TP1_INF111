package utilitaires;

public class Vect2D 
{

	private double longueurX;
	private double longueurY;
	
	/*
	 * Constructeur par défaut (valeur X  et Y vaut 0)
	 */
	public Vect2D(){}
	
	/*
	 * Contructeur qui recoit un X et Y dans le programme main()
	 */
	public Vect2D(double x, double y) 
	{
		longueurX = x;
		longueurY = y;
	}
	
	/*
	 * Copie les valeurs d'un vecteur 2D qu'on lui donne
	 */
	public Vect2D(Vect2D v) 
	{
		longueurX = v.longueurX;
		longueurY = v.longueurY;
	}
	
	/*
	 * Sachant que X et Y sont des membre private, on met des getters pour lire UNIQUEMENT 
	 * On ne peut pas la modifier une fois déclarer
	 */
	public double getLongueurX() {return longueurX;}
	public double getLongueurY() {return longueurY;}
	
	/*
	 * Permet de lire la longueur entre X et Y
	 */
	public double getLongueur() 
	{
		return Math.pow((Math.pow(longueurX,2)+Math.pow(longueurY,2)), 0.5f);
	}
	
	/*
	 * Permet de lire l'angle entre X et Y (en RAD)
	 */
	public double getAngle() {return Math.atan(longueurX/longueurY);}
	
	/*
	 * Prend la valeur des X et Y des 2 vecteur et fait par la suite une soustraction. 
	 * Retourne un nouveau vecteur 2d ayant comme X et Y le résultat de la différence
	 */
	public Vect2D calculerDiff(Vect2D posFin)
	{		
		double differenceX = posFin.longueurX - longueurX;
		double differenceY = posFin.longueurY - longueurY; 
		
		return new Vect2D(differenceX, differenceY);
	}
	
	public void diviser(double a)
	{
		longueurX = longueurX/a;
		longueurY = longueurY/a;
	}
	
	public void ajouter(double x, double y)
	{
		longueurX = longueurX + x;
		longueurY = longueurY + y;
	}
	
	@Override
	public String toString() 
	{
		return "X: "+longueurX+"Y: "+longueurY+" avec une longueur de "+getLongueur();
	}

	@Override
	
	/*
	 * Comprare l'objet qu'on lui a envoyer par apport a notre vecteur 2D
	 * La comparaison se fait sur la base des valeurs de X et Y
	 */
	public boolean equals(Object o) 
	{
		if(this == o) 
		{
			return true;
		}
		if(o == null) 
		{
			return false;
		}
		if(o instanceof Vect2D) 
		{	
			Vect2D v = (Vect2D) o;
			return (this.longueurX == v.longueurX) && (this.longueurY == v.longueurY);
		}
		else 
		{
			return false;
		}
	}
}
