package Hashing;

import java.util.Random;

public class RandomWordGenerator {

    Random random;
    String chars = "abcdefghijklmnopqrstuvwxyz";


    public RandomWordGenerator(){
//        chars = "bbbbaaaaaaaa";
        random = new Random();
    }

    public String nextRandomWord(){
        int len = 7;
        StringBuilder word = new StringBuilder();
        for(int i = 0; i < len; ++i){
            int randIndex = random.nextInt(len);
            word.append(chars.charAt(randIndex));
        }
        return word.toString();
    }
}
