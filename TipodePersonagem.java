public abstract class TipodePersonagem {
    private int atk;
    private int def;
    private int posX;
    private int posY;

    // Construtor de personagem com base no atk e def
    public TipodePersonagem(int atk, int def) {
        this.atk = atk;
        this.def = def;
    }

    // Get do ataque
    public int getAtk() { 
        return atk; 
    }
    
    // Get da defesa
    public int getDef() { 
        return def; 
    }

    // Get da posição X
    public int getPosX() { 
        return posX; 
    }
    
    // Get da posição Y
    public int getPosY() { 
        return posY; 
    }

    // Set do ataque
    public void setAtk(int atk) { 
        this.atk = atk; 
    }

    // Set da defesa
    public void setDef(int def) { 
        this.def = def; 
    }
    
    // Set da posição X
    public void setPosX(int posX) { 
        this.posX = posX; 
    }
    
    // Set da posição Y
    public void setPosY(int posY) { 
        this.posY = posY; 
    }

    // Método para o personagem atacar o alvo
    public abstract void atacar(TipodePersonagem alvo);
}