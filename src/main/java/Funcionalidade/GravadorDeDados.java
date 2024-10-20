package Funcionalidade;

import Modelo.Combos;
import Modelo.Cupcake;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GravadorDeDados {
    public static final String ARQUIVO_DE_CUPCAKES = "cupcakes.dat";
    public static final String ARQUIVO_DE_COMBOS = "combos.dat";


    public HashMap<String, Cupcake> recuperarCupcakes() throws IOException{
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream(ARQUIVO_DE_CUPCAKES));
            return (HashMap<String, Cupcake>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de cupcakes não encontrado. Iniciando com lista vazia.");
            return new HashMap<>();
        } catch (Exception e) {
            System.out.println("Não foi possível recuperar os dados dos cupcakes");
            throw new IOException("Não foi possível recuperar os dados do arquivo " + ARQUIVO_DE_CUPCAKES);
        } finally {
            if (in!=null){
                in.close();
            }
        }
    }

    public HashMap<String, Combos> recuperarCombos() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(ARQUIVO_DE_COMBOS));
            return (HashMap<String, Combos>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de combos não encontrado. Iniciando com lista vazia.");
            return new HashMap<>(); // Retorna um HashMap vazio se o arquivo não for encontrado
        } catch (Exception e) {
            System.out.println("Não foi possível recuperar os dados dos combos");
            throw new IOException("Não foi possível recuperar os dados do arquivo " + ARQUIVO_DE_COMBOS);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void salvarCupcakes(Map<String, Cupcake> cupecakes) throws IOException{
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DE_CUPCAKES));
            out.writeObject(cupecakes);
        } catch (Exception e){
            e.printStackTrace();
            throw new IOException("Erro ao salvar os cupcakes no arquivo " + ARQUIVO_DE_CUPCAKES);
        } finally {
            if (out != null){
                out.close();
            }
        }
    }

    public void salvarCombos(Map<String, Combos> combos) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DE_COMBOS));
            out.writeObject(combos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao salvar os combos no arquivo " + ARQUIVO_DE_COMBOS);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
