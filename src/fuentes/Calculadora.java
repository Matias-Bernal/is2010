package fuentes;

import java.util.Vector;

public class Calculadora {

	public Polinomio suma(Polinomio sum1, Polinomio sum2) {
		Polinomio p = new Polinomio();
		p.setTerminos(new Vector<Termino>(sum1.gerTerminos()));
		Polinomio q = new Polinomio();
		q.setTerminos(new Vector<Termino>(sum2.gerTerminos()));
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

	public Polinomio resta(Polinomio min, Polinomio sus) {
		Polinomio p = new Polinomio();
		p.setTerminos(new Vector<Termino>(min.gerTerminos()));
		Polinomio q = new Polinomio();
		q.setTerminos(new Vector<Termino>(sus.gerTerminos()));
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
		cal.corregirPolinomio();
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
		Polinomio cociente = new Polinomio();
		
		Polinomio dividendo = new Polinomio();
		dividendo.setTerminos(new Vector<Termino>(p.gerTerminos()));
		Polinomio divisor = new Polinomio();
		divisor.setTerminos(new Vector<Termino>(q.gerTerminos()));
		divisor.corregirPolinomio();
		if (esPosibleRuffini(dividendo,divisor)){
			cociente = cocienteRuffini(p,q);
			return cociente;
		}
		else{
			dividendo = dividendo.ordenarDec(dividendo.completar(dividendo));
			dividendo.corregirPolinomio();
			divisor = divisor.ordenarDec(divisor);
			divisor.corregirPolinomio();
			while (!(dividendo.grado(dividendo)<divisor.grado(divisor))) {
				Polinomio monomio = new Polinomio();
				Polinomio aux = new Polinomio();
				Termino gradoDivisor = divisor.gerTerminos().firstElement();
				Termino gradoDividendo = dividendo.gerTerminos().firstElement();
				monomio.addTermPolinomio(gradoDividendo.getValor()/gradoDivisor.getValor(),gradoDividendo.getExponente()-gradoDivisor.getExponente());
				cociente.addTermPolinomio(gradoDividendo.getValor()/gradoDivisor.getValor(),gradoDividendo.getExponente()-gradoDivisor.getExponente());
				aux = producto(monomio,divisor);
				dividendo = resta(dividendo,aux);
			}
			return cociente;
		}
	}

	public Polinomio cocienteRuffini(Polinomio r, Polinomio s) {
		Polinomio p = new Polinomio();
		p.verPolinomio();
		p.setTerminos(r.gerTerminos());
		Polinomio q = new Polinomio();
		q.setTerminos(s.gerTerminos());
		//p = p.ordenarDec(p);// ordenamos el polinomio
		q = q.ordenarDec(q);
		p.verPolinomio();
		p = p.completar(p);
		p.verPolinomio();
		Vector <Termino> aux = q.gerTerminos(); // pasamos el polinomio a un vector para manipularlo mejor
		Integer cantCol = p.grado(p)+1; // sacamos la cantidad de terminos que tiene el dividendo para saber cuantas columnas tendria la tabla
		Integer divisor = aux.lastElement().getValor(); // sacamos el divisor
		
		Vector  <Termino> filaUno = p.gerTerminos();
		Vector  <Integer> filaDos = new Vector  <Integer>(cantCol) ; 
		Vector  <Integer> filaTres =new Vector  <Integer> (cantCol);
		
		Integer auxElem = 0;
		System.out.println(cantCol);
		for (int i = 0;i < cantCol; i++){
			Integer a = new Integer((divisor).intValue() * (auxElem).intValue());
			filaDos.add(i,a);
			filaTres.insertElementAt(filaUno.get(i).getValor() + filaDos.elementAt(i),i) ;
			auxElem = new Integer( filaTres.get(i));
		}
		
		// armamos res dede el vector fila3
		Polinomio res = new Polinomio();
		int i = 0;
		int j = cantCol-1;
		while  (i < cantCol && j >= 0){
			if (!(filaTres.get(i) == 0)){
				res.addTermPolinomio(filaTres.elementAt(i),j);
				j--;
				i++;
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
		calculadora.cocienteRuffini(p1, p2).verPolinomio();
	}
}