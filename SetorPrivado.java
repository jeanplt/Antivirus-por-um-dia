public class SetorPrivado extends Setor {
    public SetorPrivado(int x, int y) {
        super(x, y);
    }

    // Desativa a procura de itens
    @Override 
    public boolean podeProcurar() { 
        return false;
    }

    // Retorna a característica do Setor Privado
    @Override
    public void aplicarRegraSetor(Jogador player) {
        System.out.println("Este é um Setor Privado. A ação de procurar itens está bloqueada.");
    }
}