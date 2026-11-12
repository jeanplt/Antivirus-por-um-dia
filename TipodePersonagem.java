public abstract class TipodePersonagem {
    private int atk;
    private int def;
    private int posX;
    private int posY;

    public TipodePersonagem(int atk, int def) {
        this.atk = atk;
        this.def = def;
    }

    public int getAtk() { 
        return atk; 
    }
    
    public void setAtk(int atk) { 
        this.atk = atk; 
    }
    
    public int getDef() { 
        return def; 
    }
    
    public void setDef(int def) { 
        this.def = def; 
    }
    
    public int getPosX() { 
        return posX; 
    }
    
    public void setPosX(int posX) { 
        this.posX = posX; 
    }
    
    public int getPosY() { 
        return posY; 
    }
    
    public void setPosY(int posY) { 
        this.posY = posY; 
    }

    public abstract void atacar(TipodePersonagem alvo);
}