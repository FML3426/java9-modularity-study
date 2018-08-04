package blog.ml3426.easytext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    
    private final static Set<Character> vowelSets = new HashSet<>() { {
        add('a');
        add('e');
        add('i');
        add('o');
        add('u');
    } };

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Welcome to EasyText. Please provide a filename as input argument.");
            return;
        }

        final Path filePath = Paths.get(args[0]);
        System.out.println("Reading " + filePath);
        final String text = new String(Files.readAllBytes(filePath), Charset.forName("UTF-8"));
        System.out.println("Flesch-Kincaid: " + analyze(toSentences(text)));
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
    
    private static double analyze(final List<List<String>> sentences) {
        final int totalSentences = sentences.size();
        final int totalWords = sentences.stream().mapToInt(List::size).sum();
        final int totalSyllables = sentences.stream().mapToInt(sentence -> sentence.stream()
                .mapToInt(Main::countSyllables).sum()).sum();
        return 206.835 - 1.015 * ((double) totalWords / (double) totalSentences) - 
                84.6 * ((double) totalSyllables / (double) totalWords);
    }

    private static int countSyllables(final String word) {
        int syllables = 0;
        boolean prevNonVowel = false;
        final String lowerWord = word.toLowerCase();
        for(int i = 0; i < lowerWord.length(); i++) {
            boolean isVowel = vowelSets.contains(lowerWord.charAt(i));
            if(prevNonVowel && isVowel && i != lowerWord.length() - 1) {
                syllables++;
            }
            prevNonVowel = !isVowel;
        }
        return syllables == 0 ? 1 : syllables;
    }
}
