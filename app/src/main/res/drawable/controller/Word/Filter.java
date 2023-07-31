package com.example.memorengandroid.controller.Word;

import com.example.memorengandroid.model.EnglishWord;
import com.example.memorengandroid.model.EnglishWords;

import java.util.Locale;

public class Filter {
    EnglishWords englishWordList = EnglishWords.getInstance();

    public String filterString(String str) {
        String resultText = "";

        str = str.toUpperCase(Locale.ROOT);

        if (str.equals("")) {
            return "";
        } else {
            int resultCount = 0;

            if (englishWordList.getAllWords() != null) {
                for (EnglishWord myWord: englishWordList.getAllWords()) {
                    if (resultCount < 4 && (myWord.getWord().toUpperCase(Locale.ROOT).contains(str) ||
                            myWord.getTranslations().get(0).toUpperCase(Locale.ROOT).contains(str))) {

                        resultText += myWord.getWord() + " = " +
                                myWord.getTranslations().get(0)
                                        .replaceAll("\"", "") + "\n\n";

                        resultCount++;
                    }
                }
            }
        }

        return resultText;
    }
}
