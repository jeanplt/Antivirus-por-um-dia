public class SetorNormal extends Setor {

    public SetorNormal(int x, int y) {
        super(x, y); 
    }

    @Override
    public void aplicarRegraSetor(Jogador player) {
        System.out.println("Você está em um Setor Normal. Nenhuma restrição aplicada.");
    }
}