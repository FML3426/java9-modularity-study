package blog.ml3426.easytext.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FleschKincaid {

    private final static Set<Character> vowelSets = new HashSet<>() { {
        add('a');
        add('e');
        add('i');
        add('o');
        add('u');
    } };

    public static double analyze(final List<List<String>> sentences) {
        final int totalSentences = sentences.size();
        final int totalWords = sentences.stream().mapToInt(List::size).sum();
        final int totalSyllables = sentences.stream().mapToInt(sentence -> sentence.stream()
                .mapToInt(FleschKincaid::countSyllables).sum()).sum();
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
