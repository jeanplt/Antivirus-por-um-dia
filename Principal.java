import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // Inicio do programa
        System.out.println("\u001B[32m" + "Antivírus por um dia" + "\u001B[0m");
        Scanner scanner = new Scanner(System.in);
        // Load do Menu do jogo, sempre que acaba volta pra ele, pra sair tem que apertar 3
        while (true) {
            System.out.println("Menu (selecione uma opção):");
            System.out.println("1) Iniciar Jogo");
            System.out.println("2) Controles");
            System.out.println("3) Sair");
            int opcao = scanner.nextInt();
            // Switch case com as opções do Menu
            switch (opcao) {
            case 1: // Início do jogo
                System.out.println("\u001B[32m" + "Iniciando Antivírus por um dia..." + "\u001B[0m"); 
                Turnos jogo = new Turnos();
                jogo.iniciarJogo();
                while (!jogo.verificarFimdeJogo()) {
                    System.out.println("\n--- Turno Atual: " + jogo.getCicloAtual() + " ---");
                    jogo.carregarTurnodoPlayers(jogo.getPlayer1());
                    if (jogo.verificarFimdeJogo()) {
                        break;
                    }
                    jogo.carregarTurnodoPlayers(jogo.getPlayer2());
                    System.out.println("\n--- Turno dos Inimigos ---");
                    jogo.carregarTurnosdosInimigos();
                }
                System.out.println("\nFim de jogo!");
                break;
            case 2: // Mostra só controles e volta pro Menu apertando qualquer coisa
                System.out.println("\u001B[32m" + "Controles do jogo:" + "\u001B[0m");
                System.out.println("Movimentação: W(cima), A(esquerda), S(baixo), D(direita)");
                System.out.println("Ataque: E");
                System.out.println("Procurar: P");
                System.out.println("Recuperar Defesa (P2): R NP(Numero do jogador)");
                System.out.println("\u001B[32m" + "Pressione qualquer tecla para continuar..." + "\u001B[0m");
                Scanner continueScanner = new Scanner(System.in);
                continueScanner.nextLine();
                break;
            case 3: // Fecha o programa
                System.out.println("\u001B[32m" + "Saindo do jogo..." + "\u001B[0m");
                scanner.close();
                System.exit(0);
            default: // Se comeu bola e digitou algum comando inválido
                System.out.println("\u001B[31m" + "Opção inválida." + "\u001B[0m");
                break;
            }
        }
    }
}