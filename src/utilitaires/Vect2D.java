package utilitaires;

public class Vect2D 
{

	private double longueurX;
	private double longueurY;
	
	public Vect2D(){}
	
	public Vect2D(double x, double y) 
	{
		longueurX = x;
		longueurY = y;
	}
	
	public Vect2D(Vect2D v) 
	{
		longueurX = v.longueurX;
		longueurY = v.longueurY;
	}
	
	public double getLongueurX() {return longueurX;}
	public double getLongueurY() {return longueurY;}
	public double getLongueur() 
	{
		return Math.pow((Math.pow(longueurX,2)+Math.pow(longueurY,2)), 0.5f);
	}
	public double getAngle() {return Math.atan(longueurX/longueurY);}
	
	/*
	 * Prend la valeur absolu des X et Y des 2 vecteur et fait par la suite une soustraction. 
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
