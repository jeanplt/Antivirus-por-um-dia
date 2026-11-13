public abstract class Jogador extends TipodePersonagem implements AcoesExploracao {
    private int danoAcumulado;
    
    public Jogador(int atk, int def) {
        super(atk, def);
        this.danoAcumulado = 0;
    }

    public int getDanoAcumulado() {
        return  this.danoAcumulado;
    }

    public void somaDanoAcumulado(int idPlayer) {
        if (idPlayer == 1)
            this.danoAcumulado += 2;
        else
            this.danoAcumulado += 1;
    }
}