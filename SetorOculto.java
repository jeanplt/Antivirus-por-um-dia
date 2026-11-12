import java.util.Random;
public class SetorOculto extends Setor {

    public SetorOculto(int x, int y) {
        super(x, y);
    }

    @Override 
    public boolean ataqueAcertou() { // Define 50% de chance de acertar o golpe
        Random aleatorio = new Random(); 
        return aleatorio.nextBoolean(); 
    }

    @Override
    public void aplicarRegraSetor(TipodePersonagem player) {
        System.out.println("Este é um Setor Oculto. Seus ataques podem falhar.");
    }
}