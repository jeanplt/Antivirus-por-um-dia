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
    
    public int getDef() { 
        return def; 
    }

    public int getPosX() { 
        return posX; 
    }
    
    public int getPosY() { 
        return posY; 
    }

    public void setAtk(int atk) { 
        this.atk = atk; 
    }

    public void setDef(int def) { 
        this.def = def; 
    }
    
    public void setPosX(int posX) { 
        this.posX = posX; 
    }
    
    public void setPosY(int posY) { 
        this.posY = posY; 
    }

    public abstract void atacar(TipodePersonagem alvo);
}