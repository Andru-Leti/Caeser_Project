package HackingCipher;

import Alphabet.RuAlphabet;
import Exception.StatisticalAnalyzerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticalAnalyzer {

    private static final List<Character> TOP_RUSSIAN_LETTERS = List.of(
            'о', 'е', 'а', 'и', 'н', 'т', 'с', 'р', 'в', 'л',
            'к', 'м', 'д', 'п', 'у', 'я', 'ы', 'ь', 'г', 'з',
            'б', 'ч', 'й', 'х', 'ж', 'ю', 'ш', 'ц', 'щ', 'э',
            'ф', 'ъ', 'ё'
    );

    private static final int MIN_UNIQUE_LETTERS = 5;
    private static final int MIN_TEXT_LENGTH = 100;

    /**
     * Определяет ключ шифрования статистическим методом
     * @param encryptedLines список строк зашифрованного текста
     * @return ключ (сдвиг), которым зашифрован текст
     */
    public static int getDecryptedKey(List<String> encryptedLines) {
        // Шаг 1: преобразую список строк в один массив символов
        char[] encrypted = convertLinesToChars(encryptedLines);

        System.out.println("Начинаю статистический анализ...");

        if (encrypted.length < MIN_TEXT_LENGTH) {
            throw new StatisticalAnalyzerException("⚠Текст слишком короткий (" + encrypted.length +
                    " < " + MIN_TEXT_LENGTH + "), результаты могут быть неточными");
        }

        // Шаг 2: подсчитываю частоту каждой буквы
        Map<Character, Integer> frequencyMap = countLetterFrequencies(encrypted);

        if (frequencyMap.size() < MIN_UNIQUE_LETTERS) {
            throw new StatisticalAnalyzerException("Слишком мало различных букв в тексте, анализ невозможен");
        }

        // Шаг 3: получаю самые частые буквы из шифротекста
        List<Character> topCipherLetters = getMostFrequentLetters(frequencyMap, TOP_RUSSIAN_LETTERS.size());

        // Шаг 4: провожу голосование за возможные сдвиги
        RuAlphabet alphabet = new RuAlphabet();
        Map<Integer, Integer> voteMap = performVoting(topCipherLetters, alphabet);

        // Шаг 5: отправляю лучший ключ
        return selectBestKey(voteMap);
    }

    /**
     * Преобразует список строк в массив символов
     * @param lines список строк
     * @return массив символов
     */
    private static char[] convertLinesToChars(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
        }
        return sb.toString().toCharArray();
    }

    /**
     * Подсчитывает частоту встречаемости каждой буквы в тексте
     * @param text массив символов (зашифрованный текст)
     * @return Map, где ключ — буква, значение — количество вхождений
     */
    private static Map<Character, Integer> countLetterFrequencies(char[] text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : text) {
            char lowerChar = Character.toLowerCase(c);

            if (isRussianLetter(lowerChar)) {
                frequencyMap.merge(lowerChar, 1, Integer::sum);
            }
        }
        return frequencyMap;
    }

    private static boolean isRussianLetter(char c) {
        return (c >= 'а' && c <= 'я') || c == 'ё';
    }

    /**
     * Возвращает список самых частых букв в порядке убывания частоты
     * @param frequencyMap карта частот букв
     * @param limit количество букв, которые нужно вернуть
     * @return список букв, отсортированный по убыванию частоты
     */
    private static List<Character> getMostFrequentLetters(Map<Character, Integer> frequencyMap, int limit) {
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed()) // Сортирую по убыванию количества вхождений
                .limit(limit)                                                        // Беру только первые limit букв
                .map(Map.Entry::getKey)                                              // Преобразую Entry в букву
                .collect(Collectors.toList());                                       // Собираю в список
    }

    /**
     * Проводит голосование: для каждой пары (частная буква шифротекста → эталонная буква)
     * вычисляется возможный сдвиг, и за этот сдвиг отдаётся голос
     * @param topCipherLetters самые частые буквы из шифротекста
     * @param alphabet экземпляр вашего класса RuAlphabet
     * @return карта голосования: ключ (сдвиг) → количество голосов
     */
    private static Map<Integer, Integer> performVoting(List<Character> topCipherLetters, RuAlphabet alphabet) {
        Map<Integer, Integer> voteMap = new HashMap<>();

        int alphabetSize = alphabet.getSizeRuChar();

        for (int i = 0; i < topCipherLetters.size() && i < TOP_RUSSIAN_LETTERS.size(); i++) {
            char cipherChar = topCipherLetters.get(i);      // Частая буква из шифротекста
            char expectedChar = TOP_RUSSIAN_LETTERS.get(i); // Ожидаемая буква (из эталона)

            int cipherIndex = alphabet.getIndex(cipherChar);
            int expectedIndex = alphabet.getIndex(expectedChar);

            // Вычисляю сдвиг: какой сдвиг превратил expectedChar в cipherChar
            // Формула: cipherIndex = (expectedIndex + shift) % alphabetSize
            // Отсюда: shift = (cipherIndex - expectedIndex + alphabetSize) % alphabetSize
            int shift = Math.floorMod(cipherIndex - expectedIndex, alphabetSize);

            // Отдаю голос за этот сдвиг
            voteMap.merge(shift, 1, Integer::sum);
        }

        return voteMap;
    }

    private static int selectBestKey(Map<Integer, Integer> voteMap) {
        return voteMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0);
    }
}
