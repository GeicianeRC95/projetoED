package projetoEstrutura;

import java.util.*;

public class Categorias {
    int ID;
    String nome;
    List<String> palavraChave;
    int idPai;
    
    public Categorias(int ID, String nome, String palavra, int idPai) {
        this.ID = ID;
        this.nome = nome;
        this.idPai = idPai;
        this.palavraChave = new ArrayList<>();
        
        if (palavra != null && !palavra.isEmpty()) { 
            String[] separar = palavra.split(",");
            for (String s : separar) {
                this.palavraChave.add(s.trim());
            }
        }
    }
}
