package java;

import java.util.Iterator;
import java.util.Vector;

public class Polinomio {
 
	private Vector<Termino> terminos;
	
	public Polinomio() {
		terminos = new Vector<Termino>();
	}
	 
	public void makePolinomioFromFile() {
	 
	}
	 
	public void verPolinomio() {
	 
	}
	 
	public void addTermPolinomio(int v, int e) 
	{
		Termino termino = new Termino();
		termino.setValor(v);
		termino.setExponente(e);
		int index = indexOf(e);
		if (index == -1)
			terminos.addElement(termino);
		else
			terminos.setElementAt(termino, index);
	}
	 
	public Polinomio ordenarDec(Polinomio p) {
		return null;
	}
	 
	public Polinomio completar(Polinomio p) {
		return null;
	}
	 
	public boolean estaOrdenado(Polinomio p) {
		return true;
	}
	 
	public int grado(Polinomio p) {
		if (estaOrdenado(p)) {
			return p.gerTerminos().firstElement().getExponente();
		}
						
		return 0;
	}
	
	private int indexOf(int exponente) {
		for (int i = 0; i < terminos.size(); i++) {
			Termino termino = terminos.elementAt(i);
			if(termino.getExponente() == exponente)
				return i;
		}
		return -1;
	}
	
	public Vector<Termino> gerTerminos()  {
		return terminos;
	}
}
 
