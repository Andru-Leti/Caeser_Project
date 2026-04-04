import Alphabet.RuAlphabet;
import Cipher.CaesarCipher;
import Exception.*;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final String INFORMATION = "Привет, в этом приложении ты можешь: \n" +
                                              "1. Зашифровать файл\n" +
                                              "2. Расшифровать файл\n" +
                                              "3. Выход";

    private static final String SWITCH_MODE = "\nВыбери режим работы, введя номер от 1 до 3: ";

    static void main() {
        int key;
        int mode = 0;
        StringBuilder inputFile = new StringBuilder();
        StringBuilder outputFile = new StringBuilder();
        List<String> data;
        List<String> result;

        FileManager file = new FileManager();
        RuAlphabet alphabet = new RuAlphabet();
        CaesarCipher cipher = new CaesarCipher(alphabet);

        Scanner scanner = new Scanner(System.in);

        System.out.println(INFORMATION);

        while(mode != 3){
           try{
                System.out.println(SWITCH_MODE);
                mode = scanner.nextInt();
                scanner.nextLine();

                switch (mode) {
                    case 1:
                        System.out.println("Введите ключ для шифра Цезаря от 0 до 32");
                        key = scanner.nextInt();
                        scanner.nextLine();

                        while (key < 0 || key > 32) {
                            System.out.println("Некоректный ключ " + key + "\n Допустимые значения от 0 до 32");
                            System.out.println("\nВведите заново");
                            key = scanner.nextInt();
                            scanner.nextLine();
                        }
                        System.out.println("Введите путь к входному файлу:");
                        inputFile.append(scanner.nextLine());

                        System.out.println("Введите путь к выходному файлу:");
                        outputFile.append(scanner.nextLine());

                        System.out.println("Начинаю шифрование...");
                        data = file.fileReader(inputFile.toString());
                        result = cipher.encryption(data, key);
                        file.fileWriter(outputFile.toString(), result);
                        System.out.println("Шифрование завершено\n" +
                                "Результаты в файле " + outputFile);

                        data.clear();
                        result.clear();
                        inputFile.setLength(0);
                        outputFile.setLength(0);
                        break;
                    case 2:
                        System.out.println("Введите ключ для дешифрации от 0 до 32");
                        key = scanner.nextInt();
                        scanner.nextLine();

                        while (key < 0 || key > 32) {
                            System.out.println("Некоректный ключ " + key + "\n Допустимые значения от 0 до 32");
                            System.out.println("\nВведите заново");
                            key = scanner.nextInt();
                            scanner.nextLine();
                        }
                        System.out.println("Введите путь к входному файлу:");
                        inputFile.append(scanner.nextLine());

                        System.out.println("Введите путь к выходному файлу:");
                        outputFile.append(scanner.nextLine());

                        System.out.println("Начинаю дешифрование...");
                        data = file.fileReader(inputFile.toString());
                        result = cipher.decryption(data, key);
                        file.fileWriter(outputFile.toString(), result);
                        System.out.println("Дешифрование завершено\n" +
                                "Результаты в файле " + outputFile);

                        data.clear();
                        result.clear();
                        inputFile.setLength(0);
                        outputFile.setLength(0);
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        break;
                    default:
                        System.out.println("Неверный режим. Введите число от 1 до 3");
                }
            } catch (FileProcessException | AlphabetException e){
               System.out.println("   " + e.getMessage());
            } catch (Exception e){
               System.out.println("Ошибка ввода!");
               scanner.nextLine();
               mode = 0;
           }
        }
    }


}
