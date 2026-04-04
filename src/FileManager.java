import Exception.FileProcessException;
import Validator.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileManager{

    public List<String> fileReader(String fileName)  {
        FileNameValidator.validateForReading(fileName);
        List<String> result = new ArrayList<>();

        try(BufferedReader reader = Files.newBufferedReader(Path.of(fileName), StandardCharsets.UTF_8)) {
            String line;
            while((line = reader.readLine()) != null){
                result.add(line);
            }
        } catch(IOException | InvalidPathException e){
            throw new FileProcessException(e.getMessage(), e);
        }
        return result;
    }

    public void fileWriter(String fileName, List<String> data){
        FileNameValidator.validateForWriting(fileName);
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(fileName), StandardCharsets.UTF_8)){
            for(String string: data){
                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }
        } catch (IOException | InvalidPathException e) {
            throw new FileProcessException(e.getMessage(), e);
        }
    }
}
