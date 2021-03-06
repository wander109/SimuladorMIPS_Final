package Memoria;

import java.util.HashMap;
import java.util.TreeMap;

public class Registro {
	private HashMap<String, String> registradores;
	
	public Registro(TreeMap<String, String> dicionarioRegistradores) {
		registradores = new HashMap<String, String>();
		for(int i = 0; i < dicionarioRegistradores.size(); i++)
			registradores.put((String)dicionarioRegistradores.keySet().toArray()[i], "00000000000000000000000000000000");
	}

	public String getValorRegistrador(String id) {
	
		return registradores.get(id);
	}

	public void setValorRegistrador(String id, String valor) {
		this.registradores.put(id, valor);
	}
	
/*	public void atualizaRegistrador(String id, int valor){
		int valorAtual = registradores.get(id);
		int novoValor = valorAtual + valor;
		
		registradores.put(id, novoValor);
	} 
	*/
	
}
