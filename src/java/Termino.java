package java;

public class Termino {
 
	private int valor;
	private int exponente;


	public int getValor() {
		return valor;
	}
 
	public void setValor(int v) {
		valor = v;
	}
	 
	public int getExponente() {
		return exponente;
	}
	 
	public void setExponente(int e) {
		exponente = e;
	}
	
	public String toString(){
		if (valor>=0){
			return ("+",valor,"^",exponente);
		}else{
			return ("-",valor,"^",exponente);
		}
	}
}