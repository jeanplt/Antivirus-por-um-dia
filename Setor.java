import java.util.ArrayList;

public abstract class Setor {
    private int x;
    private int y;
    private boolean[] portasVisiveis;
    private boolean visitado;
    private ArrayList<Inimigo> inimigos; 

    public Setor(int x, int y) {
        this.x = x;
        this.y = y;
        this.portasVisiveis = new boolean[4];
        this.visitado = false;
        this.inimigos = new ArrayList<>();
    }

    public boolean[] getPortas() { 
        return portasVisiveis; 
    }

    public void setPorta(int direcao, boolean estado) { 
        this.portasVisiveis[direcao] = estado; 
    }

    public int getX() { 
        return x; 
    }
    
    public int getY() { 
        return y; 
    }
    
    public boolean isVisitado() { 
        return visitado; 
    }
    
    public void setVisitado(boolean visitado) { 
        this.visitado = visitado; 
    }
    
    public ArrayList<Inimigo> getInimigos() { 
        return inimigos; 
    }

    public void gerarInimigos() {
        if (!this.visitado) {
            int qtdInimigos = Aleatorio.entre(0, 2);         
            for (int i = 0; i < qtdInimigos; i++) {
                this.inimigos.add(new Inimigo());
            }
        }
    }

    public boolean podeProcurar() {
        return true;
    }

    public boolean ataqueAcertou() {
        return true; 
    }

    public abstract void aplicarRegraSetor(Jogador player);
}