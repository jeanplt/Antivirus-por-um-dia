public abstract class Jogador extends TipodePersonagem implements AcoesExploracao {
    private int danoAcumulado;
    
    // Construtor dos players com atk, def e dano acumulado para monitorar durante a partida
    public Jogador(int atk, int def) {
        super(atk, def);
        this.danoAcumulado = 0;
    }

    // Get para poder comparar os danos dos jogadores ao longo dos turnos
    public int getDanoAcumulado() {
        return  this.danoAcumulado;
    }

    // Acumula dano para o jogador se ele acertou o ataque
    public void somaDanoAcumulado(int idPlayer) {
        if (idPlayer == 1)
            this.danoAcumulado += 2;
        else
            this.danoAcumulado += 1;
    }
}