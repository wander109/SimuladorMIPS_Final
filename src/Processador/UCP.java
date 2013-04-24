package Processador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import Memoria.*;
import ManipulacaoArquivo.TrataArquivo;

public class UCP {
	
	private ULA ula; //Pra chamar os métodos direto pelo objeto
	private TreeMap<String, String> dicionarioInstrucoes = new TreeMap<String, String>();
	private TreeMap<String, String> dicionarioInstrucoesRFormat = new TreeMap<String, String>(); 
	private TrataArquivo carregaRegistradores;
	private TreeMap<String, String> listaDeRegistradores;
	public Registro registradores;
	private String rs;
	private String rd;
	private String rt;
	private int address;
	private int constant;
	private int offset;
	private int shamt;
	private String operacao;
	private String funcao;
	private String result;
	
	public UCP(String arqRFormat, String arqInstrucoes){	
		try {
			dicionarioInstrucoesRFormat = montaHash(arqRFormat);
			dicionarioInstrucoes = montaHash(arqInstrucoes);
			carregaRegistradores = new TrataArquivo("Traducoes/Registradores.txt");
			listaDeRegistradores = carregaRegistradores.carregaMapaDeTraducao();
			registradores = new Registro(listaDeRegistradores);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ula = new ULA();
		
	}
	
	public TreeMap<String, String> montaHash(String arq) throws IOException{
		TrataArquivo leitorDeTraducoes = new TrataArquivo(arq);
		return leitorDeTraducoes.carregaMapaDeTraducao();
		
	}
	
	public int converteDecimal(String binario){
		int decimal = 0, j = 1;
		for(int i=binario.length(); i >0 ; i--){
			decimal += Integer.valueOf(binario.charAt(i)) * j;
			j+= j;
		}		
		return decimal;
	}
	
	public void pularELinkar(int address){
		//ula.atualizaRa(pc+1);
		//ula.atualizaPC(address);	
		
	}
	
	public String acharRegistrador(String codigoBin){
		String registro = "";
		
		for(int i = 0; i < listaDeRegistradores.size(); i++){
			String aux = listaDeRegistradores.get(listaDeRegistradores.keySet().toArray()[i]);
			if	(aux.equals(codigoBin)){
				registro = String.valueOf(listaDeRegistradores.keySet().toArray()[i]);
				return registro;
			}
		
		}
		System.out.println(registro);
		return registro;
	}
	
	private String getBinario(double n, int casas){
		String aux = "";
		ArrayList resto = new ArrayList();
		String res = "";
		if(n < 0){
			n = n*(-1);

			while (n != 0){
				resto.add((int)n%2);
				n = (int)(n/2);
			}
			
			for (int i=resto.size()-1;i>=0;i--){
				res += resto.get(i);
			}
			if(res.length() != casas){
				int tamFaltante = casas - res.length();
				for(int i = 0; i < tamFaltante; i++)
					res = "0" + res;
			}
			res = "-" + res;
			
		}
		else{
			while (n != 0){
				resto.add((int)n%2);
				n = (int)(n/2);
			}
			
			for (int i=resto.size()-1;i>=0;i--){
				res += resto.get(i);
			}
			if(res.length() != casas){
				int tamFaltante = casas - res.length();
				for(int i = 0; i < tamFaltante; i++)
					res = "0" + res;
			}
		}
		
		
			return res;
	}
	
	public double converteParaDecimal(String binario){
		  double j=0;
		    for(int i=0;i<binario.length();i++){
		        if(binario.charAt(i)== '1'){
		         j=j+ Math.pow(2,binario.length()-1-i);
		     }
		     

		    }
		    if (binario.charAt(0) == '-'){
		    	j = j*(-1);
		    }
		    return  j;
		}
	
	
	public int lerInterpretarInstrucao(String instrucao, int pc) throws IOException{
		
		System.out.println("entrou na UCP - pc= " + pc);
		double num1, num2;
		int aux;
		double result;
		String Result;
		String valor1, valor2;
		String op = instrucao.substring(0,6); 
		
		if(op.equals("000000")){ 
			
			funcao = String.valueOf(instrucao.subSequence(26,32));
			operacao = dicionarioInstrucoesRFormat.get(funcao);
			switch (operacao) {
			case "add":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
				System.out.println("Valor do registrador rt= "+ registradores.getValorRegistrador(rt));
				num2 = converteParaDecimal(registradores.getValorRegistrador(rt));
				System.out.println("num2 = "+ num2);
				
				
				result = ula.add(num1, num2);
				registradores.setValorRegistrador(rd, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rd= " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
				
			case "or":
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//tansformo o valor que esta decimal no objeto registrador em binario para q a operação OR possa ser feita
				valor1 = registradores.getValorRegistrador(rs);
				System.out.println("valor1 = "+ valor1);
				
				valor2 = registradores.getValorRegistrador(rt);
				System.out.println("valor2 = "+ valor2);
				
				Result = ula.or(valor1, valor2);
				System.out.println("resultado= " + Result);
				registradores.setValorRegistrador(rd, Result);	
				System.out.println("valor registrador Rd = " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
			
			case "and":
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//tansformo o valor que esta decimal no objeto registrador em binario para q a operação OR possa ser feita
				valor1 = registradores.getValorRegistrador(rs);
				System.out.println("valor1 = "+ valor1);
				
				valor2 = registradores.getValorRegistrador(rt);
				System.out.println("valor2 = "+ valor2);
				
				Result = ula.and(valor1, valor2);
				System.out.println("resultado= " + Result);
				registradores.setValorRegistrador(rd, Result);	
				System.out.println("valor registrador Rd = " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
			
			case "sub":
				
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
				System.out.println("Valor do registrador rt= "+ registradores.getValorRegistrador(rt));
				num2 = converteParaDecimal(registradores.getValorRegistrador(rt));
				System.out.println("num2 = "+ num2);
				
				
				result = ula.sub(num1, num2);
				registradores.setValorRegistrador(rd, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rd= " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
				
			case "mult":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
				System.out.println("Valor do registrador rt= "+ registradores.getValorRegistrador(rt));
				num2 = converteParaDecimal(registradores.getValorRegistrador(rt));
				System.out.println("num2 = "+ num2);
				
				
				result = ula.mult(num1, num2);
				registradores.setValorRegistrador(rd, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rd= " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
				
			case "div":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
				System.out.println("Valor do registrador rt= "+ registradores.getValorRegistrador(rt));
				num2 = converteParaDecimal(registradores.getValorRegistrador(rt));
				System.out.println("num2 = "+ num2);
				
				
				result = ula.div(num1, num2);
				registradores.setValorRegistrador(rd, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rd= " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
				
			case "sll":
				valor1 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//tansformo o valor que esta decimal no objeto registrador em binario para q a operação OR possa ser feita
				
				valor1 = registradores.getValorRegistrador(rt);
				System.out.println("valor1 = "+ valor1);
				
				shamt = (int)converteParaDecimal(instrucao.substring(21,26));
				System.out.println("Shamt = "+ shamt);
				
				Result = ula.sll(valor1, shamt);
				System.out.println("resultado= " + Result);
				registradores.setValorRegistrador(rd, Result);	
				System.out.println("valor registrador Rd = " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
				
			case "srl":
				valor1 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//tansformo o valor que esta decimal no objeto registrador em binario para q a operação OR possa ser feita
				
				valor1 = registradores.getValorRegistrador(rt);
				System.out.println("valor1 = "+ valor1);
				
				shamt = (int)converteParaDecimal(instrucao.substring(21,26));
				System.out.println("Shamt = "+ shamt);
				
				Result = ula.srl(valor1, shamt);
				System.out.println("resultado= " + Result);
				registradores.setValorRegistrador(rd, Result);	
				System.out.println("valor registrador Rd = " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
				
			case "slt":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				rd = acharRegistrador(instrucao.substring(16,21));
				System.out.println("rd: "+rd);
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
				System.out.println("Valor do registrador rt= "+ registradores.getValorRegistrador(rt));
				num2 = converteParaDecimal(registradores.getValorRegistrador(rt));
				System.out.println("num2 = "+ num2);
				
				
				result = ula.slt(num1, num2);
				registradores.setValorRegistrador(rd, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rd= " + registradores.getValorRegistrador(rd));
				
				pc = pc +1;
				break;
		
			case "jr":
				num1 = 0;
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				
				pc = (int)num1;
				break;
			default:
				break; 
		
			}
			
			return pc;
		} else{
			operacao = dicionarioInstrucoes.get(op);
			switch (operacao) {
			case "addi":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
				num2 = converteParaDecimal(instrucao.substring(16,32));
				System.out.println("num2 = "+ num2);
				
				result = ula.add(num1, num2);
				registradores.setValorRegistrador(rt, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rt= " + registradores.getValorRegistrador(rt));
				pc = pc +1;
				break;
				
			case "slti":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
			
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				System.out.println("num1 = "+ num1);
				
			
				num2 = converteParaDecimal(instrucao.substring(16,32));
				System.out.println("num2 = "+ num2);
				
				
				result = ula.slt(num1, num2);
				registradores.setValorRegistrador(rt, getBinario(result, 32));
				System.out.println("resultado = " + getBinario(result, 32));
				
				
				System.out.println("Valor Registrador Rt= " + registradores.getValorRegistrador(rt));
				pc = pc +1;
				break;
				
			case "beq":
				System.out.println("-- beq --");
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				valor1 = registradores.getValorRegistrador(rs);
				System.out.println(valor1);
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				valor2 = registradores.getValorRegistrador(rt);
				System.out.println(valor2);
				
				if (valor1.equals(valor2)){
					pc = (int)converteParaDecimal(instrucao.substring(16,32));
				}
				else{
					pc = pc + 1;
				}
			
				System.out.println(pc);

				break;
				
			case "bne":
				num1 = num2 = 0;
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				num1 = converteParaDecimal(registradores.getValorRegistrador(rs));
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				num2 = converteParaDecimal(registradores.getValorRegistrador(rt));
				
				if (num1 != num2){
					pc = (int)converteParaDecimal(instrucao.substring(16,32));
				}
				else{
					pc = pc + 1;
				}
			
				
				break;
				
			case "j":
				System.out.println("-- j --");
					pc = (int)converteParaDecimal(instrucao.substring(6,32));
					System.out.println(pc);
				
				break;
				
			case "jal":
				
				registradores.setValorRegistrador("$ra", String.valueOf(pc + 1) );
				pc = (int)converteParaDecimal(instrucao.substring(6,32));
			
			break;
				
			case "andi":
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				valor1 = registradores.getValorRegistrador(rs);
				System.out.println("valor1 = "+ valor1);
				
				valor2 = instrucao.substring(16,32);
				System.out.println("valor2 = "+ valor2);
				
				Result = ula.and(valor1, valor2);
				registradores.setValorRegistrador(rt, Result);
				System.out.println("resultado = " + Result);
				
				
				System.out.println("Valor Registrador Rt= " + registradores.getValorRegistrador(rt));
				pc = pc +1;
				break;	
				
			case "ori":
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				
				//pego o valor do registrador e transformo de bin para decimal.
				System.out.println("Valor do registrador rs= "+ registradores.getValorRegistrador(rs));
				valor1 = registradores.getValorRegistrador(rs);
				System.out.println("valor1 = "+ valor1);
				
				valor2 = instrucao.substring(16,32);
				System.out.println("valor2 = "+ valor2);
				
				Result = ula.or(valor1, valor2);
				registradores.setValorRegistrador(rt, Result);
				System.out.println("resultado = " + Result);
				
				
				System.out.println("Valor Registrador Rt= " + registradores.getValorRegistrador(rt));
				pc = pc +1;
				break;
				
			case "lw":
				aux = 0;
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				aux = (int)converteParaDecimal(instrucao.substring(16,32));
				System.out.println("num1 = "+ aux);
				
				valor1 = registradores.getValorRegistrador(rs);
				
				valor2 = "0000000000000000000000000000" + valor1.substring(aux,(aux + 4));
				
				registradores.setValorRegistrador(rt, valor2);
				System.out.println("Valor Registrador Rt= " + registradores.getValorRegistrador(rt));
				pc = pc +1;
				break;
				
			case "sw":
				aux = 0;
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				System.out.println("rs: "+rs);
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				aux = (int)converteParaDecimal(instrucao.substring(16,32));
				System.out.println("num1 = "+ aux);
				
				valor1 = registradores.getValorRegistrador(rt);
				
				valor2 = "0000000000000000000000000000" + valor1.substring(aux,(aux + 4));
				
				registradores.setValorRegistrador(rs, valor2);
				System.out.println("Valor Registrador Rs= " + registradores.getValorRegistrador(rt));
				pc = pc +1;
				break;
				
			case "lui":
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				
				rt = acharRegistrador(instrucao.substring(11,16));
				System.out.println("rt: "+rt);
				
				
				//pego o valor do registrador e transformo de bin para decimal.
				
				valor1 = instrucao.substring(16,32);
				System.out.println("valor1 = "+ valor1);
				
				valor2 = valor1.substring(0, 16) + "0000000000000000";
				
				registradores.setValorRegistrador(rt, valor2);
				pc = pc +1;
				break;

				
		
			default:
				break; 
			}
			
		
		}
		
		return pc;
	}
}