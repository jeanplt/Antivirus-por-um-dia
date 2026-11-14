import java.util.ArrayList;

public abstract class Setor {
    private int x;
    private int y;
    private boolean[] portasVisiveis;
    private boolean visitado;
    private ArrayList<Inimigo> inimigos; 

    // Construtor do setor com base em suas coordenadas
    public Setor(int x, int y) {
        this.x = x;
        this.y = y;
        this.portasVisiveis = new boolean[4];
        this.visitado = false;
        this.inimigos = new ArrayList<>();
    }

    // Get coordenada X
    public int getX() { 
        return x; 
    }
    
    // Get coordenada Y
    public int getY() { 
        return y; 
    }

    // Get da porta
    public boolean[] getPortas() { 
        return portasVisiveis; 
    }

    // Is para retornar se o setor foi visitado
    public boolean isVisitado() { 
        return visitado; 
    }

    // Get para todos os inimigos existentes no setor
    public ArrayList<Inimigo> getInimigos() { 
        return inimigos; 
    }

    // Set para porta aberta (true) ou fechada (false)
    public void setPorta(int direcao, boolean estado) { 
        this.portasVisiveis[direcao] = estado; 
    }
    
    // Set para definir o setor como visitado (true) ou não visitado (false)
    public void setVisitado(boolean visitado) { 
        this.visitado = visitado; 
    }

    // Método gerador de inimigos
    public void gerarInimigos() {
        if (!this.visitado) {
            int qtdInimigos = Aleatorio.entre(1, 3);         
            for (int i = 0; i < qtdInimigos; i++) {
                this.inimigos.add(new Inimigo());
            }
        }
    }

    // Método que permite procura por padrão (somente sobrescrito no SetorPrivado)
    public boolean podeProcurar() {
        return true;
    }

    // Método que permite acertar o ataque por padrão (somente sobrescrito no SetorOculto)
    public boolean ataqueAcertou() {
        return true; 
    }

    // Método abstrato para mostrar regras do setor que o player está
    public abstract void aplicarRegraSetor(Jogador player);
}