{
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
        System.out.print("Digite a palavra-chave para busca (ex: Móveis ou Casa): ");
        String busca = sc.nextLine();

        List<List<String>> resultados = filtrarPorPalavraChave(busca);

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada para: " + busca);
        } else {
            for (List<String> res : resultados) {
                System.out.println(res);
            }
        }
        sc.close();
    }
    
    public static int nivel(int id) {
        int contador = 1; 
        Categorias atual = mapearCateg.get(id);
        while(atual != null && atual.idPai != -1) {
            contador++;
            atual = mapearCateg.get(atual.idPai);
        }
        return contador;
    }
    
    public static List<String> buscarPalavraC(int id){
        Categorias atual = mapearCateg.get(id);
        while(atual != null) {
            if(!atual.palavraChave.isEmpty()) {
                return atual.palavraChave;
            }
            atual = mapearCateg.get(atual.idPai);
        }
        return new ArrayList<>();
    }
    
    public static List<List<String>> filtrarPorPalavraChave(String termo) {
        List<List<String>> listaFinal = new ArrayList<>();

        for (Categorias c : mapearCateg.values()) {
            List<String> kws = buscarPalavraC(c.ID);

           
            boolean encontrou = false;
            for(String s : kws) {
                if(s.equalsIgnoreCase(termo)) {
                    encontrou = true;
                    break;
                }
            }

            if (encontrou) {
                List<String> item = new ArrayList<>();
                item.add(String.valueOf(c.ID));         
                item.add(String.valueOf(nivel(c.ID)));   
                item.add(c.nome);                       
                item.addAll(kws);                        
                listaFinal.add(item);
            }
        }
        return listaFinal;
    }
}
