package Cipher;

import Alphabet.RuAlphabet;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher {
    private final RuAlphabet ruAlphabet;

    public CaesarCipher(RuAlphabet alphabet){
        this.ruAlphabet = alphabet;
    }

    /**
     * Функция для шифрации и дешифрации
     * Для дешифрации вызывать функцию, передавая -key
     * Случай, когда newIndex мог стать отрицательным, обработан в 40-41 строках
     * @param read
     * @param key
     * @return
     */
    public List<String> encryption(List<String> read, int key){
        List<String> result = new ArrayList<>();

        char current;
        int index;
        int newIndex;
        boolean isUpperCase;
        String processed;

        for(int i = 0; i < read.size(); i++){
            processed = "";
            for(int j = 0; j < read.get(i).length(); j++){
                current = read.get(i).charAt(j);
                isUpperCase = Character.isUpperCase(current);
                index = ruAlphabet.getIndex(current);

                if (Character.isLetter(current)){
                    newIndex = (index + key) % ruAlphabet.getSizeRuChar();
                    if(newIndex < 0){ newIndex += ruAlphabet.getSizeRuChar(); }

                    current = ruAlphabet.getChar(newIndex);
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
