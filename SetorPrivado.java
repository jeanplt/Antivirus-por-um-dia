public class SetorPrivado extends Setor {

    public SetorPrivado(int x, int y) {
        super(x, y);
    }

    @Override
    public void aplicarRegraSetor(TipodePersonagem player) {
        System.out.println("Este é um Setor Privado. A ação de procurar itens está bloqueada.");
    }
    public boolean podeProcurar() {
        return false;
    }
}