package Principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import Janela.JanelaPrincipal;
import ManipulacaoArquivo.TrataArquivo;
import Memoria.Memoria;
import Memoria.Registro;
import Processador.Montador;
import Processador.UCP;

public class Main {
	public static void main(String[] args) throws IOException {
		TrataArquivo leitorDeTraducoes;
		ArrayList<String> codigoFonte = new ArrayList<String>();
		TreeMap<String, String> dicionarioInstrucoes = new TreeMap<String, String>();
		TreeMap<String, String> dicionarioRegistradores = new TreeMap<String, String>();
		TreeMap<String, String> dicionarioInstrucoesTipo = new TreeMap<String, String>();
		TreeMap<String, String> dicionarioInstrucoesRFormat = new TreeMap<String, String>();
		TreeMap<String, Integer> dicionarioLabels = new TreeMap<String, Integer>();
		
		UCP ucp = new UCP("Traducoes/InstrucoesRFormatBIN.txt", "Traducoes/InstrucoesBIN.txt");
		Memoria memoria = new Memoria();
		
		
	
		TrataArquivo arquivoCodigoFonte = new TrataArquivo("Programas/teste00.txt");
		codigoFonte = arquivoCodigoFonte.getArquivoSalvo();
		
		//Manipulador de arquivo para ler o "dicionario" da traducao das instrucoes
		leitorDeTraducoes = new TrataArquivo("Traducoes/Instrucoes.txt");
		dicionarioInstrucoes = leitorDeTraducoes.carregaMapaDeTraducao();
		
		leitorDeTraducoes = new TrataArquivo("Traducoes/Registradores.txt");
		dicionarioRegistradores = leitorDeTraducoes.carregaMapaDeTraducao();
		
		
		leitorDeTraducoes = new TrataArquivo("Traducoes/InstrucoesTipo.txt");
		dicionarioInstrucoesTipo = leitorDeTraducoes.carregaMapaDeTraducao();
		
		leitorDeTraducoes = new TrataArquivo("Traducoes/InstrucoesRFormat.txt");
		dicionarioInstrucoesRFormat = leitorDeTraducoes.carregaMapaDeTraducao();
		
		dicionarioLabels = leitorDeTraducoes.getDicionarioLabels(codigoFonte);
		
		Montador interpretador = new Montador(codigoFonte,
											  dicionarioRegistradores,
											  dicionarioInstrucoes,
											  dicionarioInstrucoesRFormat,
											  dicionarioInstrucoesTipo,
											  dicionarioLabels);
		
		memoria.setCodigoFonteBinario(interpretador.traduzir());
		System.out.println(memoria);
		//memoria.imprimeCodigoFonteBinario();
		
		//ucp.lerInterpretarInstrucao("00000010000100010100000000100000");
		//Registro registro = new Registro(dicionarioRegistradores);
		//JanelaPrincipal jp = new JanelaPrincipal();
		
	}

}
