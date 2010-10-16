package fuentes;

import java.io.*;
import java.util.*;

/**
 * @author Bernal - Odorizzi - Bressan - Jaule.
 * Clase que representa un polinomio. 
 */
public class Polinomio {

	private Vector<Termino> terminos; // usado para almacenar y manipular los terminos del polinomio
	
	/**
	* Constructor de la clase Polinomio(): prepara el vector para poder empezar a usarlo en la representacion del polinomio.
	*/
	public Polinomio() {
		terminos = new Vector<Termino>();
	}

	/**
	* makePolinomioFromFile(String fileName): Setea el polinomio a partir de un polinomio que se encuentra escrito en un archivo.
	* @param filName representa el nombre del archivo donde se encuentra el polinomio.
	*/
	public void makePolinomioFromFile(String fileName) {
		try {
            FileReader archivo = new FileReader(fileName);
            BufferedReader l = new BufferedReader(archivo);
            String lineaDelArchivo = l.readLine();
            
            while ( lineaDelArchivo !=null) {
				String[] datos = lineaDelArchivo.split(String.valueOf(' '));
							
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

	/**
	* verPolinomio(): Muestra por consola al polinomio.
	*/
	public void verPolinomio(){
		int i = 0;
		int valor = 0;
		int exponete = 0;
		String res = "";
		Termino term = new Termino();
		while (i<terminos.size()){
			term = terminos.elementAt(i);
			exponete = term.getExponente();
			valor = term.getValor();
			switch (valor){
			case 0:	break;
			case 1:
				switch(exponete){
				case 0:
					if (i!=0){
						res += "+"+valor;
					}else{
						res += valor;
					}
					break;
				case 1:
					if (i!=0){
						res +="+X";
					}else{
						res +="X";
					}
					break;
				default:
					if (i!=0){
						res +="+X^"+exponete;
					}else{
						res +="X^"+exponete;
					}
					break;
				}
				break;
			case -1:
				switch(exponete){
				case 0:
					res += valor;
					break;
				case 1:
					res +="-X";
					break;
				default:
					res +="-X^"+exponete;
					break;
				}
				break;
			default:
				if(valor<-1){
					switch(exponete){
					case 0:
						res += valor;
						break;
					case 1:
						res +=valor+"X";
						break;
					default:
						res +=term;
						break;
					} 
				}else{
					switch(exponete){
					case 0:
						if (i!=0){
							res += "+"+valor;
						}else{
							res += valor;
						}
						break;
					case 1:
						if (i!=0){
							res += "+"+valor+"X";
						}else{
							res += valor+"X";
						}
						break;
					default:
						if (i!=0){
							res +="+"+term;
						}else{
							res +=term;
						}
						break;
					} 					
				}
			break;
			}
			i++;
		}
		System.out.println(res);
	}

	/**
	* addTermPolinomio(int v, int e): Agrega un termino al polinomio.
	* @param v representa el coeficiente del termino que se desea agregar.
	* @param e representa el exponente del termino que se desea agregar.
	*/
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

	/**
	* ordenarDec(Polinomio p): Ordena los terminos de un polinomio de forma decreciente.
	* @param p representa el polinomio a ordenar.
	* @return p ordenado decrecientemente.
	*/
	public Polinomio ordenarDec(Polinomio p) {
		Polinomio res = new Polinomio();
		Vector<Termino> pol = new Vector<Termino>(p.gerTerminos());
		Vector<Termino> ord = new Vector<Termino>();
		while (!pol.isEmpty()){
			int i = 0;
			Termino max = pol.elementAt(i);
			while  (i<pol.size()-1){	//busca el maximo para ponerlo al principio
				if (max.getExponente()<pol.elementAt(i+1).getExponente()){
					max = pol.elementAt(i+1);
				}
				i++;
			}
			ord.addElement(max);
			pol.remove(max);		//saca el maximo de la lista para poder buscar el que lo sigue
		}
		res.setTerminos(ord);
		return res;
	}

	/**
	* completar(Polinomio p):Completa un polinomio en el caso de que le falten terminos.
	* @param p representa el polinomio a completar.
	* @return p completo.
	*/
	public Polinomio completar(Polinomio p) {
		Polinomio polinomio = p;
		if (!estaOrdenado(polinomio)){
			polinomio=ordenarDec(polinomio);
		}
		int grado = polinomio.grado(polinomio);
		int i = 0;
		int tamaño = 0;
		Vector<Termino> terminos = polinomio.gerTerminos();
		tamaño = terminos.size();
		Termino[] term = new Termino[tamaño];
		
		while (i<terminos.size()){
			term[i]= terminos.elementAt(i);
			i++;
		}
		Termino[] correccion = new Termino[grado+1];
		i = 0;
		int exponete = 0;
		while (i<term.length){
			exponete = term[i].getExponente();
			correccion[exponete] = term[i];
			i++;
		}
		i=0;
		while (i<grado+1){
			if (correccion[i]==null){
				Termino monomio = new Termino();
				monomio.setExponente(i);
				correccion[i]= monomio;
			}
			i++;
		}
		Vector<Termino> correc = new Vector<Termino>();
		i=0;
		tamaño = correccion.length-1;
		while(i<correccion.length){
			correc.add(correccion[tamaño-i]);
			i++;
		}
		polinomio.setTerminos(correc);
		return polinomio;
	}
	
	/**
	* estaOrdenado(Polinomio p): Indica si un polinomio esta ordenado.
	* @param p representa el polinomio a analizar.
	* @return True si p esta ordenado o False caso contrario.
	*/
	public boolean estaOrdenado(Polinomio p) {
		boolean ordenado = true;
		int i = 0;
		int termino1;
		int termino2;
		Vector<Termino> pol = p.gerTerminos();
		while ((i < pol.size()-1) && ordenado){
			termino1 = pol.elementAt(i).getExponente();
			termino2 = pol.elementAt(i+1).getExponente();
			ordenado = ordenado && (termino1>=termino2);
			i++;
		}
		return ordenado;
	}

	/**
	* grado(Polinomio p): Indica el grado de un polinomio.
	* @param p representa el polinomio a analizar.
	* @return int representando el grado de p.
	*/
	public int grado(Polinomio p) {
		Polinomio polinomio = p;
		if (!estaOrdenado(polinomio)){
			polinomio=ordenarDec(polinomio);
		}
		return polinomio.gerTerminos().firstElement().getExponente();
	}

	/**
	* indexOf(int exponente): Indica la posicion de un exponente dentro de la estructura que representa al polinomio.
	* @param exponente que se desea buscar.
	* @return int representando la posicion del exponente dentro del la estructura que almacena los terminos.
	*/
	private int indexOf(int exponente) {
		for (int i = 0; i < terminos.size(); i++) {
			Termino termino = terminos.elementAt(i);
			if(termino.getExponente() == exponente)
				return i;
		}
		return -1;
	}

	/**
	* gerTerminos(): Devuelve un vector conteniendo todos los terminos del polinomio.
	* @return Un vector con los terminos del polinomio.
	*/
	public Vector<Termino> gerTerminos()  {
		return terminos;
	}

	/**
	* setTerminos(Vector<Termino> terminos): Setea el polinomio a partir de un vector con terminos.
	*/
	public void setTerminos(Vector<Termino> terminos){
		this.terminos=terminos;
	}
	
	/**
	* corregirPolinomio(): Remueve del polinomio los terminos que poseen coeficiente igual a 0.
	*/
	public void corregirPolinomio(){
		int i = 0;
		boolean fin = false;
		while((i<terminos.size()-1)&&(!fin)){
			if (terminos.elementAt(i).getValor()==0){
				terminos.remove(i);
			}else{
				fin = true;
			}
			i++;
		}
	}
	
	//TODO borrar main
	public static void main(String[] args){
		Polinomio pol = new Polinomio();
		Polinomio aux = new Polinomio();
		pol.makePolinomioFromFile(args[0]);
		pol=aux.ordenarDec(pol);
		pol.verPolinomio();
	}
}