package Processador;

import java.util.ArrayList;
import java.util.TreeMap;

import ManipulacaoArquivo.TrataArquivo;

public class Montador {
	/* Dada um codigo fonte, o montador deve ser capaz de traduzi-lo para binario
	 * a fim da Unidade de Controle interpretar os comandos que serao executados pela
	 * Unidade Logica Aritmetica ou por ela mesma
	 */
	private ArrayList<String> codigoFonte;
	private TreeMap<String, String> dicionarioRegistradores;
	private TreeMap<String, String> dicionarioInstrucoes;
	private TreeMap<String, String> dicionarioInstrucoesRFormat;
	private TreeMap<String, String> dicionarioInstrucoesTipo;
	private TreeMap<String, Integer> dicionarioLabels;
	
	public Montador(ArrayList<String> codigoFonte,
					TreeMap<String, String> dicionarioRegistradores,
					TreeMap<String, String> dicionarioInstrucoes,
					TreeMap<String, String> dicionarioInstrucoesRFormat,
					TreeMap<String, String> dicionarioInstrucoesTipo,
					TreeMap<String, Integer> dicionarioLabels){
		
		this.codigoFonte = codigoFonte;
		this.dicionarioInstrucoes = dicionarioInstrucoes;
		this.dicionarioRegistradores = dicionarioRegistradores;
		this.dicionarioInstrucoesRFormat = dicionarioInstrucoesRFormat;
		this.dicionarioInstrucoesTipo = dicionarioInstrucoesTipo;
		this.dicionarioLabels = dicionarioLabels;
	

	}
	
	//Metodo inutil, e possivel imprimir o TreeMap simplesmente dando um print(<TreeMap>)
	public void imprimeDicionario(TreeMap<String, String> dicionario){
		//for (int i = 0; i < dicionario.size(); i++)
			System.out.print(dicionario.values());
	}
	
	public ArrayList<String> traduzir(){
		ArrayList<String> codigoFonteBin = new ArrayList<String>();
		String[] linha;
		
		for(int i = 0; i < codigoFonte.size()-1; i++){
			linha = codigoFonte.get(i).split(" ");
			if (dicionarioInstrucoesTipo.get(linha[0]).equals("R")) {
				codigoFonteBin.add(traduzR(linha[1], linha[0]));
			} 
			else if  (dicionarioInstrucoesTipo.get(linha[0]).equals("I")) {
				codigoFonteBin.add(traduzI(linha[1], linha[0]));
			}
			else if  (dicionarioInstrucoesTipo.get(linha[0]).equals("J")) {
				codigoFonteBin.add(traduzJ(linha[1], linha[0]));
			}
		}
		
		return codigoFonteBin;
	}
	
	private String traduzJ(String parametros, String funcao){
		String op, rs, adress;
		String codigoBinario;
		
		op = dicionarioInstrucoes.get(funcao);
		
		if (dicionarioLabels.get(parametros) != null){
				
			adress = getBinario(dicionarioLabels.get(parametros), 26);	
		}
		else{
			adress = getBinario(Integer.parseInt(parametros), 26);
		}
		
		codigoBinario = op + adress; 
		return codigoBinario;
	}
		
	private String traduzI(String parametros, String funcao){
		String op, rs, rt, immediate;
		String codigoBinario;
		
		op = dicionarioInstrucoes.get(funcao);
		rs = "00000"; rt = "00000";
		immediate = "0";
		
		String[] parametrosAux = parametros.split(",");
			
		 if((funcao.equals("lw")) || (funcao.equals("sw"))){
				rt = dicionarioRegistradores.get(parametrosAux[0]);
				String[] aux = parametrosAux[1].split("(?=\\()|(?<=\\))"); 
				immediate = getBinario(Integer.parseInt(aux[0]), 16);
				rs = dicionarioRegistradores.get(aux[1].substring(1, aux[1].length()-1));
		 }
		 else if (funcao.equals("beq") || funcao.equals("bne")){ //falta implementar a limpeza do código-Fonte para com isso fazer rastreamento dos rótulos
			    rt = dicionarioRegistradores.get(parametrosAux[1]); 
			    rs = dicionarioRegistradores.get(parametrosAux[0]);
				immediate = getBinario(dicionarioLabels.get(parametrosAux[2]), 16);
		 }
		 
		 else{
			rs = dicionarioRegistradores.get(parametrosAux[0]);
			rt = dicionarioRegistradores.get(parametrosAux[1]);
			immediate = getBinario(Integer.parseInt(parametrosAux[2]), 16);
		}
			
		codigoBinario = op + rs + rt + immediate; 
		return codigoBinario;
	}
		
	private String traduzR(String parametros, String funcao){
		String op, rs, rt, rd, shamt, func;
		String codigoBinario;
		
		op = "000000";
		rs = "00000"; rt = "00000"; rd = "00000";
		shamt = "00000";
		func = dicionarioInstrucoesRFormat.get(funcao);
		
		String[] parametrosAux = parametros.split(",");
		
		if(funcao.equals("jr")){
			rs = dicionarioRegistradores.get(parametros);
		}else if(funcao.equals("sll") || funcao.equals("srl")){
			//implementa o preenchimento do rs rt e rd da forma correta e seta o shamt
			//por default o rt e zerado
			
			rt = dicionarioRegistradores.get(parametrosAux[0]);
			rd = dicionarioRegistradores.get(parametrosAux[1]);
			shamt = getBinario(Integer.valueOf(parametrosAux[2]), 5);
		}else{
			//Caso seja uma traducao de add, sub, slt, sgt
			rd = dicionarioRegistradores.get(parametrosAux[0]);
			rs = dicionarioRegistradores.get(parametrosAux[1]);
			rt = dicionarioRegistradores.get(parametrosAux[2]);
		}
		
		codigoBinario = op + rs + rt + rd + shamt + func; 
		return codigoBinario;
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

	public ArrayList<String> getCodigoFonte() {
		return codigoFonte;
	}

	public TreeMap<String, String> getDicionarioRegistradores() {
		return dicionarioRegistradores;
	}

	public TreeMap<String, String> getDicionarioInstrucoes() {
		return dicionarioInstrucoes;
	}

	public TreeMap<String, String> getDicionarioInstrucoesRFormat() {
		return dicionarioInstrucoesRFormat;
	}

	public TreeMap<String, String> getDicionarioInstrucoesTipo() {
		return dicionarioInstrucoesTipo;
	}

	public TreeMap<String, Integer> getDicionarioLabels() {
		return dicionarioLabels;
	}
	
	
}
