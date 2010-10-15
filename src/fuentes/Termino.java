package fuentes;

import java.text.DecimalFormat;

public class Termino{
	private float valor;
	private int exponente;

	public float getValor() {
		return valor;
	}

	/*public void setValor(int v) {
		valor = v;
	}*/
	
	public void setValor(float v) {
		valor = v;
	}

	public int getExponente() {
		return exponente;
	}

	public void setExponente(int e) {
		exponente = e;
	}

	public String toString(){
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("#0.00");
		if (valor>=0){
			return (df.format(valor)+"X"+"^"+exponente);
		}else{
			return (df.format(valor)+"X"+"^"+exponente);
		}
	}
}