import java.util.Random;

public class Inimigo extends TipodePersonagem {

    public Inimigo() {
        super(0, 0); 
        Random aleatorio = new Random(); 
        int statusAleatorio = aleatorio.nextInt(3) + 1; 
        this.setAtk(statusAleatorio);
        this.setDef(statusAleatorio);
    }

    @Override
    public void atacar(TipodePersonagem alvo) {
        Random aleatorio = new Random(); 
        int chance = aleatorio.nextInt(6) + 1; 
        if (chance % 2 == 0) { 
            alvo.setDef(alvo.getDef() - this.getAtk());
        }
    }
}