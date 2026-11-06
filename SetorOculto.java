import java.util.Random;
public class SetorOculto extends Setor {

    public SetorOculto(int x, int y) {
        super(x, y);
    }

    @Override
    public void aplicarRegraSetor(TipodePersonagem player) {
        System.out.println("Cuidado! Este é um Setor Oculto. Seus ataques podem falhar.");
    }
    public boolean ataqueAcertou() {
        Random aleatorio = new Random(); 
        int chance = aleatorio.nextInt(2); 
        return chance == 1; 
    }
}