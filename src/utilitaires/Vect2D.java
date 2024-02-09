package utilitaires;

public class Vect2D 
{

	private float longueurX;
	private float longueurY;
	
	public Vect2D(){}
	
	public Vect2D(float x, float y) 
	{
		longueurX = x;
		longueurY = y;
	}
	
	public Vect2D(Vect2D v) 
	{
		longueurX = v.longueurX;
		longueurY = v.longueurY;
	}
	
	public float getLongueurX() {return longueurX;}
	public float getLongueurY() {return longueurY;}
	public float getLongueur() 
	{
		return (float)Math.pow((Math.pow(longueurX,2)+Math.pow(longueurY,2)), 0.5f);
	}
}
