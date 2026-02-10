package projetoEstrutura;

import java.util.*;

public class Categorias {
    int ID;
    String nome;
    List<String> palavraChave;
    int parentID;
    
    public Categorias(int ID, String nome, String palavra, int parentID) {
        this.ID = ID;
        this.nome = nome;
        this.parentID = parentID;
        this.palavraChave = new ArrayList<>();
        
        if (palavra != null && !palavra.isEmpty()) { 
            String[] separar = palavra.split(",");
            for (String s : separar) {
                this.palavraChave.add(s.trim());
            }
        }
    }
}