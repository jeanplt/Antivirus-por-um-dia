public class SetorOculto extends Setor {

    public SetorOculto(int x, int y) {
        super(x, y);
    }

    @Override 
    public boolean ataqueAcertou() { // Define 50% de chance de acertar o golpe
        return Aleatorio.booleano();
    }

    @Override
    public void aplicarRegraSetor(Jogador player) {
        System.out.println("Este é um Setor Oculto. Seus ataques podem falhar.");
    }
}