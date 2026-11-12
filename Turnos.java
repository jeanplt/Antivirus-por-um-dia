import java.util.Scanner;

public class Turnos {
    private int cicloAtual; 
    private JogadorSimples player1; 
    private JogadorSuporte player2; 
    private Tabuleiro tabuleiro;

    public Turnos() {
        this.cicloAtual = 0;
        this.player1 = new JogadorSimples();
        this.player2 = new JogadorSuporte();
        this.tabuleiro = new Tabuleiro();
    }

    public int getCicloAtual() {
        return this.cicloAtual;
    }

    public void iniciarJogo() {
        tabuleiro.iniciarTabuleiro();
        player1.setPosX(2); player1.setPosY(2);
        player2.setPosX(2); player2.setPosY(2);
    }

    public void carregarTurnodoPlayer1() { 
        // Carregamento do mapa, P1, Setor que ele está e Inimigos, caso existam
        Scanner scanner = new Scanner(System.in);
        tabuleiro.exibirTabuleiro(player1, player2);
        System.out.println("\u001B[32m" + "\n--- TURNO DO JOGADOR 1 (SIMPLES) ---" + "\u001B[0m");
        tabuleiro.exibirStatusdoPlayer(player1);
        tabuleiro.exibirDetalhesdoSetor(player1);
        tabuleiro.exibirInimigosdoSetor(player1); 

        // Parte inicial para se movimentar caso não tenha inimigos no Setor de P1
        Setor setorAtual = tabuleiro.getSetor(player1.getPosX(), player1.getPosY());
        if (setorAtual.getInimigos().isEmpty()) {
            tabuleiro.exiberPortasDisponiveis(player1);
            boolean movimentoValido = false;
            while (!movimentoValido) {
                char acaoP1 = scanner.next().charAt(0);
                if (acaoP1 == 'W' || acaoP1 == 'w') { // Movimento para o NORTE (x - 1)
                    if (!setorAtual.getPortas()[0]) { // Porta Sul é o índice 0
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(player1.getPosX() - 1, player1.getPosY());
                        player1.movimentar(destino);
                        System.out.println("P1 se moveu para o Norte.");
                        movimentoValido = true;
                    }
                } else if (acaoP1 == 'S' || acaoP1 == 's') { // Movimento para o SUL (x + 1)
                    if (!setorAtual.getPortas()[1]) { // Porta Sul é o índice 1
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(player1.getPosX() + 1, player1.getPosY());
                        player1.movimentar(destino);
                        System.out.println("P1 se moveu para o Sul.");
                        movimentoValido = true;
                    }
                } else if (acaoP1 == 'D' || acaoP1 == 'd') { // Movimento para o LESTE (y + 1)
                    if (!setorAtual.getPortas()[2]) { // Porta Leste é o índice 2
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(player1.getPosX(), player1.getPosY() + 1);
                        player1.movimentar(destino);
                        System.out.println("P1 se moveu para o Leste.");
                        movimentoValido = true;
                    }
                } else if (acaoP1 == 'A' || acaoP1 == 'a') { // Movimento para o OESTE (y - 1)
                    if (!setorAtual.getPortas()[3]) { // Porta Oeste é o índice 3
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(player1.getPosX(), player1.getPosY() - 1);
                        player1.movimentar(destino);
                        System.out.println("P1 se moveu para o Oeste.");
                        movimentoValido = true;
                    }
                } else { // Se comeu bola e digitou algum comando errado
                    System.out.println("Movimento inválido, tente novamente uma das seguintes portas.");
                    tabuleiro.exiberPortasDisponiveis(player1);
                }
                // Aqui é pra sair da função caso P1 tenha encontrado a fonte de infecção
                if (player1.getPosX() == tabuleiro.getFonteInfecX() && player1.getPosY() == tabuleiro.getFonteInfecY())
                    return;
            }
        }
        // Atualiza a posição de P1 se ele saiu do setor que estava
        setorAtual = tabuleiro.getSetor(player1.getPosX(), player1.getPosY());
        if (!setorAtual.isVisitado()) {
            setorAtual.gerarInimigos();
            setorAtual.setVisitado(true);
        }

        // Imprime de novo o mapa com os detalhes com base na nova posição do P1 (se ele se moveu)
        tabuleiro.exibirTabuleiro(player1, player2);
        tabuleiro.exibirDetalhesdoSetor(player1);
        tabuleiro.exibirInimigosdoSetor(player1);

        // Verifica se P1 terá alguma ação disponível no setor que está
        if (setorAtual.getInimigos().isEmpty()) {
            if (setorAtual instanceof SetorPrivado) {
                System.out.println("\nNão tem mais nenhum inimigo no setor privado, passando a vez.");
                return; 
            }
            System.out.println("\nEste setor não tem mais inimigos, mas você ainda pode procurar(P) itens!");
        }

        // Laço para processar as duas ações de P1
        int quantidadeMovimento = 0;
        while (quantidadeMovimento < 2) {
            // Verifica de novo (desta vez para a segunda ação), se P1 não ficou sem nada para fazer no Setor
            if (setorAtual.getInimigos().isEmpty() && !setorAtual.podeProcurar()) {
                        System.out.println("Não tem mais inimigos no setor privado, passando a vez.");
                        return; 
            }
            char acaoP1 = scanner.next().charAt(0);
            if (acaoP1 == 'P' || acaoP1 == 'p') { // Tenta procurar por itens no setor
                // Verifica se é setor privado para impedir a procura
                if (!setorAtual.podeProcurar()) {
                    System.out.println("Ação Negada! Você está em um Setor Privado e não pode procurar itens aqui.");
                } else {
                    player1.procurar(setorAtual);
                    quantidadeMovimento++;
                }
            } else if (acaoP1 == 'E' || acaoP1 == 'e') { // Tenta atacar inimigos no setor
                if (setorAtual.getInimigos().isEmpty()) {
                    System.out.println("Não há vírus neste setor para atacar.");
                } else { // Pega sempre o primeiro inimigo da lista para atacar
                    Inimigo alvo = setorAtual.getInimigos().get(0);
                    if (setorAtual.ataqueAcertou()) { // Vê se o golpe vai pegar com 50% de chance (Oculto) ou 100% de chance (Demais)
                        player1.atacar(alvo);
                        System.out.println("P1 acertou a ataque no vírus!");
                    } else {
                        System.out.println("P1 mirou no vírus, mas acertou na vazio!");
                    }
                    if (alvo.getDef() <= 0) { // Verifica se o golpe derrotou o inimigo
                        setorAtual.getInimigos().remove(0);
                        System.out.println("Um Vírus foi eliminado do setor!");
                    }
                    tabuleiro.exibirInimigosdoSetor(player1);
                    quantidadeMovimento++;
                }
            } else { // Se comeu bola e digitou algum comando errado
                System.out.println("Ação inválida. Tente novamente.");
            }
            if (quantidadeMovimento == 1) { // Aqui é para lembrar o player que ele pode fazer mais uma ação
                System.out.println("Você ainda pode realizar mais uma ação!");
            }
        }
    }

    // Tem fazer isso aqui ainda
    public void carregarTurnosdosInimigos() { 
        this.cicloAtual++;
    }

    public boolean verificarFimdeJogo() { 
        // colocar 25 dps
        if (cicloAtual > 5) { 
            System.out.println("O ciclo limite de jogo foi excedido! Encerrando a partida.");
            return true;
        }
        if (player1.getDef() <= 0) {
            System.out.println("O jogador simples foi derrotado! Encerrando a partida.");
            return true;
        }
        int infecX = tabuleiro.getFonteInfecX();
        int infecY = tabuleiro.getFonteInfecY();
        if (player1.getPosX() == infecX && player1.getPosY() == infecY) {
            System.out.println("O jogador simples encontrou a fonte da infecção! Encerrando a partida.");
            return true;
        }
        if (player2.getPosX() == infecX && player2.getPosY() == infecY) {
            System.out.println("O jogador suporte encontrou a fonte da infecção! Encerrando a partida.");
            return true;
        }
        return false;
    }
}