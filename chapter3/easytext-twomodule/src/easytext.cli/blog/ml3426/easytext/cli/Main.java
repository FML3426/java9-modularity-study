package blog.ml3426.easytext.cli;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import blog.ml3426.easytext.analysis.FleschKincaid;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Welcome to EasyText. Please provide a filename as input argument.");
            return;
        }

        final Path filePath = Paths.get(args[0]);
        System.out.println("Reading " + filePath);
        final String text = new String(Files.readAllBytes(filePath), Charset.forName("UTF-8"));
        System.out.println("Flesch-Kincaid: " + FleschKincaid.analyze(toSentences(text)));
    }

    private static List<List<String>> toSentences(final String text) {
        final String removedBreaks = text.replaceAll("\\r?\\n", " ");
        return Stream.of(removedBreaks.split("[.?!]"))
                .map(Main::toWords)
                .filter(words -> !words.isEmpty())
                .collect(Collectors.toList());
    }

    private static List<String> toWords(final String sentence) {
        return Stream.of(sentence.split("\\s+"))
                .map(rawWord -> rawWord.replaceAll("\\W", ""))
                .collect(Collectors.toList());
    }
}
