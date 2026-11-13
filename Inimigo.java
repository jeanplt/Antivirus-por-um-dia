import java.util.Random;

public class Inimigo extends TipodePersonagem {

    public Inimigo() {
        super(0, 0); 
        int statusAleatorio = Aleatorio.entre(1, 3);
        this.setAtk(statusAleatorio);
        this.setDef(statusAleatorio);
    }

    @Override
    public void atacar(TipodePersonagem alvo) {
        Random aleatorio = new Random(); 
        int chance = Aleatorio.d6();
        
        if (chance % 2 == 0) { 
            alvo.setDef(alvo.getDef() - this.getAtk());
        }
    }
}