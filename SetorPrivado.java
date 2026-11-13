public class SetorPrivado extends Setor {

    public SetorPrivado(int x, int y) {
        super(x, y);
    }

    @Override 
    public boolean podeProcurar() { // Desativa a procura de itens
        return false;
    }

    @Override
    public void aplicarRegraSetor(Jogador player) {
        System.out.println("Este é um Setor Privado. A ação de procurar itens está bloqueada.");
    }
}