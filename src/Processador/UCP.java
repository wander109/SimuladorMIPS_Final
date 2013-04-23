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
	private Registro registradores;
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

		while (n != 0){
			resto.add(n%2);
			n = (int)(n/2);
		}
		String res = "";
		for (int i=resto.size()-1;i>=0;i--){
			res += resto.get(i);
		}
		
		
		
		
		
		return res;
	}
	
	private double converteParaDecimal(String binario){
		  double j=0;
		    for(int i=0;i<binario.length();i++){
		        if(binario.charAt(i)== '1'){
		         j=j+ Math.pow(2,binario.length()-1-i);
		     }

		    }
		    return  j;
		}
	
	
	public void lerInterpretarInstrucao(String instrucao) throws IOException{
		System.out.println("entrou na UCP");
		
		double result;
		String Result;
		String op = instrucao.substring(0,6); 
		
		if(op.equals("000000")){ 
			
			funcao = String.valueOf(instrucao.subSequence(26,32));
			operacao = dicionarioInstrucoesRFormat.get(funcao);
			switch (operacao) {
			case "add":
				//rs = Integer.valueOf(instrucao.substring(6,11));
				//rt = Integer.valueOf(instrucao.substring(12,17));
				//rd = Integer.valueOf(instrucao.substring(18,23));
				System.out.println("add " + rs + " "+ rt+" "+rd);
				//ula.add(rs,rt,rd);
				break;
				
			case "or":
				String valor1, valor2;
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
				break;
			
			case "sub":
				double num1, num2;
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
				break;
				
		/* 	case "sll":
				String rss = instrucao.substring(6,11);
				String rts = instrucao.substring(12,17);
				shamt = converteDecimal(instrucao.substring(18,23));
				rts = ula.sll(rss,shamt);
				break;
			case "srl":
				rss = instrucao.substring(6,11);
				rts = instrucao.substring(12,17);
				shamt = converteDecimal(instrucao.substring(18,23));
				rts = ula.srl(rss,shamt);
				break;
			case "jr":
				//ula.srl(rs,rt,shamt);
				break;
			default:
				break; */
			}
		} else{
			operacao = dicionarioInstrucoes.get(op);
			switch (operacao) {
			case "addi":
			//	rs = Integer.valueOf(instrucao.substring(6,11));
				//rt = Integer.valueOf(instrucao.substring(12,17));
				address =  converteDecimal(instrucao.substring(18,31));
				//ula.addi(rs,rt,address);
				break;
				
		/*	case "j":
				address =  converteDecimal(instrucao.substring(6,31));
				//ula.atualizaPC(address);
				break;
				
			case "jal":
				address =  converteDecimal(instrucao.substring(6,31));
				//this.pularELinkar();
				break;
				
			case "beq":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				address =  converteDecimal(instrucao.substring(18,31));
				//ula.beq(rs,rt,address);
				break;
				
			case "bnq":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				address =  converteDecimal(instrucao.substring(18,31));
				//ula.bnq(rs,rt,address);
				break;	
				
			case "blez":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				//ula.blez(rs,rt,0);
				break;	
				
			case "bgtz":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				address =  converteDecimal(instrucao.substring(18,31));
				//ula.bgtz(rs,rt,address);
				break;	
				
			case "addiu":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				constant =  converteDecimal(instrucao.substring(18,31));
				//ula.addiu(rs,rt,constant);
				break;
				
			case "slti":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				constant =  converteDecimal(instrucao.substring(18,31));
				//ula.slti(rs,rt,constant);
				break;
				
			case "sltiu":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				constant =  converteDecimal(instrucao.substring(18,31));
				//ula.sltiu(rs,rt,constant);
				break;	
				
			case "andi":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				constant =  converteDecimal(instrucao.substring(18,31));
				//ula.andi(rs,rt,constant);
				break;	
				
			case "ori":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				constant =  converteDecimal(instrucao.substring(18,31));
				//ula.ori(rs,rt,constant);
				break;	
				
			case "lui":
				rs = Integer.valueOf(instrucao.substring(6,11));
				address =  converteDecimal(instrucao.substring(12,31));
				//ula.lui(rs,address);
				break;	
			
			case "lb":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,16));
				offset =  converteDecimal(instrucao.substring(12,31));
				//ula.lb(rs,rt,offset);
				break;	
			
			case "sw":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,16));
				offset =  converteDecimal(instrucao.substring(12,31));
				//ula.sw(rs,rt,offset);
				break;
			
			default:
				break; */
			}
			
		
		}
	}
}