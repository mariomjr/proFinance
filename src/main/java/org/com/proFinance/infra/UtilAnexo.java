package org.com.proFinance.infra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.com.proFinance.entity.Anexo;

public class UtilAnexo  {

	public static final String caminhoAnexo= insertSeparator("C:","AnexosProFinance");
	
	public static Path diretorio;
	
	private static Path anexoPath;
		
	public static void inserirArquivo(Anexo anexo) throws IOException{
		
		String nomeCaminho = String.valueOf(anexo.getId());
		if(isPastaCriada(nomeCaminho)){
			diretorio = Paths.get(insertSeparator(caminhoAnexo,nomeCaminho));
			anexoPath = diretorio.resolve(anexo.getNomeArquivo());
			Files.write(anexoPath, anexo.getAnexo());
		}
	}
	
	public static void inserirArquivo(List<Anexo>arquivos) throws IOException{
		if(arquivos != null){
			for(Anexo anexo : arquivos){
				inserirArquivo(anexo);
			}
		}
	}
	
	public static void buscaArquivo(Anexo anexo) throws IOException{
		
		diretorio =  Paths.get(insertSeparator(caminhoAnexo,anexo.getId()));
		anexoPath = diretorio.resolve(anexo.getNomeArquivo());

		byte[]arq = Files.readAllBytes(anexoPath);
		anexo.setAnexo(arq);

	}
	
	public static void buscaArquivo(List<Anexo> listAnexos) throws IOException{
		if(listAnexos!= null){
			for(Anexo anexoAux : listAnexos){
				buscaArquivo(anexoAux);
			}
		}
	}

	public static boolean isPastaCriada(String nomePasta){
		
		Path pastaDiretorio = Paths.get(insertSeparator(caminhoAnexo,nomePasta));   
		try{
			if(Files.exists(pastaDiretorio) == false){
				Files.createDirectory(pastaDiretorio);
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static String insertSeparator(Object... campos){
		
		String strReturn = "";
		for(Object campo: campos){
			strReturn += File.separator;
			strReturn += campo;
		}
		return strReturn;
		
	}

}
