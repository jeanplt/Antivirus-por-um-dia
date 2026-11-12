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

    public JogadorSimples getPlayer1() {
        return this.player1;
    }

    public JogadorSuporte getPlayer2() {
        return this.player2;
    }

    public void iniciarJogo() {
        tabuleiro.iniciarTabuleiro();
        player1.setPosX(2); player1.setPosY(2);
        player2.setPosX(2); player2.setPosY(2);
    }

    public void carregarTurnodoPlayers(Jogador playerDaVez) { 
        Scanner scanner = new Scanner(System.in);

        // Variáveis para tratar cor do turno do player e como chamá-lo (P1 ou P2)
        String corTurno;
        String abreviacaoPlayer;
        String tipoPlayer;

        if (playerDaVez instanceof JogadorSimples) {
            corTurno = "\u001B[33m"; // Amarelo para P1
            abreviacaoPlayer = "P1";
            tipoPlayer = "JOGADOR 1 (SIMPLES)";
        } else {
            corTurno = "\u001B[34m"; // Azul para P2
            abreviacaoPlayer = "P2";
            tipoPlayer = "JOGADOR 2 (SUPORTE)";
        }

        tabuleiro.exibirTabuleiro(player1, player2);

        System.out.println(corTurno + "\n--- TURNO DO " + tipoPlayer + " ---" +"\u001B[0m");

        tabuleiro.exibirStatusdoPlayer(playerDaVez);
        tabuleiro.exibirDetalhesdoSetor(playerDaVez);
        tabuleiro.exibirInimigosdoSetor(playerDaVez); 

        // Parte inicial para se movimentar caso não tenha inimigos no Setor
        Setor setorAtual = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY());
        if (setorAtual.getInimigos().isEmpty()) {
            tabuleiro.exiberPortasDisponiveis(playerDaVez);
            boolean movimentoValido = false;
            while (!movimentoValido) {
                char acao = scanner.next().charAt(0);
                if (acao == 'W' || acao == 'w') { // Movimento para o NORTE (x - 1)
                    if (!setorAtual.getPortas()[0]) { // Porta Sul é o índice 0
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX() - 1, playerDaVez.getPosY());
                        playerDaVez.movimentar(destino);
                        System.out.println(abreviacaoPlayer + " se moveu para o Norte.");
                        movimentoValido = true;
                    }
                } else if (acao == 'S' || acao == 's') { // Movimento para o SUL (x + 1)
                    if (!setorAtual.getPortas()[1]) { // Porta Sul é o índice 1
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX() + 1, playerDaVez.getPosY());
                        playerDaVez.movimentar(destino);
                        System.out.println(abreviacaoPlayer + " se moveu para o Sul.");
                        movimentoValido = true;
                    }
                } else if (acao == 'D' || acao == 'd') { // Movimento para o LESTE (y + 1)
                    if (!setorAtual.getPortas()[2]) { // Porta Leste é o índice 2
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY() + 1);
                        playerDaVez.movimentar(destino);
                        System.out.println(abreviacaoPlayer + " se moveu para o Leste.");
                        movimentoValido = true;
                    }
                } else if (acao == 'A' || acao == 'a') { // Movimento para o OESTE (y - 1)
                    if (!setorAtual.getPortas()[3]) { // Porta Oeste é o índice 3
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY() - 1);
                        playerDaVez.movimentar(destino);
                        System.out.println(abreviacaoPlayer + " se moveu para o Oeste.");
                        movimentoValido = true;
                    }
                } else { // Se comeu bola e digitou algum comando errado
                    System.out.println("Movimento inválido, tente novamente uma das seguintes portas.");
                    tabuleiro.exiberPortasDisponiveis(playerDaVez);
                }
                // Aqui é pra sair da função caso tenha encontrado a fonte de infecção
                if (playerDaVez.getPosX() == tabuleiro.getFonteInfecX() && playerDaVez.getPosY() == tabuleiro.getFonteInfecY())
                    return;
            }
        }
        // Atualiza a posição se ele saiu do setor que estava
        setorAtual = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY());
        if (!setorAtual.isVisitado()) {
            setorAtual.gerarInimigos();
            setorAtual.setVisitado(true);
        }

        // Imprime de novo o mapa com os detalhes com base na nova posição (se ele se moveu)
        tabuleiro.exibirTabuleiro(player1, player2);
        tabuleiro.exibirDetalhesdoSetor(playerDaVez);
        tabuleiro.exibirInimigosdoSetor(playerDaVez);

        // Verifica se terá alguma ação disponível no setor que está
        if (setorAtual.getInimigos().isEmpty()) {
            if (setorAtual instanceof SetorPrivado) {
                System.out.println("\nNão tem mais nenhum inimigo no setor privado, passando a vez.");
                return; 
            }
            System.out.println("\nEste setor não tem mais inimigos, mas você ainda pode procurar(P) itens!");
        }

        // Laço para processar as duas ações
        int quantidadeMovimento = 0;
        while (quantidadeMovimento < 2) {
            // Verifica de novo (desta vez para a segunda ação), se não ficou sem nada para fazer no Setor
            if (setorAtual.getInimigos().isEmpty() && !setorAtual.podeProcurar()) {
                        System.out.println("Não tem mais inimigos no setor privado, passando a vez.");
                        return; 
            }
            char acao = scanner.next().charAt(0);
            if (acao == 'P' || acao == 'p') { // Tenta procurar por itens no setor
                // Verifica se é setor privado para impedir a procura
                if (!setorAtual.podeProcurar()) {
                    System.out.println("Ação Negada! Você está em um Setor Privado e não pode procurar itens aqui.");
                } else {
                    playerDaVez.procurar(setorAtual);
                    quantidadeMovimento++;
                }
            } else if (acao == 'E' || acao == 'e') { // Tenta atacar inimigos no setor
                if (setorAtual.getInimigos().isEmpty()) {
                    System.out.println("Não há vírus neste setor para atacar.");
                } else { // Pega sempre o primeiro inimigo da lista para atacar
                    Inimigo alvo = setorAtual.getInimigos().get(0);
                    if (setorAtual.ataqueAcertou()) { // Vê se o golpe vai pegar com 50% de chance (Oculto) ou 100% de chance (Demais)
                        playerDaVez.atacar(alvo);
                        System.out.println(abreviacaoPlayer + " acertou o ataque no vírus!");
                    } else {
                        System.out.println(abreviacaoPlayer + " mirou no vírus, mas acertou na vazio!");
                    }
                    if (alvo.getDef() <= 0) { // Verifica se o golpe derrotou o inimigo
                        setorAtual.getInimigos().remove(0);
                        System.out.println("Um Vírus foi eliminado do setor!");
                    }
                    tabuleiro.exibirInimigosdoSetor(playerDaVez); 
                    quantidadeMovimento++;
                }
            } else if ((acao == 'R' || acao == 'r') && playerDaVez instanceof JogadorSuporte) {
                System.out.println("P2 decidiu recuperar 2 de Defesa, agora escolha para quem irá a ajuda (1 para P1 ou 2 para P2):");
                int numPlayer = scanner.nextInt();
                if (numPlayer == 1) {
                    if ((player1.getPosX() == player2.getPosX()) && (player1.getPosY() == player2.getPosY()))
                        ((JogadorSuporte) playerDaVez).recDefesa(player1);
                    else {
                        System.out.println("P1 não está no mesmo setor de P2, logo P2 irá recuperar sua própria defesa em 2!");
                        ((JogadorSuporte) playerDaVez).recDefesa(player2);
                    }
                } else
                    ((JogadorSuporte) playerDaVez).recDefesa(player2);
                quantidadeMovimento++;
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