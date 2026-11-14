import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // Inicio do programa
        System.out.println(Cores.ANSI_GREEN + "Antivírus por um dia" + Cores.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        // Load do Menu do jogo, sempre que acaba volta pra ele, pra sair tem que apertar 3
        while (true) {
            System.out.println("\nMenu (selecione uma opção):");
            System.out.println("1) Iniciar Jogo");
            System.out.println("2) Controles");
            System.out.println("3) Sair");
            int opcao = scanner.nextInt();
            // Switch case com as opções do Menu
            switch (opcao) {
            case 1: // Início do jogo
                System.out.println(Cores.ANSI_GREEN + "Iniciando Antivírus por um dia..." + Cores.ANSI_RESET); 
                Turnos jogo = new Turnos();
                jogo.iniciarJogo();
                // Laço da partida
                while (!jogo.verificarFimdeJogo()) {
                    System.out.println("\n--- Turno Atual: " + jogo.getCicloAtual() + " ---");
                    jogo.carregarTurnodoPlayers(jogo.getPlayer1(), scanner);
                    if (jogo.verificarFimdeJogo()) {
                        break;
                    }
                    jogo.carregarTurnodoPlayers(jogo.getPlayer2(), scanner);
                    if (jogo.verificarFimdeJogo()) {
                        break;
                    }
                    System.out.println("\n--- Turno dos Inimigos ---");
                    System.out.println("");
                    jogo.carregarTurnosdosInimigos();
                }
                System.out.println("\nFim de jogo!");
                break;
            case 2: // Mostra só controles e volta pro Menu apertando qualquer coisa
                System.out.println(Cores.ANSI_GREEN + "Controles do jogo:" + Cores.ANSI_RESET);
                System.out.println("Movimentação: W(cima), A(esquerda), S(baixo), D(direita)");
                System.out.println("Ataque: E");
                System.out.println("Procurar: P");
                System.out.println("Recuperar Defesa (P2): R NP(Numero do jogador)");
                System.out.println(Cores.ANSI_GREEN + "Pressione qualquer tecla para continuar..." + Cores.ANSI_RESET);
                Scanner continueScanner = new Scanner(System.in);
                continueScanner.nextLine();
                break;
            case 3: // Fecha o programa
                System.out.println(Cores.ANSI_GREEN + "Saindo do jogo..." + Cores.ANSI_RESET);
                scanner.close();
                System.exit(0);
            default: // Se digitou algum comando inválido
                System.out.println(Cores.ANSI_RED + "Opção inválida." + Cores.ANSI_RESET);
                break;
            }
        }
    }
}