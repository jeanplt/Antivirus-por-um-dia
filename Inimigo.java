public class Inimigo extends TipodePersonagem {
    // Construtor do inimigo com atk = def e valor de 1..3
    public Inimigo() {
        super(0, 0); 
        int statusAleatorio = Aleatorio.entre(1, 3);
        this.setAtk(statusAleatorio);
        this.setDef(statusAleatorio);
    }

    // Método de ataque do inimigo
    @Override
    public void atacar(TipodePersonagem alvo) {
        int chance = Aleatorio.d6();
        if (chance % 2 == 0) { 
            alvo.setDef(alvo.getDef() - this.getAtk());
        }
    }
}