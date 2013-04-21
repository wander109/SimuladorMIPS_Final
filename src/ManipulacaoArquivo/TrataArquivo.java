package ManipulacaoArquivo;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class TrataArquivo {
	private File F;
	private FileReader fr;
	private BufferedReader br;
	private JFileChooser arquivo;
	private ArrayList<String> arquivoSalvo;
	
	//Abre arquivo a partir de uma janela
	public TrataArquivo() {
		arquivoSalvo = new ArrayList<String>();
		arquivo = new JFileChooser("Programas/");
	}
	
	//Abre arquivo a partir de um caminho fixo
	public TrataArquivo(String caminho){
		F = new File(caminho);
		arquivoSalvo = new ArrayList<String>();
		
		try {
			fr = new FileReader(F);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
		
		try {
			arquivoSalvo = getConteudoArquivo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void imprimeConteudo(){
		for(int i = 0; i < arquivoSalvo.size(); i++)
			System.out.println(arquivoSalvo.get(i));
	}
	
	//Retorna um ArrayList com todas as linhas do arquivo
	private ArrayList<String> getConteudoArquivo() throws IOException{
		ArrayList<String> arquivoTxt = new ArrayList<String>();
		
		String proxLinha = br.readLine();
		while (proxLinha != null){
			arquivoTxt.add(proxLinha);
			proxLinha = br.readLine();
		}
		return arquivoTxt;
	}
	
	private void limpaCodigo(ArrayList<String> codigoFonte){
		String aux;
		for(int i = 0; i < codigoFonte.size(); i++){
			if(codigoFonte.get(i).split(" ").length > 2){
				aux = codigoFonte.get(i).split(" ", 2)[1];
				aux = aux.replaceAll(" ", "");
				aux = codigoFonte.get(i).split(" ", 2)[0] + " " + aux;
			}
		}
	}
	
	//Retorna um dicionario de labels a partir da limpeza do codigo fonte
	public TreeMap<String, Integer> getDicionarioLabels(ArrayList<String> codigoFonte){
		TreeMap<String, Integer> dicionarioLabels = new TreeMap<String, Integer>();
		
		for(int i = 0; i < codigoFonte.size(); i++){
			if(codigoFonte.get(i).contains(":")){
				String[] aux = codigoFonte.get(i).split(":");
				dicionarioLabels.put(aux[0], i);
				codigoFonte.set(i, aux[1]);
			}
		}
		return dicionarioLabels;
	}
	
	//Metodo utilizado para carregar arquivo quando utilizando o JFileChooser
	public void selecionarArquivo() throws FileNotFoundException{
		
		int retorno = arquivo.showOpenDialog(null);
		
		if(retorno == JFileChooser.APPROVE_OPTION){
			F = new File(arquivo.getSelectedFile().getAbsolutePath());
			fr = new FileReader(F);
			br = new BufferedReader(fr);
		}else{
			JOptionPane.showMessageDialog(null, "Nenhum arquivo aberto");
		}
		
		try {
			arquivoSalvo = getConteudoArquivo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Retorna uma HashMap com as traducoes lidas do arquivo passado por parametro no metodo construtor
	public TreeMap<String, String> carregaMapaDeTraducao() throws IOException{
		TreeMap<String, String> traducoes = new TreeMap<String, String>();

		for(int i = 0; i < arquivoSalvo.size(); i++){
			String[] aux = arquivoSalvo.get(i).split(" "); 
			traducoes.put(aux[0], aux[1]);
		}
		
		return traducoes;
	}
	
	public String getCaminhoArquivo(){
		return arquivo.getCurrentDirectory().getAbsolutePath() + "\\" + arquivo.getName(arquivo.getSelectedFile());
	}
	
	public ArrayList<String> getArquivoSalvo(){
		//limpaCodigo(arquivoSalvo);
		return arquivoSalvo;
	}
}
