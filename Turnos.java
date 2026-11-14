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
        String tipoPlayer;
        int idPlayer;

        if (playerDaVez instanceof JogadorSimples) {
            corTurno = "\u001B[33m"; // Amarelo para P1
            idPlayer = 1;
            tipoPlayer = "JOGADOR 1 (SIMPLES)";
        } else {
            corTurno = "\u001B[34m"; // Azul para P2
            idPlayer = 2;
            tipoPlayer = "JOGADOR 2 (SUPORTE)";
        }

        tabuleiro.exibirTabuleiro(player1, player2);

        System.out.println(corTurno + "\n--- TURNO DO " + tipoPlayer + " ---" +"\u001B[0m");

        tabuleiro.exibirStatusdoPlayer(playerDaVez);
        tabuleiro.exibirDetalhesdoSetor(playerDaVez);
        tabuleiro.exibirInimigosdoSetor(playerDaVez); 

        // Parte inicial para se movimentar caso não tenha inimigos no Setor
        Setor setorAtual = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY());
        boolean movimentoValido = false;
        if (setorAtual.getInimigos().isEmpty()) {
            tabuleiro.exibirPortasDisponiveis(playerDaVez);
            while (!movimentoValido) {
                char acao = scanner.next().charAt(0);
                if (acao == 'W' || acao == 'w') { // Movimento para o NORTE (x - 1)
                    if (!setorAtual.getPortas()[0]) { // Porta Norte é o índice 0
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX() - 1, playerDaVez.getPosY());
                        playerDaVez.movimentar(destino);
                        System.out.println("P" + idPlayer + " se moveu para o Norte.");
                        movimentoValido = true;
                    }
                } else if (acao == 'S' || acao == 's') { // Movimento para o SUL (x + 1)
                    if (!setorAtual.getPortas()[1]) { // Porta Sul é o índice 1
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX() + 1, playerDaVez.getPosY());
                        playerDaVez.movimentar(destino);
                        System.out.println("P" + idPlayer + " se moveu para o Sul.");
                        movimentoValido = true;
                    }
                } else if (acao == 'D' || acao == 'd') { // Movimento para o LESTE (y + 1)
                    if (!setorAtual.getPortas()[2]) { // Porta Leste é o índice 2
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY() + 1);
                        playerDaVez.movimentar(destino);
                        System.out.println("P" + idPlayer + " se moveu para o Leste.");
                        movimentoValido = true;
                    }
                } else if (acao == 'A' || acao == 'a') { // Movimento para o OESTE (y - 1)
                    if (!setorAtual.getPortas()[3]) { // Porta Oeste é o índice 3
                        System.out.println("Movimento inválido! Há uma parede no caminho.");
                    } else {
                        Setor destino = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY() - 1);
                        playerDaVez.movimentar(destino);
                        System.out.println("P" + idPlayer + " se moveu para o Oeste.");
                        movimentoValido = true;
                    }
                } else { // Se comeu bola e digitou algum comando errado
                    System.out.println("Movimento inválido, tente novamente uma das seguintes portas.");
                    tabuleiro.exibirPortasDisponiveis(playerDaVez);
                }
                // Aqui é pra sair da função caso tenha encontrado a fonte de infecção
                if (playerDaVez.getPosX() == tabuleiro.getFonteInfecX() && playerDaVez.getPosY() == tabuleiro.getFonteInfecY())
                    return;
            }
            // Atualiza a posição se ele saiu do setor que estava
            setorAtual = tabuleiro.getSetor(playerDaVez.getPosX(), playerDaVez.getPosY());
            if (!setorAtual.isVisitado()) {
                setorAtual.gerarInimigos();
                setorAtual.setVisitado(true);
            }
        }
        // Imprime de novo o mapa com os detalhes com base na nova posição (se ele se moveu)
        if (movimentoValido) {
            tabuleiro.exibirTabuleiro(player1, player2);
            tabuleiro.exibirDetalhesdoSetor(playerDaVez);
            tabuleiro.exibirInimigosdoSetor(playerDaVez);
        }

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
                } else { // Pega sempre o primeiro inimigo da lista para atacar, mas antes verifica se P2 não vai passar o dano
                    int danoP1 = player1.getDanoAcumulado();
                    int danoP2 = player2.getDanoAcumulado();
                    if (idPlayer == 2 && danoP1 == danoP2) {
                        System.out.println("P2 não pode ultrapassar o dano de P1, logo P2 perdeu a ação de ataque!");    
                    } else { // Aqui é para o player que pode atacar, aí só verifica se ele vai acertar ou não o golpe
                        Inimigo alvo = setorAtual.getInimigos().get(0);
                        if (setorAtual.ataqueAcertou()) { // Vê se o golpe vai pegar com 50% de chance (Oculto) ou 100% de chance (Demais)
                            playerDaVez.atacar(alvo);
                            playerDaVez.somaDanoAcumulado(idPlayer);
                            System.out.println("P" + idPlayer + " acertou o ataque no vírus!");
                        } else {
                            System.out.println("P" + idPlayer + " mirou no vírus, mas acertou na vazio!");
                        }
                        if (alvo.getDef() <= 0) { // Verifica se o golpe derrotou o inimigo
                            setorAtual.getInimigos().remove(0);
                            System.out.println("Um Vírus foi eliminado do setor!");
                            if (setorAtual.getInimigos().isEmpty())
                                System.out.println("Todos os vírus do setor foram eliminados!");
                        }
                        System.out.println();
                    }
                    tabuleiro.exibirInimigosdoSetor(playerDaVez); 
                    quantidadeMovimento++;
                }
            } else if ((acao == 'R' || acao == 'r') && playerDaVez instanceof JogadorSuporte) { // Exclusivo P2, recupera defesa para si ou para P1
                System.out.println("P2 decidiu recuperar 2 de Defesa, agora escolha para quem irá a ajuda (1 para P1 ou 2 para P2):");
                int numPlayer = scanner.nextInt();
                if (numPlayer == 1) {
                    if ((player1.getPosX() == player2.getPosX()) && (player1.getPosY() == player2.getPosY())) {
                        System.out.println("P2 recuperou 2 de defesa para P1!");
                        ((JogadorSuporte) playerDaVez).recDefesa(player1);
                    } else {
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
        // Primeira parte para bater no P1
        Setor setorP1 = tabuleiro.getSetor(player1.getPosX(), player1.getPosY());
        if (!setorP1.getInimigos().isEmpty()) {
            System.out.println("\n\u001B[31m--- Os vírus estão atacando o jogador simples (P1)! ---\u001B[0m");
            System.out.println();
            for (int i = 0; i < setorP1.getInimigos().size(); i++) {
                Inimigo inimigo = setorP1.getInimigos().get(i);
                int defesaAntes = player1.getDef(); 
                inimigo.atacar(player1); 
                if (player1.getDef() < defesaAntes) {
                    System.out.println("O Vírus " + (i+1) + " acertou o golpe! A defesa de P1 caiu para: " + player1.getDef());
                } else {
                    System.out.println("O Vírus " + (i+1) + " errou o ataque!");
                }
                if (player1.getDef() <= 0 ) {
                    return;
                }
                // Comando só para mostrar com mais calma como foi o ataque dos inimigos a P1
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    
        if(player2.getDef() <= 0 ) 
            return;
        // A pancada agora vai para as costas do P2
        Setor setorP2 = tabuleiro.getSetor(player2.getPosX(), player2.getPosY());
        if (!setorP2.getInimigos().isEmpty()) {
            System.out.println("\n\u001B[31m--- Agora os vírus estão atacando o jogador suporte (P2)! ---\u001B[0m");
            System.out.println();
            for (int i = 0; i < setorP2.getInimigos().size(); i++) {
                if (player2.getDef() <= 0 ) {
                    System.out.println("\nO jogador P2 foi derrotado!");
                    return;
                }
                Inimigo inimigo = setorP2.getInimigos().get(i);
                int defesaAntes = player2.getDef();
                inimigo.atacar(player2); 
                if (player2.getDef() < defesaAntes) {
                    System.out.println("O Vírus " + (i+1) + " acertou o golpe! A defesa do P2 caiu para: " + player2.getDef());
                } else {
                    System.out.println("O Vírus " + (i+1) + " errou o ataque!");
                }
                // Mais uma pausa agora para mostrar também os ataques ao P2
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Processa o fim do turno
        this.cicloAtual++;
        System.out.println();
    }

    public boolean verificarFimdeJogo() { 
        int infecX = tabuleiro.getFonteInfecX();
        int infecY = tabuleiro.getFonteInfecY();
        if (player1.getPosX() == infecX && player1.getPosY() == infecY) { // Caso P1 ache a fonte
            System.out.println("\nO jogador simples (P1) encontrou a fonte da infecção! Encerrando a partida.");
            return true;
        }
        if (player2.getPosX() == infecX && player2.getPosY() == infecY) { // Caso P2 ache a fonte
            System.out.println("\nO jogador suporte (P2) encontrou a fonte da infecção! Encerrando a partida.");
            return true;
        }
        if (cicloAtual > 25) { // Caso o limite de ciclos exceda
            System.out.println("O ciclo limite de jogo foi excedido! Encerrando a partida.");
            return true;
        }
        if (player1.getDef() <= 0) { // Caso o P1 tenha sido derrotado
            System.out.println("O jogador simples foi derrotado! Encerrando a partida.");
            return true;
        }
        return false;
    }
}