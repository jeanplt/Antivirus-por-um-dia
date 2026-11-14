import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Tabuleiro {
    private Setor[][] map; 
    private int fonteInfecX; 
    private int fonteInfecY; 

    // Construtor do tabuleiro com matriz 5x5 de Setores
    public Tabuleiro() {
        map = new Setor[5][5];
    }

    // Get do setor no tabuleiro pelas coordenadas
    public Setor getSetor(int x, int y) {
        return map[x][y];
    }

    // Get da coordenada X da fonte de infecção
    public int getFonteInfecX() {
        return this.fonteInfecX;
    }

    // Get da coordenada Y da fonte de infecção
    public int getFonteInfecY() {
        return this.fonteInfecY;
    }

    // Método gerador do tabuleiro
    public void iniciarTabuleiro() {
        boolean mapaValido = false;
        // Laço para gerar as paredes do mapa, posicionar a infecção e verificar se é possível chegar até ela a partir do centro
        while (!mapaValido) {
            gerarEstruturaAleatoria();
            posicionarFonteInfeccao();
            mapaValido = validarCaminhoComBFS(2, 2, fonteInfecX, fonteInfecY);
        }
    }

    // Método para estrutura aleatória do mapa
    private void gerarEstruturaAleatoria() {
        Random aleatorio = new Random();
        // Mapeamento dos Setores: 
        //      0: Normal 
        //      1: Oculto 
        //      2: Privado
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) { // Garante que o centro é setor Normal e visitado
                    map[i][j] = new SetorNormal(i, j);
                    map[i][j].setVisitado(true);
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
        // Novo laço agora para definir as portas para os setores
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i > 0) map[i][j].setPorta(0, aleatorio.nextBoolean()); // Norte (0)
                if (i < 4) map[i][j].setPorta(1, aleatorio.nextBoolean()); // Sul (1)
                if (j < 4) map[i][j].setPorta(2, aleatorio.nextBoolean()); // Leste (2)
                if (j > 0) map[i][j].setPorta(3, aleatorio.nextBoolean()); // Oeste (3)
                // Sincroniza portas adjacentes 
                if (i > 0) map[i-1][j].setPorta(1, map[i][j].getPortas()[0]);
                if (j > 0) map[i][j-1].setPorta(2, map[i][j].getPortas()[3]);
            }
        }
        // Força existir 4 portas no centro
        map[2][2].setPorta(0, true); 
        map[2][2].setPorta(1, true); 
        map[2][2].setPorta(2, true); 
        map[2][2].setPorta(3, true); 
        // Força existir 4 portas adjacentes ao centro
        map[1][2].setPorta(1, true); // De cima
        map[2][3].setPorta(3, true); // Direita
        map[3][2].setPorta(0, true); // De baixo
        map[2][1].setPorta(2, true); // Esquerda
    }

    // Método de posicionamento da fonte de infecção
    private void posicionarFonteInfeccao() {
        Random aleatorio = new Random();
        do { // Só garante que a fonte de infecção não fique no centro
            fonteInfecX = aleatorio.nextInt(5);
            fonteInfecY = aleatorio.nextInt(5);
        } while (fonteInfecX == 2 && fonteInfecY == 2); 
    }

    // Algoritmo de Busca em Largura
    private boolean validarCaminhoComBFS(int startX, int startY, int targetX, int targetY) {
        // Matriz auxiliar para marcar por onde o algoritmo já passou e evitar loops infinitos
        boolean[][] visitados = new boolean[5][5];
        Queue<int[]> fila = new LinkedList<>();
        // Inicia a busca pela coordenada de origem [2, 2]
        fila.add(new int[]{startX, startY});
        visitados[startX][startY] = true;
        // Vetores de movimento mapeados para sua ordem: 0=Norte, 1=Sul, 2=Leste, 3=Oeste
        int[] movX = {-1, 1, 0, 0}; 
        int[] movY = {0, 0, 1, -1};
        // Laço que valida o caminho
        while (!fila.isEmpty()) {
            int[] atual = fila.poll();
            int currX = atual[0];
            int currY = atual[1];
            // Se a posição atual for igual a da infecção, o caminho é válido!
            if (currX == targetX && currY == targetY) {
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
                        fila.add(new int[]{nextX, nextY});
                    }
                }
            }
        }
        // Se a fila esvaziar e não achar o X, tem que recriar o tabuleiro
        return false;
    }

    // Método para imprimir o tabuleiro
    public void exibirTabuleiro(JogadorSimples p1, JogadorSuporte p2) {
        // Tabela para impressão do mapa
        System.out.println("\n--- Mapa do Sistema ---");
        System.out.println("    1     2     3     4     5");
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 5; j++) {
                // Imprime jogadores no mapa
                if ((p1.getPosX() == i && p1.getPosY() == j) && (p2.getPosX() == i && p2.getPosY() == j)) {
                    System.out.print("[ P ] "); 
                } else if ((p1.getPosX() == i && p1.getPosY() == j)) {
                    System.out.print("[P1 ] "); 
                } else if ((p2.getPosX() == i && p2.getPosY() == j) && (p2.getDef() > 0)) {
                    System.out.print("[P2 ] "); 
                } else if (i == 2 && j == 2) {
                    System.out.print("[ C ] "); 
                } /* else if (i == fonteInfecX && j == fonteInfecY) {
                    System.out.print("[ X ] "); 
                } */ else {
                    System.out.print("[   ] ");
                }
            }
            System.out.println(); 
        }
    }

    // Método para imprimir portas disponíveis no setor que o player está
    public void exibirPortasDisponiveis(Jogador player) {
        // Mostra as portas para Player pode se movimentar durante seu turno
        Setor setorAtual = map[player.getPosX()][player.getPosY()];
        System.out.print("Portas disponíveis: ");
        if (setorAtual.getPortas()[0]) System.out.print("Norte(W) ");
        if (setorAtual.getPortas()[1]) System.out.print("Sul(S) ");
        if (setorAtual.getPortas()[2]) System.out.print("Leste(D) ");
        if (setorAtual.getPortas()[3]) System.out.print("Oeste(A) ");
    }

    // Método para mostrar atk e def do player
    public void exibirStatusdoPlayer(Jogador player) {
        System.out.println("\nAtaque/Defesa do Personagem: " + (player.getAtk()) + "/" + (player.getDef()));
    }

    // Método para mostrar regras do setor que o player está
    public void exibirDetalhesdoSetor(Jogador player) {
        Setor setorAtual = map[player.getPosX()][player.getPosY()];
        System.out.println("\n--- Detalhes do Setor [" + (player.getPosX() + 1) + "," + (player.getPosY() + 1) + "] ---");
        setorAtual.aplicarRegraSetor(player);
    }

    // Método para mostrar quais inimigos estão no setor e seus status
    public void exibirInimigosdoSetor(Jogador player) {
        // Imprime os inimigos do setor se eles existirem
        Setor setorAtual = map[player.getPosX()][player.getPosY()];
        int i = setorAtual.getInimigos().size();
        if (i == 0) 
            return;
        else {
            System.out.println("Vírus no local: " + setorAtual.getInimigos().size());
            for (int k = 0; k < setorAtual.getInimigos().size(); k++) {
                Inimigo ini = setorAtual.getInimigos().get(k);
                System.out.println("Vírus " + (k+1) + " -> ATK: " + ini.getAtk() + " | DEF: " + ini.getDef());
            }
        }
    }
}