package Cipher;

import Alphabet.RuAlphabet;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher {
    private final RuAlphabet ruAlphabet;

    public CaesarCipher(RuAlphabet alphabet){
        this.ruAlphabet = alphabet;
    }

    public List<String> encryption(List<String> read, int key){
        List<String> data = read;
        List<String> result = new ArrayList<>();

        char current;
        int index;
        boolean isUpperCase;
        String processed;

        for(int i = 0; i < data.size(); i++){
            processed = "";
            for(int j = 0; j < data.get(i).length(); j++){
                current = data.get(i).charAt(j);
                isUpperCase = Character.isUpperCase(current);
                index = ruAlphabet.getIndex(current);
                if (Character.isLetter(current)){
                    current = ruAlphabet.getChar((index + key) % ruAlphabet.getSizeRuСhar());
                    if (isUpperCase){
                        current = Character.toUpperCase(current);
                    }
                }
                processed += current;
            }
            result.add(processed);
        }
        return result;
    }

    public List<String> decryption(List<String> data, int key){
        return encryption(data, -key);
    }


}
