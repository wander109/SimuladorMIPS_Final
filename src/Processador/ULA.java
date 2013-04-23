package Processador;

public class ULA {
	/* A execucao propria das funcoes, como somar, shift left/right (multiplicacao/divisao)
	 * 
	 */
	

	
	//as funções do tipo I é o mesmo método
	public String add(int rs, int rd, int rt){ 
		rt = rs + rd;
		return String.valueOf(rt);
	}
	
	
	public double sub(double rs, double rt){
		double result = rs - rt;
		return result;	
		
	}
	
	public String div(int a1, int a2){
		int div;
		div = a1/a2;
		return String.valueOf(div);	
	}
	
	
	public String mult(int a1, int a2){
		int mult;
		mult = a1*a2;
		return String.valueOf(mult);	
	}
	
	//os shifts estão errados, vou re-ver isso amanha
	public String srl(String a1, int qntde){
		a1 = a1.substring(0, (a1.length() - qntde));
		return a1;
	}

	public String sll(String a1, int qntde){
		a1 = a1.substring(qntde, (a1.length()-1));
		return a1;
	}
	

	
	public String or(String a1, String a2){
		String result = "";
		for (int i = 0; i< a1.length(); i++ ){
			
			if ((a1.charAt(i) != 1) && (a2.charAt(i)!= 1) ){
				result = result + "0";
			}
			else{
				result = result + "1";
			}	
		}
		
		return result;

	}
	

	public String and(String a1, String a2){
		String result = "";
		for (int i = 0; i< a1.length(); i++ ){
			if ((a1.charAt(i) == 1) && (a2.charAt(i)==1) ){
				result = result + "1";
			}
			else{
				result = result + "0";
			}	
		}
		
		return result;

	}
	
	
	public String not(String a1){
		String result = "";
		
		for (int i = 0; i < a1.length(); i++){
			if (a1.charAt(i) == 1){
				result = result + "0";
			}
			else{
				result = result + "1";
			}

		}
		return result;
		
	}
	
	
}
