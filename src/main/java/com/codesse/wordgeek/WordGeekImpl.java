package com.codesse.wordgeek;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the shell implementation of the WordGeek interface.
 * It is the class that you should focus on when developing your solution to the Challenge.
 */
public class WordGeekImpl implements WordGeek {
    private final String reallyLongWord;
    private final ValidWords validWords;
    List<Entry> leaderBoard = new ArrayList<>();
    List<String> enteredWord = new ArrayList<>();


    static class Entry {
        String playerName;
        Integer playerScore;
        String word;
    }
    static class leaderBoardComparator implements Comparator<Entry>{
        @Override
        public int compare(Entry o1, Entry o2) {
            return o2.playerScore.compareTo(o1.playerScore);
        }
    }

    public WordGeekImpl(String reallyLongWord, ValidWords validWords) {
        this.reallyLongWord = reallyLongWord;
        this.validWords = validWords;
    }

    @Override
     public synchronized int submitWord(String playerName, String word){
        if (enteredWord.contains(word)){
            return 0;
        }
        Entry entry = new Entry();
        entry.playerName = playerName;
        entry.playerScore = 0;
        entry.word = word;
        // validate word and calculate score;
        String [] split = word.split("");

        List<String> container = new ArrayList<>(List.of(reallyLongWord.split("")));


        for (int i = 0; i < split.length; i++) {
            if (container.contains(split[i])) {
                container.remove(split[i]);
            }else{
                return 0;
            }
        }
        if(validWords.contains(word)){
            entry.playerScore = word.length();
            leaderBoard.add(entry);
            enteredWord.add(word);
            leaderBoard = leaderBoard.stream().sorted(new leaderBoardComparator()).limit(10).collect(Collectors.toList());
            return word.length();
        }
        return 0;
    }

    @Override
    public String getPlayerNameAtPosition(int position) {
        return leaderBoard.get(position).playerName;
    }

    @Override
    public String getWordEntryAtPosition(int position) {
        return leaderBoard.get(position).word;
    }

    @Override
    public Integer getScoreAtPosition(int position) {
        return leaderBoard.get(position).playerScore;
    }
}
