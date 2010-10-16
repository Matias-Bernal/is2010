package fuentes;

 /**
 * @author Bernal - Odorizzi - Bressan - Jaule.
 * Clase que representa un termino. 
 */

public class Termino{
	
	private int valor; // usado para representar el coeficiente del termino.
	private int exponente; // usado para represenatr el exponente del termino.
	
	/** 
	 * getValor()
	 * Metodo que retorna el coeficiente del termino.
	 * @return int representando al coeficiente del termino.
	 */
	
	public int getValor() {
		return valor;
	}

	/** 
	 * getValor()
	 * Metodo que setea el coeficiente del termino.
	 * @param v El coeficiente a setearle al termino.
	 */
	public void setValor(int v) {
		valor = v;
	}
	
	/** 
	 * getExponente()
	 * Metodo que retorna el exponente del termino.
	 *@return int representando el valor del exponente.
	 */
	public int getExponente() {
		return exponente;
	}
	
	/** 
	 * setExponente()
	 * Metodo que setea el exponente del termino.
	 * @param e El exponente a setearle al termino.
	 */
	public void setExponente(int e) {
		exponente = e;
	}
	
	/** 
	 * toString()
	 * Metodo que retorna una representacion del termino en un String.
	 *@return String representando al tremino.
	*/
	public String toString(){
		if (valor>=0){
			return (valor+"X"+"^"+exponente);
		}else{
			return (valor+"X"+"^"+exponente);
		}
	}
}