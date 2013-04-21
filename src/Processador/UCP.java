package Processador;

import java.io.IOException;
import java.util.TreeMap;

import ManipulacaoArquivo.TrataArquivo;

public class UCP {
	
	private ULA ula; //Pra chamar os métodos direto pelo objeto
	private TreeMap<String, String> dicionarioInstrucoes = new TreeMap<String, String>();
	private TreeMap<String, String> dicionarioInstrucoesRFormat = new TreeMap<String, String>(); 
	
	private int rs;
	private int rd;
	private int rt;
	private int address;
	private int constant;
	private int offset;
	private int shamt;
	private String operacao;
	private String funcao;
	
	public UCP(String arqRFormat, String arqInstrucoes){	
		try {
			dicionarioInstrucoesRFormat = montaHash(arqRFormat);
			dicionarioInstrucoes = montaHash(arqInstrucoes);
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
	
	
	
	public void lerInterpretarInstrucao(String instrucao) throws IOException{
		
		String op = instrucao.substring(0,6); 
		if(op.equals("000000")){ 
			
			funcao = String.valueOf(instrucao.subSequence(26,32));  
			operacao = dicionarioInstrucoesRFormat.get(funcao);
			switch (operacao) {
			case "add":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				rd = Integer.valueOf(instrucao.substring(18,23));
				System.out.println("add " + rs + " "+ rt+" "+rd);
				ula.add(rs,rt,rd);
				break;
			
			case "sub":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				rd = Integer.valueOf(instrucao.substring(18,23));
				System.out.println("add " + rs + " "+ rt+" "+rd);
				ula.sub(rs,rt,rd);
				break;
				
			case "sll":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				shamt = converteDecimal(instrucao.substring(18,23));
				//ula.sll(rs,rt,shamt);
				break;
			case "srl":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				shamt = converteDecimal(instrucao.substring(18,23));
				//ula.srl(rs,rt,shamt);
				break;
			case "jr":
				//ula.srl(rs,rt,shamt);
				break;
			default:
				break;
			}
		} else{
			operacao = dicionarioInstrucoes.get(op);
			switch (operacao) {
			case "addi":
				rs = Integer.valueOf(instrucao.substring(6,11));
				rt = Integer.valueOf(instrucao.substring(12,17));
				address =  converteDecimal(instrucao.substring(18,31));
				//ula.addi(rs,rt,address);
				break;
				
			case "j":
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
				break;
			}
			
		
		}
	}
}