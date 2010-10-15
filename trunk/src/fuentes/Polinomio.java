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

					float valor = Integer.parseInt(valorexponete.substring(0, valorexponete.indexOf("^")));
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

	public void verPolinomio(){
		int i = 0;
		float valor = 0;
		int exponete = 0;
		String res = "";
		Termino term = new Termino();
		while (i<terminos.size()){
			term = terminos.elementAt(i);
			exponete = term.getExponente();
			valor = term.getValor();
			
			if (valor == 1) {
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
			} else if (valor == -1) {
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
			}else{
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
			}
			i++;
		}
		System.out.println(res);
	}

	public void addTermPolinomio(float v, int e){
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

	public int grado(Polinomio p) {
		Polinomio polinomio = p;
		if (!estaOrdenado(polinomio)){
			polinomio=ordenarDec(polinomio);
		}
		return polinomio.gerTerminos().firstElement().getExponente();
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
	public static void main(String[] args){
		Polinomio pol = new Polinomio();
		Polinomio aux = new Polinomio();
		pol.makePolinomioFromFile(args[0]);
		pol=aux.ordenarDec(pol);
		pol.verPolinomio();
	}
}