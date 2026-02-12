package projetoEstrutura;

import java.util.*;

public class Main {
    static Map<Integer, Categorias> mapearCateg = new HashMap<>();

    public static void main(String[] args) {
        Object[][] dataset = {
            {1, "Raiz", "Produtos", -1},
            {2, "Móveis", "Móveis", 1},
            {3, "Eletrônicos", "Eletrônicos, Gadgets", 1},
            {4, "Casa e Eletrodomésticos", "Casa, Eletrodomésticos", 1},
            {5, "Eletrodomésticos Principais", "", 4},
            {6, "Eletrodomésticos Secundários", "", 4},
            {7, "Gramado e Jardim", "Gramado, Jardim", 4},
            {8, "Eletrodomésticos de Cozinha", "", 5},
            {9, "Eletrodomésticos em Geral", "", 5}
        };

        for(Object[] registro : dataset) {
            int id = (int) registro[0];
            String nome = (String) registro[1];
            String palavra = (String) registro[2];
            int idPai = (int) registro[3]; 
            
            Categorias cat = new Categorias(id, nome, palavra, idPai);
            mapearCateg.put(id, cat);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Banco de dados pronto! Total: " + mapearCateg.size());
        
        int idInserido = -1;
        boolean idValido = false;
        while (!idValido) {
            System.out.print("\nInsira um ID correspondente a categoria: ");
            try {
                idInserido = sc.nextInt();
                sc.nextLine(); 

                if (mapearCateg.containsKey(idInserido)) {
                    System.out.println("Válido! Resultado da busca: " + consultar(idInserido));
                    idValido = true; 
                } else {
                    System.out.println("Inválido! O ID " + idInserido + " não existe no mapa.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite apenas NÚMEROS para o ID!");
                sc.nextLine(); 
            }
        }

        System.out.print("\nInsira uma palavra ou frase para filtragem: ");
        String busca = sc.nextLine();
        List<List<String>> encontrado = filtrar(busca);
        
        if(encontrado.isEmpty()) {
            System.out.println("Filtragem: [" + busca + "] => Nenhuma categoria encontrada");
        } else {
            System.out.println("Categorias encontradas: " + encontrado);
        }
        
        sc.close();
    }

    public static int nivel(int id) {
        int contador = 0;
        Categorias atual = mapearCateg.get(id);
        while(atual != null && atual.idPai != -1) {
            Categorias pai = mapearCateg.get(atual.idPai);
            if (pai == null) break; 
            
            contador++;
            atual = pai;
        }
        return contador;
    }

    public static List<String> buscarPalavraC(int id){
        Categorias atual = mapearCateg.get(id);
        while(atual != null) {
            if(!atual.palavraChave.isEmpty()) {
                return atual.palavraChave;
            }
            if (atual.idPai == -1 || !mapearCateg.containsKey(atual.idPai)) break; 
            
            atual = mapearCateg.get(atual.idPai);
        }
        return new ArrayList<>();
    }

    public static List<String> consultar(int id){
        List<String> devolver = new ArrayList<>();
        devolver.add(String.valueOf(nivel(id)));
        devolver.addAll(buscarPalavraC(id));
        return devolver;
    }

    public static List<List<String>> filtrar(String buscar){
        List<List<String>> resultado = new ArrayList<>();
        for(Categorias c : mapearCateg.values()) {
            List<String> palavraCateg = buscarPalavraC(c.ID);
  
            if(palavraCateg.toString().toLowerCase().contains(buscar.toLowerCase())) {
                resultado.add(consultar(c.ID));
            }
        }
        return resultado;
    }
}
