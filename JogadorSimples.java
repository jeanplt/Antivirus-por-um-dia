
public class JogadorSimples extends Jogador {
    // Construtor do jogador simples definido com atk=2 e def=6
    public JogadorSimples() {
        super(2, 6); 
    }

    // Ataque do P1
    @Override
    public void atacar(TipodePersonagem alvo) {
        alvo.setDef(alvo.getDef() - this.getAtk());
    }

    // Movimento do P1
    @Override
    public void movimentar(Setor destino) {
        this.setPosX(destino.getX());
        this.setPosY(destino.getY());
    }

    // Procura do P1
    @Override
    public void procurar(Setor setorAtual) {
       int num = Aleatorio.d6();
        if (num < 4) {
            System.out.println("P1 vasculhou o setor, mas nada foi encontrado desta vez!");
        } else if (num == 4) {
            System.out.println("P1 encontrou alguns tokens de segurança e obteve mais 1 de Defesa!");
            this.setDef(this.getDef() + 1); 
        } else if (num == 5) {
            System.out.println("P1 encontrou vários tokens de segurança e obteve mais 2 de Defesa!");
            this.setDef(this.getDef() + 2);
        } else {
            System.out.println("P1 encontrou um Firewall poderoso! Se existir vírus do setor, todos eles perdem 1 de Defesa!");
            for (int i = 0; i < setorAtual.getInimigos().size(); i++) {
                Inimigo inimigo = setorAtual.getInimigos().get(i);
                inimigo.setDef(inimigo.getDef() - 1);
            }
            setorAtual.getInimigos().removeIf(ini -> ini.getDef() <= 0);
        }
    }
}