package Processador;

import java.io.IOException;
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
		System.out.println("entrou no acharRegistrador");
		
		for(int i = 0; i < listaDeRegistradores.size(); i++)
			if (listaDeRegistradores.values().toArray()[i] == codigoBin){
				registro = (String)listaDeRegistradores.keySet().toArray()[i];
			}
		System.out.println(registro);
		return registro;
	}
	
	private String getBinario(int numero, int casas){
		String aux;
		aux = Integer.toString(numero, 2);
		
		if(aux.length() != casas){
			int tamFaltante = casas - aux.length();
			for(int i = 0; i < tamFaltante; i++)
				aux = "0" + aux;
		}
		
		return aux;
	}
	
	
	public void lerInterpretarInstrucao(String instrucao) throws IOException{
		System.out.print("entrou na UCP");
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
				System.out.println("entrou no or");
				String valor1, valor2;
				valor1 = valor2 = "";
				//converte o numero binário presente na instrução para o registrador correspondente.
				rs = acharRegistrador(instrucao.substring(6, 11));
				rt = acharRegistrador(instrucao.substring(12,17));
				rd = acharRegistrador(instrucao.substring(18,23));
				//tansformo o valor que esta decimal no objeto registrador em binario para q a operação OR possa ser feita
				valor1 = getBinario(registradores.getValorRegistrador(rs), 32);
				valor2 = getBinario(registradores.getValorRegistrador(rt), 32);
				result = ula.or(valor1, valor2);
				//registradores.setValorRegistrador(rd, result);
				System.out.println(registradores.getValorRegistrador(rd));
				break;
			
			/* case "sub":
				//rs = Integer.valueOf(instrucao.substring(6,11));
				//rt = Integer.valueOf(instrucao.substring(12,17));
				//rd = Integer.valueOf(instrucao.substring(18,23));
				System.out.println("add " + rs + " "+ rt+" "+rd);
				ula.sub(rs,rt,rd);
				break;
				
			case "sll":
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