package Processador;

public class ULA {
	/* A execucao propria das funcoes, como somar, shift left/right (multiplicacao/divisao)
	 * 
	 */
	

	
	//as funções do tipo I é o mesmo método
	public double add(double rs, double rt){ 
		double result = rs + rt;
		return result;
	}
	
	
	public double sub(double rs, double rt){
		double result = rs - rt;
		return result;	
		
	}
	
	public double div(double a1, double a2){
		double div;
		div = (int)a1/a2;
		return div;	
	}
	
	
	public double mult(double a1, double a2){
		double mult = a1*a2;
		return mult;	
	}
	
	public String srl(String a1, int qntde){
		a1 = a1.substring(0, (a1.length() - qntde));
		for (int i = 0; i < qntde; i++){
			a1 = "0" + a1 ;
		}
		return a1;
	}

	public String sll(String a1, int qntde){
		a1 = a1.substring(qntde,a1.length());
		for (int i = 0; i < qntde; i++){
			a1 = a1 + "0";
		}
		return a1;
	}
	

	
	public String or(String a1, String a2){
		String result = "";
		
		if (a1.length() > a2.length()){
			while (a1.length() > a2.length()) {
				a2 = "0" + a2;
			}
		}
		
		for (int i = 0; i< a1.length(); i++ ){
			
			if ((a1.charAt(i) != '1') && (a2.charAt(i) != '1') ){
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
		
		if (a1.length() > a2.length()){
			while (a1.length() > a2.length()) {
				a2 = "0" + a2;
			}
		}
		
		for (int i = 0; i< a1.length(); i++ ){
			if ((a1.charAt(i) == '1') && (a2.charAt(i)=='1') ){
				result = result + "1";
			}
			else{
				result = result + "0";
			}	
		}
		
		return result;

	}
	
	public double slt(double a, double b){
		double result;
		if (a < b){
			result = 1;
		}
		else{
			result = 0;
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
