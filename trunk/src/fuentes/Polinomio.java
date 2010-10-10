package fuentes;

import java.io.*;
import java.util.*;


public class Polinomio {
 
	private Vector<Termino> terminos;
	
	public Polinomio() {
		terminos = new Vector<Termino>();
	}

	public void makePolinomioFromFile(String fileName) {
		try {
            FileReader archivo = new FileReader(fileName);
            BufferedReader l = new BufferedReader(archivo);
            String lineaDelArchivo = l.readLine();
			while ( lineaDelArchivo !=null) {
				String[] datos = lineaDelArchivo.split(String.valueOf('\t')); 
				for(int i = 0; i < datos.length; i++) {
					String valorexponete = new String(datos[i]);

					int valor = Integer.parseInt(valorexponete.substring(0, valorexponete.indexOf("^")));
					int exponete = Integer.parseInt(valorexponete.substring(valorexponete.indexOf("^")+1, valorexponete.length()));
					
					addTermPolinomio(valor,exponete);
				}
				lineaDelArchivo = l.readLine();
			}	
            
        }
        catch(java.io.FileNotFoundException fnFound){
        	fnFound.printStackTrace();
        }
        catch(java.io.IOException ioex){
        	ioex.printStackTrace();
        }
    }

	public void verPolinomio() {
		String res = "";
		int i=0;
		while (i<terminos.size()){
			if (i==0){ // primer valor si es positivo no muestro el signo
				res += terminos.elementAt(i).getValor();
			}else{
				if (terminos.elementAt(i).getValor()>1){ // si el valor es mayor a 1 muestro +valor^exponente
					if (terminos.elementAt(i).getExponente()==1){ //+valorX^1 es igual que +valorX
						res += "+"+terminos.elementAt(i).getValor()+"X";
					}else{
						res += "+"+terminos.elementAt(i); // no era un 1
					}
				}else{
					if (terminos.elementAt(i).getValor()<-1){ // si el valor es menor a -1 muestro -valor^exponete 
						if (terminos.elementAt(i).getExponente()==1){// -valorX^1 es igual que -valorX
							res += terminos.elementAt(i).getValor()+"X";
						}else{
							res += terminos.elementAt(i);
						}
					}else{// el valor entre [-1,1]
						if(terminos.elementAt(i).getValor()==1){// +1X^exponete es igual que +X^exponente
							if (terminos.elementAt(i).getExponente()==1){
								res += "+X";
							}else{
								res += "+X^"+terminos.elementAt(i).getExponente();
							}
						}else{
							if(terminos.elementAt(i).getValor()==-1){ // -1X^exponente es igual que -X^exponente
								if (terminos.elementAt(i).getExponente()==1){
									res += "-X";
								}else{
									res += "-X^"+terminos.elementAt(i).getExponente();
								}
							}else{ // sacar este else para no mostrar grados con 0 ej 0X^5
								res+="+0X^"+terminos.elementAt(i).getExponente();
							}
						}	
					}
				}
			}
			i++;
		}
		System.out.println(res);
	}

	public void addTermPolinomio(int v, int e){
		Termino termino = new Termino();
		termino.setValor(v);
		termino.setExponente(e);
		int index = indexOf(e);
		if (index == -1)
			terminos.addElement(termino);
		else
			terminos.setElementAt(termino,index); //OJO QUE CUANDO EN EL ARCHIVO SE REPITEN DOS VALORES CON
	}											  // EL MISMO EXPONETES PISA EL QUE YA HABIA ANTES CON EL ULTIMO

	public Polinomio ordenarDec(Polinomio p) {
		Vector<Termino> pol = p.gerTerminos();
		Vector<Termino> ord = new Vector<Termino>();
		while (!pol.isEmpty()){
			int i = 0;
			Termino min = pol.elementAt(i);
			while  (i<pol.size()-1){
				if (min.getExponente()>pol.elementAt(i+1).getExponente()){
					min = pol.elementAt(i+1);
				}
				i++;
			}
			ord.addElement(min);
			pol.remove(min);
		}
		p.setTerminos(ord);
		return p;
	}

	public Polinomio completar(Polinomio p) {
		Polinomio polinomio = p;
		if (!estaOrdenado(polinomio)){
			ordenarDec(polinomio);
		}
		Vector<Termino> pol = polinomio.gerTerminos();
		int i = 0;
		int exponente1;
		int exponente2;
		while (i < pol.size()-1){
			exponente1 = pol.elementAt(i).getExponente();
			exponente2 = pol.elementAt(i+1).getExponente();
			while (exponente1+1!=exponente2){
				Termino ter = new Termino();
				ter.setValor(0);
				exponente1++;
				ter.setExponente(exponente1);
				pol.insertElementAt(ter,exponente1);
			}
			i++;
		}
		polinomio.setTerminos(pol);
		return polinomio;
	}

	public boolean estaOrdenado(Polinomio p) {
		boolean ordenado = true;
		int i = 0;
		int termino1;
		int termino2;
		Vector<Termino> pol = p.gerTerminos();
		while ((i < pol.size()-1) && ordenado){
			termino1 = pol.elementAt(i).getExponente();
			termino2 = pol.elementAt(i+1).getExponente();
			ordenado = ordenado && (termino1<=termino2);
			i++;
		}
		return ordenado;
	}

	public int grado(Polinomio p) {
		Polinomio polinomio = p;
		if (!estaOrdenado(polinomio)){
			ordenarDec(polinomio);
		}
		return polinomio.gerTerminos().lastElement().getExponente();
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

	public void setTerminos(Vector<Termino> terminos){
		this.terminos=terminos;
	}


	public static void main(String[] args){
		Polinomio pol = new Polinomio();
		Polinomio aux = new Polinomio();
		pol.makePolinomioFromFile(args[0]);
		aux.completar(pol);
		pol.verPolinomio();
	}
}
