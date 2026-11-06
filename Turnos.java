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

    public void exibirTelaDoJogo() {
        System.out.println("\n--- Turno Atual: " + cicloAtual + " ---");
        tabuleiro.exibirTabuleiro(player1, player2);
    }

    public void carregarTurnosdosPlayers() { 
    }

    public void carregarTurnosdosInimigos() { 
        this.cicloAtual++;
    }

    public boolean verificarFimdeJogo() { 
        if (cicloAtual > 25) { 
            return true;
        }
        if (player1.getDef() <= 0 && player2.getDef() <= 0) {
            return true;
        }
        return false;
    }
}