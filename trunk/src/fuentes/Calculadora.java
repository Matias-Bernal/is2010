package fuentes;

import java.util.Vector;

public class Calculadora {

	public Polinomio suma(Polinomio p, Polinomio q) {
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
		return null;
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
		return null;
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
		/*System.out.println("producto");
		calculadora.producto(p1, p2).verPolinomio();
		System.out.println("cociente");
		calculadora.cociente(p1, p2).verPolinomio();*/
	}
}