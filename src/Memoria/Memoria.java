package Memoria;

import java.util.ArrayList;

public class Memoria {
	private ArrayList<String> codigoFonteBinario;
	
	public Memoria(){
		codigoFonteBinario = new ArrayList<String>();
	}
	public ArrayList<String> getCodigoFonteBinario() {
		return codigoFonteBinario;
	}

	public void setCodigoFonteBinario(ArrayList<String> codigoFonteBinario) {
		this.codigoFonteBinario = codigoFonteBinario;
	}
	
	public void imprimeCodigoFonteBinario(){
		for(int i = 0; i < codigoFonteBinario.size(); i++)
			System.out.println(codigoFonteBinario.get(i));
	}
	
	@Override
	public String toString() {
		for(int i = 0; i < codigoFonteBinario.size(); i++)
			System.out.println(Integer.toString(i, 16) + "	" + codigoFonteBinario.get(i));
		return super.toString();
	}
}
