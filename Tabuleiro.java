import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Tabuleiro {
    private Setor[][] map; 
    private int fonteInfecX; 
    private int fonteInfecY; 

    public Tabuleiro() {
        map = new Setor[5][5];
    }

    public void iniciarTabuleiro() {
        boolean mapaValido = false;
        
        while (!mapaValido) {
            gerarEstruturaAleatoria();
            posicionarFonteDeInfeccao();
            mapaValido = validarCaminhoComBFS(2, 2, fonteInfecX, fonteInfecY);
        }
    }

    private void gerarEstruturaAleatoria() {
        Random aleatorio = new Random();
        // 1. Cria os setores 0: Normal, 1: Oculto, 2: Privado
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) {
                    map[i][j] = new SetorNormal(i, j);
                } else {    
                    int tipoSetor = aleatorio.nextInt(3);
                    switch (tipoSetor) {
                        case 0:
                            map[i][j] = new SetorNormal(i, j);
                            break;
                        case 1:
                            map[i][j] = new SetorOculto(i, j);
                            break;
                        case 2:
                            map[i][j] = new SetorPrivado(i, j);
                            break;
                    }
                }
            }
        }
        // 2. Define portas aleatórias e faz a sincronização delas
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // Norte (0)
                if (i > 0) map[i][j].setPorta(0, aleatorio.nextBoolean());
                // Sul (1)
                if (i < 4) map[i][j].setPorta(1, aleatorio.nextBoolean());
                // Leste (2)
                if (j < 4) map[i][j].setPorta(2, aleatorio.nextBoolean());
                // Oeste (3)
                if (j > 0) map[i][j].setPorta(3, aleatorio.nextBoolean());
                // Sincronizar portas adjacentes para consistência arquitetural
                if (i > 0) map[i-1][j].setPorta(1, map[i][j].getPortas()[0]);
                if (j > 0) map[i][j-1].setPorta(2, map[i][j].getPortas()[3]);
            }
        }

        // 3. Abre as 4 portas do centro
        map[2][2].setPorta(0, true); // Norte
        map[2][2].setPorta(1, true); // Sul
        map[2][2].setPorta(2, true); // Leste
        map[2][2].setPorta(3, true); // Oeste

        // 4. Abre portas adjacentes ao centro 
        map[1][2].setPorta(1, true); // De cima
        map[2][3].setPorta(3, true); // Direita
        map[3][2].setPorta(0, true); // De baixo
        map[2][1].setPorta(2, true); // Esquerda
    }

    private void posicionarFonteDeInfeccao() {
        Random aleatorio = new Random();
        // 1. Só garante que a fonte de infecção não fique no centro
        do {
            fonteInfecX = aleatorio.nextInt(5);
            fonteInfecY = aleatorio.nextInt(5);
        } while (fonteInfecX == 2 && fonteInfecY == 2); 
    }

    private boolean validarCaminhoComBFS(int startX, int startY, int targetX, int targetY) {
        // Matriz auxiliar para marcar por onde o algoritmo já passou e evitar loops infinitos
        boolean[][] visitados = new boolean[5][5];
        Queue<int[]> fila = new LinkedList<>();

        /* DEBUG
        int[][][] pai = new int[5][5][2]; 
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pai[i][j][0] = -1; // Inicializa vazio
                pai[i][j][1] = -1;
            }
        }   
        */


        // Inicia a busca pela coordenada de origem [2, 2]
        fila.add(new int[]{startX, startY});
        visitados[startX][startY] = true;

        // Vetores de movimento mapeados para sua ordem: 0=Norte, 1=Sul, 2=Leste, 3=Oeste
        // Movimentos em X (linhas) e Y (colunas)
        int[] movX = {-1, 1, 0, 0}; 
        int[] movY = {0, 0, 1, -1};

        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int currX = atual[0];
            int currY = atual[1];

            // Se a posição atual for igual a da infecção, o caminho é válido!
            if (currX == targetX && currY == targetY) {
                /* DEBUG
                java.util.ArrayList<String> caminho = new java.util.ArrayList<>();
                int pX = targetX;
                int pY = targetY;
            
                // Retorna o caminho
                while (pX != startX || pY != startY) {
                    caminho.add("[" + pX + "," + pY + "]");
                    int tempX = pai[pX][pY][0];
                    int tempY = pai[pX][pY][1];
                    pX = tempX;
                    pY = tempY;
                }
                caminho.add("[" + startX + "," + startY + "]"); // Adiciona o centro
            
                java.util.Collections.reverse(caminho); // Inverte para mostrar do início ao fim
            
                System.out.println("Caminho validado p/ Infecção: " + String.join(" -> ", caminho));
                */
                return true;
            }

            Setor setorAtual = map[currX][currY];

            // Testa as 4 direções ao redor do setor atual
            for (int dir = 0; dir < 4; dir++) {
                
                // Verifica se a porta nesta direção (dir) é 'true' (aberta)
                if (setorAtual.getPortas()[dir]) {
                    int nextX = currX + movX[dir];
                    int nextY = currY + movY[dir];

                    // Garante que o vizinho não sai do tabuleiro (0 a 4) e ainda não foi visitado
                    if (nextX >= 0 && nextX < 5 && nextY >= 0 && nextY < 5 && !visitados[nextX][nextY]) {
                        visitados[nextX][nextY] = true;
                        /* DEBUG
                        pai[nextX][nextY][0] = currX;
                        pai[nextX][nextY][1] = currY;
                        */
                        fila.add(new int[]{nextX, nextY});
                    }
                }
            }
        }
    
        // Se a fila esvaziar e não achar o X, significa que o labirinto não tem saída
        return false;
    }

    public void exibirTabuleiro(JogadorSimples p1, JogadorSuporte p2) {
        // 1. Tabela para impressão do mapa
        System.out.println("\n--- Mapa do Sistema ---");
        System.out.println("\n  1     2     3     4     5");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // 2. Imprime jogadores no mapa, acho que é pra tirar a fonte depois
                if ((p1.getPosX() == i && p1.getPosY() == j) && (p2.getPosX() == i && p2.getPosY() == j)) {
                    System.out.print("[ P ] "); 
                } else if ((p1.getPosX() == i && p1.getPosY() == j)) {
                    System.out.print("[P1 ] "); 
                } else if ((p2.getPosX() == i && p2.getPosY() == j)) {
                    System.out.print("[P2 ] "); 
                } else if (i == 2 && j == 2) {
                    System.out.print("[ C ] "); 
                } else if (i == fonteInfecX && j == fonteInfecY) {
                    System.out.print("[ X ] "); 
                } else {
                    System.out.print("[   ] ");
                }
            }
            System.out.println(); 
        }
        /* 
        // 3. Imprime os dados do Setor Atual em texto corrido
        Setor setorAtual = map[p1.getPosX()][p1.getPosY()];
        
        System.out.println("\n--- Detalhes do Setor [" + (p1.getPosX() + 1) + "," + (p1.getPosY() + 1) + "] ---");
        if (setorAtual instanceof SetorOculto) {
            System.out.println("Este é um Setor Oculto. Seus ataques podem falhar.");
        } else if (setorAtual instanceof SetorPrivado) {
            System.out.println("Este é um Setor Privado. A ação de procurar itens está bloqueada.");
        } else {
            System.out.println("Este é um Setor Normal. Sem restrições especiais.");
        }

        System.out.print("Portas disponíveis: ");
        if (setorAtual.getPortas()[0]) System.out.print("Norte(W) ");
        if (setorAtual.getPortas()[1]) System.out.print("Sul(S) ");
        if (setorAtual.getPortas()[2]) System.out.print("Leste(D) ");
        if (setorAtual.getPortas()[3]) System.out.print("Oeste(A) ");
    
        System.out.println("\nInimigos no local: " + setorAtual.getInimigos().size());
        for (int k = 0; k < setorAtual.getInimigos().size(); k++) {
            Inimigo ini = setorAtual.getInimigos().get(k);
            System.out.println("Vírus " + (k+1) + " -> ATK: " + ini.getAtk() + " | DEF: " + ini.getDef());
        }
        */
    }

    public void exibirDetalhesdoSetor(TipodePersonagem player) {
        // 3. Imprime os dados do Setor Atual em texto corrido
        Setor setorAtual = map[player.getPosX()][player.getPosY()];
        
        System.out.println("\n--- Detalhes do Setor [" + (player.getPosX() + 1) + "," + (player.getPosY() + 1) + "] ---");
        if (setorAtual instanceof SetorOculto) {
            System.out.println("Este é um Setor Oculto. Seus ataques podem falhar.");
        } else if (setorAtual instanceof SetorPrivado) {
            System.out.println("Este é um Setor Privado. A ação de procurar itens está bloqueada.");
        } else {
            System.out.println("Este é um Setor Normal. Sem restrições especiais.");
        }

        System.out.print("Portas disponíveis: ");
        if (setorAtual.getPortas()[0]) System.out.print("Norte(W) ");
        if (setorAtual.getPortas()[1]) System.out.print("Sul(S) ");
        if (setorAtual.getPortas()[2]) System.out.print("Leste(D) ");
        if (setorAtual.getPortas()[3]) System.out.print("Oeste(A) ");
    
        System.out.println("\nInimigos no local: " + setorAtual.getInimigos().size());
        for (int k = 0; k < setorAtual.getInimigos().size(); k++) {
            Inimigo ini = setorAtual.getInimigos().get(k);
            System.out.println("Vírus " + (k+1) + " -> ATK: " + ini.getAtk() + " | DEF: " + ini.getDef());
        }
    }
}