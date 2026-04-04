package Validator;

import Exception.FileProcessException;

import java.nio.file.*;
import java.util.List;
import java.util.regex.Pattern;

public class FileNameValidator {
    private static final List<String> FORBIDDEN_DIR_FILES = List.of(".bash_history",
            ".bash_profile",
            ".bashrc",
            "etc",
            "proc",
            "System32",
            "Windows");


    /**
     * Проверка указанного пути на:
     * 1. наличие файла
     * 2. путь указывает на файл, а не на директорию
     * @param fileName
     * @return
     */
    public static Path validateForFile(String fileName){
        Path path = validatePath(fileName);
        if(!Files.exists(path)){
            throw new FileProcessException(String.format("По указанному пути %s файла не существует", fileName));
        }

        if(Files.isDirectory(path)){
            throw new FileProcessException(String.format("По указанному пути %s находится директория, а не файл", fileName));
        }
        return path;
    }

    /**
     * Проверяет, содержит ли указанный путь
     * системные файлы
     * @param fileName
     * @return
     */
    public static Path validatePath(String fileName){
        String stringSeparator = Pattern.quote(FileSystems.getDefault().getSeparator());
        for(String str: fileName.split(stringSeparator)){
            if (FORBIDDEN_DIR_FILES.contains(str)){
                throw new FileProcessException("Введённый путь содержит системный фрагмент " + str);
            }
        }
        try{
            return Path.of(fileName);
        } catch(InvalidPathException e){
            throw new FileProcessException("Недопустимый путь " + fileName, e);
        }
    }

    /**
     * Проверка на доступ для записи
     * @param fileName
     */
    public static void validateForWriting(String fileName){
        Path path = validateForFile(fileName);

        if (!Files.isWritable(path)){
            throw new FileProcessException(String.format("По указанному пути %s файл недоступен для записи", fileName));
        }
    }

    /**
     * Проверка на доступ для чтения
     * @param fileName
     */
    public static void validateForReading(String fileName){
        Path path = validateForFile(fileName);

        if(!Files.isReadable(path)){     // isReadable - true, если доступен для чтения
            throw new FileProcessException(String.format("По указанному пути %s файл недоступен для чтения", fileName));
        }
    }


}
