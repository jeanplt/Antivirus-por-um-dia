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

    public void iniciarJogo() {
        tabuleiro.iniciarTabuleiro();
        player1.setPosX(2); player1.setPosY(2);
        player2.setPosX(2); player2.setPosY(2);
    }

    public void carregarTurnosdosPlayers() { 
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Turno Atual: " + cicloAtual + " ---");
        tabuleiro.exibirTabuleiro(player1, player2);
        // 1. Turno do Jogador 1 (Simples)
        System.out.println("\n--- TURNO DO JOGADOR 1 (SIMPLES) ---");
        tabuleiro.exibirDetalhesdoSetor(player1); // Chama a tela focada no P1
        
        // (Exemplo do loop de ações do P1)
        // System.out.println("Escolha sua ação: 1) Mover  2) Atacar  3) Procurar");
        // int acaoP1 = scanner.nextInt();
        // Lógica da ação do P1...


        // 2. Turno do Jogador 2 (Suporte)
        System.out.println("\n--- TURNO DO JOGADOR 2 (SUPORTE) ---");
        tabuleiro.exibirDetalhesdoSetor(player2); // Chama a tela focada no P2
        
        // (Exemplo do loop de ações do P2)
        // System.out.println("Escolha sua ação: 1) Mover  2) Atacar  3) Procurar  4) Curar");
        // int acaoP2 = scanner.nextInt();
        // Lógica da ação do P2...
    }

    public void carregarTurnosdosInimigos() { 
        this.cicloAtual++;
    }

    public boolean verificarFimdeJogo() { 
        // colocar 25 dps
        if (cicloAtual > 1) { 
            return true;
        }
        if (player1.getDef() <= 0 && player2.getDef() <= 0) {
            return true;
        }
        return false;
    }
}