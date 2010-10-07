package java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class Polinomio {
 
	private Vector<Termino> terminos;
	
	public Polinomio() {
		terminos = new Vector<Termino>();
	}
	 
	public void makePolinomioFromFile(String fileName) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(fileName));
			String str;
			while ((str = bf.readLine())!=null) {
				String[] terminos = str.split('\t');
				for(int i = 0; i < terminos.length; i++) {
					String[] ve = terminos[i].split('^');
					Termino termino = new Termino();
					termino.setValor(Integer.parseInt(ve[0]));
					termino.setExponente(Integer.parseInt(ve[1]));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
 
