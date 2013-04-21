package Memoria;

import java.util.HashMap;

public class Registro {
	private HashMap<String, Integer> registradores;
	
	public Registro(HashMap<String, String> dicionarioRegistradores) {
		registradores = new HashMap<String, Integer>();
		for(int i = 0; i < dicionarioRegistradores.size(); i++)
			registradores.put((String)dicionarioRegistradores.keySet().toArray()[i], 0);
	}

	public Integer getValorRegistrador(String id) {
		return registradores.get(id);
	}

	public void setValorRegistrador(String id, int valor) {
		this.registradores.put(id, valor);
	}
	
	public void atualizaRegistrador(String id, int valor){
		int valorAtual = registradores.get(id);
		int novoValor = valorAtual + valor;
		
		registradores.put(id, novoValor);
	}
	
}
