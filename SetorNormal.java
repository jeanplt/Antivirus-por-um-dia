public class SetorNormal extends Setor { 
    public SetorNormal(int x, int y) {
        super(x, y); 
    }

    // Retorna a característica do Setor Normal
    @Override
    public void aplicarRegraSetor(Jogador player) {
        System.out.println("Você está em um Setor Normal. Nenhuma restrição aplicada.");
    }
}