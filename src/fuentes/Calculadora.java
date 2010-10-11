package fuentes;

import java.util.Vector;

public class Calculadora {

	public Polinomio suma(Polinomio p, Polinomio q) {
		Polinomio cal = new Polinomio();
		if (cal.grado(p)<cal.grado(q)){
			p.addTermPolinomio(0, cal.grado(q));
			p = cal.completar(p);
			q = cal.completar(q);
		}else{
			if (cal.grado(p)>cal.grado(q)){
				q.addTermPolinomio(0, cal.grado(p));
				q = cal.completar(q);
				p = cal.completar(p);
			}else{
				q = cal.completar(q);
				p = cal.completar(p);				
			}
		}
		int i = 0;
		Vector<Termino> sumando1 = p.gerTerminos();
		Vector<Termino> sumando2 = q.gerTerminos();
		Vector<Termino> resultado = new Vector<Termino>();
		while (i< sumando1.size()){
			if (sumando1.elementAt(i).getExponente()==sumando2.elementAt(i).getExponente()){
				Termino res = new Termino();
				res.setExponente(sumando1.elementAt(i).getExponente());
				res.setValor(sumando1.elementAt(i).getValor()+sumando2.elementAt(i).getValor());
				resultado.add(res);
			}else{
				if (sumando1.elementAt(i).getExponente()<sumando2.elementAt(i).getExponente()){
					resultado.add(sumando1.elementAt(i));
				}else{
					resultado.add(sumando2.elementAt(i));
				}
			}
			i++;
		}
		cal.setTerminos(resultado);
		return cal;
	}

	public Polinomio resta(Polinomio p, Polinomio q) {
		Polinomio cal = new Polinomio();
		if (cal.grado(p)<cal.grado(q)){
			p.addTermPolinomio(0, cal.grado(q));
			p = cal.completar(p);
		}else{
			if (cal.grado(p)>cal.grado(q)){
				q.addTermPolinomio(0, cal.grado(p));
				q = cal.completar(q);				
			}
		}
		int i = 0;
		Vector<Termino> minuendo = p.gerTerminos();
		Vector<Termino> sustraendo = q.gerTerminos();
		Vector<Termino> resultado = new Vector<Termino>();
		while (i<minuendo.size()){
			Termino res = new Termino();
			if (minuendo.elementAt(i).getExponente()==sustraendo.elementAt(i).getExponente()){
				res.setExponente(minuendo.elementAt(i).getExponente());
				res.setValor(minuendo.elementAt(i).getValor()- sustraendo.elementAt(i).getValor());
				resultado.add(res);
			}else{
				if (minuendo.elementAt(i).getExponente()<sustraendo.elementAt(i).getExponente()){
					res.setExponente(sustraendo.elementAt(i).getExponente());
					resultado.add(minuendo.elementAt(i));
					
				}else{
					res.setExponente(minuendo.elementAt(i).getExponente());
					res.setValor(minuendo.elementAt(i).getValor()-sustraendo.elementAt(i).getValor());
					resultado.add(sustraendo.elementAt(i));
				}
			}
			i++;
		}
		cal.setTerminos(resultado);
		return cal;
	}

	public Polinomio producto(Polinomio p, Polinomio q) {		
		Polinomio total = new Polinomio();
		Vector<Termino> terminos = new Vector<Termino>();
		Vector<Termino> multiplicando = new Vector<Termino>(p.gerTerminos());
		Vector<Termino> multiplicador = new Vector<Termino>(q.gerTerminos());		
		Termino terminoResultante = new Termino();

		terminoResultante.setExponente(0);
		terminoResultante.setValor(0);
		terminos.add(terminoResultante);
		total.setTerminos(terminos);
		int i = 0;
		while(i<multiplicando.size()){
			Polinomio resultado = new Polinomio();
			Termino termMultiplicando = multiplicando.elementAt(i);
			Vector<Termino> terminosProducto = new Vector<Termino>();
			int j=0;
			while (j<multiplicador.size()){
				Termino terminoResul = new Termino();
				Termino termMultiplicador = multiplicador.elementAt(j);
				terminoResul.setValor(termMultiplicando.getValor()*termMultiplicador.getValor());
				terminoResul.setExponente(termMultiplicando.getExponente()+termMultiplicador.getExponente());
				terminosProducto.add(terminoResul);
				j++;
			}
			resultado.setTerminos(terminosProducto);
			total = suma(total,resultado);
			i++;
		}
		return total;
	}

	public Polinomio cociente(Polinomio p, Polinomio q) {
		Polinomio coc = new Polinomio();
		if (esPosibleRuffini(p,q)){
			coc = cocienteRuffini(p,q);
			return coc;
		}
		else{
			
			return coc; //hacerlo de otra forma
		}
	}

	public Polinomio cocienteRuffini(Polinomio p, Polinomio q) {
		
		p.ordenarDec(p);// ordenamos el polinomio
		Vector <Termino> aux = q.gerTerminos(); // pasamos el polinomio a un vector para manipularlo mejor
		int cantCol = p.grado(p)+1; // sacamos la cantidad de terminos que tiene el dividendo para saber cuantas columnas tendria la tabla
		int divisor = aux.get(aux.size()).getValor(); // sacamos el divisor
		Vector<Termino> filaUno = p.gerTerminos();
		Vector <Integer> filaDos = new Vector <Integer> (p.grado(p)); 
		Vector <Integer> filaTres = new Vector <Integer> (p.grado(p));
		
		int auxElem = 0;
		for (int i = 0;i <= cantCol; i++){
			filaDos.add(i,divisor * auxElem);
			filaTres.add(i, filaUno.get(i).getValor() + filaDos.get(i).intValue());
			auxElem = filaTres.get(i);
		}
		
		// el resultado esta en la fila 3
		
		Polinomio res = new Polinomio();
		for (int j =0 ; j<= cantCol; j++ ){
			if (!(filaTres.get(j) == 0)){
				res.addTermPolinomio(j,filaTres.get(j));
			}
		}
			
		return res;
	}

	public boolean esPosibleRuffini(Polinomio p, Polinomio q) {
		Polinomio binomio = new Polinomio();
		binomio = q;
		boolean grado = true;
		boolean valor = true;
		grado =(q.grado(binomio)==1);
		valor =((q.ordenarDec(binomio).gerTerminos().firstElement().getValor())==1);
		return grado&&valor;
	}

	public static void main(String[] args){
		Calculadora calculadora = new Calculadora();
		Polinomio p1 = new Polinomio();
		Polinomio p2 = new Polinomio();
		p1.makePolinomioFromFile(args[0]);
		p2.makePolinomioFromFile(args[1]);
		System.out.println("suma: ");
		calculadora.suma(p1, p2).verPolinomio();
		System.out.println("resta: ");
		calculadora.resta(p1, p2).verPolinomio();
		System.out.println("producto");
		p1.ordenarDec(calculadora.producto(p1, p2)).verPolinomio();
		//System.out.println("cociente");
		//calculadora.cociente(p1, p2).verPolinomio();*/
	}
}