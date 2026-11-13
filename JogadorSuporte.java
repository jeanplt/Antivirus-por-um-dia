import java.util.Random;

public class JogadorSuporte extends Jogador {

    public JogadorSuporte() {
        super(1, 7);
    }

    @Override
    public void atacar(TipodePersonagem alvo) {
        alvo.setDef(alvo.getDef() - this.getAtk()); 
    }

    @Override
    public void movimentar(Setor destino) {
        this.setPosX(destino.getX());
        this.setPosY(destino.getY());
    }

    @Override
    public void procurar(Setor setorAtual) {
        Random aleatorio = new Random(); 
        int num = aleatorio.nextInt(6) + 1; 
        if (num < 4) {
            System.out.println("P2 vasculhou o setor, mas nada foi encontrado desta vez!");
        } else if (num == 4) {
            System.out.println("P2 encontrou alguns tokens de segurança e obteve mais 1 de Defesa!");
            this.setDef(this.getDef() + 1); 
        } else if (num == 5) {
            System.out.println("P2 encontrou vários tokens de segurança e obteve mais 2 de Defesa!");
            this.setDef(this.getDef() + 2);
        } else {
            System.out.println("P2 encontrou um Firewall poderoso! Se existir vírus do setor, todos eles perdem 1 de Defesa!");
            for (int i = 0; i < setorAtual.getInimigos().size(); i++) {
                Inimigo inimigo = setorAtual.getInimigos().get(i);
                inimigo.setDef(inimigo.getDef() - 1);
            }
            setorAtual.getInimigos().removeIf(ini -> ini.getDef() <= 0);
        }
    }

    public void recDefesa(TipodePersonagem alvo) {
        alvo.setDef(alvo.getDef() + 2);
    }
}