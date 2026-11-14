import java.util.Random;

// Classe para padronizar o sorteio de numeros pseudoaleatorios
// rolagens do D6

public class Aleatorio {
    private static final Random random = new Random();
    // Retorna o valor entre 1 a 6
    public static int d6() {
        return random.nextInt(6) +  1;
    }
    
    // Sorteio de um numero inteiro dentro do intervalo min,max 
    // min eh o limite inferior e max eh o limite superior, sendo o retorno um valor entre esses dois
    public static int entre(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return random.nextInt((max - min) + 1) + min;
    }

    // Retorna um booleano aleatorio para testes no sistema de ataque no setor oculto
    public static boolean booleano() {
        return random.nextBoolean();
    }
}