package Alphabet;
import Exception.AlphabetException;

import java.util.*;

public class RuAlphabet {
    private static final Character[] RU_CHAR = {
            'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т',
            'у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я'
    };
    private static final Character[] SYMBOL = {'.', ',', '«', '»', '"', '\'', ':', '-', '!', '?', ' '};

    private final List<Character> char_List;
    private final Map<Character, Integer> char_Map;

    public RuAlphabet(){
        this.char_List = new ArrayList<>();

        char_List.addAll(Arrays.asList(RU_CHAR));
        char_List.addAll(Arrays.asList(SYMBOL));

        char_Map = new HashMap<>();
        for(int i = 0; i < this.char_List.size(); i++){
            char_Map.put(char_List.get(i), i);
        }
    }

    public int getSizeRuСhar(){
        return RU_CHAR.length;
    }

    private int getSize(){
        return char_List.size();
    }

    /**
     * Получение индекса символа
     * @param symbol
     * @return
     */
    public int getIndex(Character symbol){
        if(!char_Map.containsKey(Character.toLowerCase(symbol))){
            throw new AlphabetException("Некорректный символ " + symbol +
                                        " \nДопустимы символы русского языка");
        }
        return char_Map.get(Character.toLowerCase(symbol));
    }

    /**
     * Получение символа по индексу
     * @param index
     * @return
     */
    public char getChar(int index){
        if(index < 0 || index >= getSize()){
            throw new AlphabetException("Некорректный индекс " + index +
                    " \nДопустимый индекс от 0 до " + getSize());
        }
        return char_List.get(index);
    }
}
