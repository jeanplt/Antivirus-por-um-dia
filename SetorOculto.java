public class SetorOculto extends Setor {
    public SetorOculto(int x, int y) {
        super(x, y);
    }

    // Reduz a chance de ataque de 100% (base) para 50% (Oculto)
    @Override 
    public boolean ataqueAcertou() { 
        return Aleatorio.booleano();
    }

    // Retorna a característica do Setor Oculto
    @Override
    public void aplicarRegraSetor(Jogador player) {
        System.out.println("Este é um Setor Oculto. Seus ataques podem falhar.");
    }
}