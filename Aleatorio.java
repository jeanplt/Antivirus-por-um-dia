import java.util.Random;

//classe para padronizar o sorteio de numeros pseudoaleatorios
//rolagens do D6

public class Aleatorio {
    private static final Random random = new Random();

    //sorteio de um numero de 1 a 6, simulando o dado d6
    //retorna o valor entre 1 a 6
    public static int d6() {
        return random.nextInt(6) +  1;
    }

    //sorteio de um numero inteiro dentro do intervalo min,max 
    //min eh o limite inferior e max eh o limite superior, sendo o retorno um valor entre esses dois
    public static int entre(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return random.nextInt((max - min) + 1) + min;
    }

    //retorna um booleano aleatorio para testes no sistema oculo
    public static boolean booleano() {
        return random.nextBoolean();
    }
}