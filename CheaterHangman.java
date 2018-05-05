
package cheaterhangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class CheaterHangman {
    
    public int sizeOfWord;
    public int numOfWrongGuessesAllowed;
    public int numOfWrongGuessesMade;
    public ArrayList<String> listOfAllWords;
    public HashMap<String, Integer> mapOfAllWords;
    String wordToDisplay;
    boolean gameOver;
    String hiddenWord;
    public int numOfWrongGuessesLeft;
    boolean playAgain;
    
    public CheaterHangman(){
        sizeOfWord = 0;
        numOfWrongGuessesAllowed = 0;
        numOfWrongGuessesMade = 0;
        listOfAllWords = new ArrayList<String>();
        mapOfAllWords = new HashMap<String, Integer>();
        String wordToDisplay = "";
        gameOver = false;
        hiddenWord = "";
        numOfWrongGuessesLeft = 0;
        playAgain = true;
        
    }
    
    public int getSizeOfWord(HashMap<String, Integer> mapOfAllWords){
        boolean doneSizeChoosing = false;
        boolean validEntry = false;
        
        while(!doneSizeChoosing){
            while(!validEntry){
                try{
                    System.out.println("Enter size of the word you want to try to guess:");
                    Scanner scanner = new Scanner(System.in);
                    this.sizeOfWord = scanner.nextInt();

                    if(!(this.mapOfAllWords.containsValue(this.sizeOfWord))){
                        System.out.println("No words of that size, please enter another number: ");
                    } else {
                        doneSizeChoosing = true;
                        validEntry = true;
                    }
                } catch (InputMismatchException e){
                    System.out.println("Invalid entry. Please enter a number.");
                }
            }
        }
        return this.sizeOfWord;
    }
    
    public ArrayList<String> createListOfSizeWords(ArrayList<String> prevList, int sizeOfWord){
        ArrayList<String> listOfSizeWords = new ArrayList<String>();
        for (int i = 0; i < prevList.size(); i++){
            if(prevList.get(i).length() == sizeOfWord){              
                listOfSizeWords.add(prevList.get(i));
            }
        }
        return listOfSizeWords;
    }
    
    public void checkUserGuess(HashMap<String, String> mapOfWordsAndBlankedWords, char userLetter, String wordFamilyWithLargestNumberOfWords){
        boolean correctGuess = false;
        for(String word: mapOfWordsAndBlankedWords.keySet()){
            String value = mapOfWordsAndBlankedWords.get(word);
            if(value.endsWith(wordFamilyWithLargestNumberOfWords)){
                if ((word.contains("" + userLetter))){
                correctGuess = true;
                }
            }
        }        
        if (correctGuess == false){
            System.out.println("Sorry that was a wrong guess.");
            this.numOfWrongGuessesMade++;
        }
    }
    
    public boolean userGuessValid(HashMap<String, String> mapOfWordsAndBlankedWords, char userLetter, String wordFamilyWithLargestNumberOfWords){
        boolean correctGuess = false;
        for(String word: mapOfWordsAndBlankedWords.keySet()){
            String value = mapOfWordsAndBlankedWords.get(word);
            if(value.endsWith(wordFamilyWithLargestNumberOfWords)){
                if ((word.contains("" + userLetter))){
                correctGuess = true;
                }
            }
        }
        return correctGuess;
    }
    
    public char getUserGuess(){       
        boolean validEntry = false;
        char userLetter = '\0';
        
        while(!validEntry){
            System.out.println("Please guess a letter: ");       
            Scanner scanner = new Scanner(System.in);
            String userGuess = scanner.next();
            if(userGuess.matches("[a-zA-Z]")){ 
                char [] userGuessLetterArr = userGuess.toLowerCase().toCharArray();
                userLetter = userGuessLetterArr[0];
                validEntry = true;            
            } else { 
                System.out.println("Invalid entry. Please enter a letter."); 
            } 
        }
        return userLetter;
        
    }
    
    public ArrayList<String> createListOfBlankedWords(ArrayList<String> prevListOfSizeWords, char userLetter){
        ArrayList<String> listOfBlankedWords = new ArrayList<String>();
        for(int i = 0; i < prevListOfSizeWords.size(); i++){
            String currentWordWBlanks = "";
            String word = prevListOfSizeWords.get(i);
            char [] chars = word.toCharArray();
            for(char c: chars){
                if(c == userLetter){
                    currentWordWBlanks+= userLetter;
                } else {
                    currentWordWBlanks+= "-";
                }
            }
        listOfBlankedWords.add(currentWordWBlanks);      
    }
        return listOfBlankedWords;
    }
    
    public HashMap<String, String> createMapOfWordsAndBlankedWords(ArrayList<String> prevListOfSizeWords, char userLetter){      
        HashMap<String, String> mapOfWordsAndBlankedWords = new HashMap<String, String>();
        for(int i = 0; i < prevListOfSizeWords.size(); i++){
            String currentWordWBlanks = "";
            String word = prevListOfSizeWords.get(i);
            char [] chars = word.toCharArray();
            for(char c: chars){
                if(c == userLetter){
                    currentWordWBlanks+= userLetter;
                } else {
                    currentWordWBlanks+= "-";
                }
            }
            mapOfWordsAndBlankedWords.put(prevListOfSizeWords.get(i), currentWordWBlanks);
        }
        return mapOfWordsAndBlankedWords;
    }
     
    public HashMap<String, Integer> createMapOfBlankedWordsAndOccurences(ArrayList<String> prevListOfBlankedWords){
        HashMap<String, Integer> mapOfBlankedWordsAndOccurences = new HashMap<String, Integer>();        
        for(int i = 0; i < prevListOfBlankedWords.size(); i++){
            int count = Collections.frequency(prevListOfBlankedWords, prevListOfBlankedWords.get(i));
            mapOfBlankedWordsAndOccurences.put(prevListOfBlankedWords.get(i), count);
        }   
        return mapOfBlankedWordsAndOccurences;
    }
    
    public int getMaxNumOfWordsForWordFamWithLargestNumOfWords (HashMap<String, Integer> prevMapOfBlankedWordsAndOccurences){
        int maxNumberOfWords = Integer.MIN_VALUE;
        for (int n: prevMapOfBlankedWordsAndOccurences.values()){
            if(n > maxNumberOfWords){
                maxNumberOfWords = n;
            }              
        }
        return maxNumberOfWords;
    }
    
    public HashMap<Integer, String> createMapOfWordFamiliesByOccurences(HashMap<String, Integer> prevMapOfBlankedWordsAndOccurences){
        HashMap<Integer, String> mapOfWordFamiliesByOccurences = new HashMap <Integer, String>();
        for(String s: prevMapOfBlankedWordsAndOccurences.keySet()){
            mapOfWordFamiliesByOccurences.put(prevMapOfBlankedWordsAndOccurences.get(s), s);
        }
        
        return mapOfWordFamiliesByOccurences;
    }

    public String getWordFamilyWithLargestNumOfWords(HashMap<Integer, String> prevMapOfWordFamiliesByOccurences, int maxNumberOfWords){
        String wordFamilyWithLargestNumOfWords = "";
        for(int n: prevMapOfWordFamiliesByOccurences.keySet()){
            if(n == maxNumberOfWords){
                wordFamilyWithLargestNumOfWords += prevMapOfWordFamiliesByOccurences.get(n);
            }
        }
        return wordFamilyWithLargestNumOfWords;
    }
    
    public ArrayList<String> getWordListBasedOnChosenWordFamily(HashMap<String, String> prevMapOfWordsAndBlankedWords, String wordFamilyWithLargestNumberOfWords){
            ArrayList<String> wordListBasedOnChosenWordFamily = new ArrayList<String>();
            for(String s: prevMapOfWordsAndBlankedWords.keySet()){
                if(prevMapOfWordsAndBlankedWords.get(s).equals(wordFamilyWithLargestNumberOfWords)){
                    wordListBasedOnChosenWordFamily.add(s);
                }
            }
            return wordListBasedOnChosenWordFamily;
    }
    
    public String updateWordToDisplayInitial(int sizeOfWord){
        String word = "";
        for(int i = 0; i < sizeOfWord; i++){
            word += "-";
        }
        this.wordToDisplay = word;
        return word;
    }
   
    public String updateWordToDisplayWUserLetter(String wordToDisplay, char userLetter, String wordFamily){
        int index = 0;
        char [] wordFamilyArray = wordFamily.toCharArray();
        for(int i = 0; i < wordFamilyArray.length; i++){
            if (wordFamilyArray[i] != '-'){
                index = i;
            }
        }
        
        char [] wordArray = wordToDisplay.toCharArray();       
        for(int i = 0; i < wordArray.length; i++){
            if (i == index){
                wordArray[index] = userLetter;
            }
        }

        String word = String.valueOf(wordArray);
        return word;

    }
    
    public void displayGame(String wordToDisplay){
        System.out.println("Word: " + wordToDisplay);
        this.numOfWrongGuessesLeft = this.numOfWrongGuessesAllowed - this.numOfWrongGuessesMade;
        if (!(this.numOfWrongGuessesLeft < 0)){
            System.out.println("Number of wrong guesses left: " + (this.numOfWrongGuessesLeft));
        } else {
            System.out.println("You lost. The word was: " + this.hiddenWord);
            System.out.println("Would you like to play again?");
            Scanner scan = new Scanner(System.in);
            String ans = scan.next();
            if(ans.equalsIgnoreCase("yes")){
                playAgain = true;
            } else {
                playAgain = false;
            }
        }
    }
    
    public int getNumOfWrongGuessesAllowed(){
        boolean validEntry = false;
        while(!validEntry){
            try{
                System.out.println("Please enter the number of wrong guesses you would like to have before you lose: ");
                Scanner scanner = new Scanner(System.in);
                this.numOfWrongGuessesAllowed = scanner.nextInt();
                validEntry = true;
            } catch (InputMismatchException e){
                System.out.println("Invalid entry. Please enter a number.");
            }
        }
        return this.numOfWrongGuessesAllowed;
        
    }
    
    public static void main(String[] args) {
        
        
        CheaterHangman game = new CheaterHangman();
        while(game.playAgain){
            try {
                Scanner scanner1 = new Scanner(new File("dictionary.txt"));
                    while(scanner1.hasNext()){
                        String word = scanner1.next();
                        game.listOfAllWords.add(word);
                        game.mapOfAllWords.put(word, word.length());
                    }
                        scanner1.close();			
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            //Start Game
            // Get size of word
            int size = game.getSizeOfWord(game.mapOfAllWords);
            //Get number of wrong guesses allowed
            game.getNumOfWrongGuessesAllowed();

            //created list of size words
            ArrayList<String> listOfSizeWords = new ArrayList<String>();
            listOfSizeWords = game.createListOfSizeWords(game.listOfAllWords, size);
                
            String wordToDisplay = game.updateWordToDisplayInitial(size);
            System.out.println("Initial: " + wordToDisplay);

            while(game.numOfWrongGuessesMade <= game.numOfWrongGuessesAllowed){
               
                if (!(wordToDisplay.contains("-"))){
                    System.out.println("You win");
                    game.gameOver = true;
                    return;
                }      
                               
                // THIS IS WHERE THE USER IS ASKED TO GUESS
                char userLetter = game.getUserGuess();

                //CREATE A MAP WITH WORD AND BLANKED WORDS
                HashMap<String, String> mapOfWordsAndBlankedWords = new HashMap<String, String>();
                mapOfWordsAndBlankedWords = game.createMapOfWordsAndBlankedWords(listOfSizeWords, userLetter);
                
                //CREATE LIST OF BLANKED WORDS    
                ArrayList<String> listOfBlankedWords = new ArrayList<String>();
                listOfBlankedWords = game.createListOfBlankedWords(listOfSizeWords, userLetter);               

                // CREATE A MAP OF BLANKEDWORDS/FAMS AND OCCURENCES OF EACH BLANKED WORD/FAM        
                HashMap<String, Integer> mapOfBlankedWordsAndOccurences = new HashMap<String, Integer>();
                mapOfBlankedWordsAndOccurences = game.createMapOfBlankedWordsAndOccurences(listOfBlankedWords);

                //CREATE A MAP OF WORD FAMILIES BY OCCURENCE
                HashMap<Integer, String> mapOfWordFamiliesByOccurences = new HashMap<Integer, String>();
                mapOfWordFamiliesByOccurences = game.createMapOfWordFamiliesByOccurences(mapOfBlankedWordsAndOccurences);

                //GET MAX NUMBER OF WORDS FOR THE WORD FAMILY WITH MOST WORDS
                int maxNumberOfWords;
                maxNumberOfWords = game.getMaxNumOfWordsForWordFamWithLargestNumOfWords (mapOfBlankedWordsAndOccurences);

                //get word fam with largest nuuvebr of words
                String wordFamilyWithLargestNumberOfWords = "";
                wordFamilyWithLargestNumberOfWords = game.getWordFamilyWithLargestNumOfWords(mapOfWordFamiliesByOccurences, maxNumberOfWords);

                //GET NEW LIST OF WORDS BASED ON CHOSEN WORD FAM
                ArrayList <String> wordListBasedOnChosenWordFamily = new ArrayList<String>();
                wordListBasedOnChosenWordFamily = game.getWordListBasedOnChosenWordFamily(mapOfWordsAndBlankedWords, wordFamilyWithLargestNumberOfWords);
                
                game.checkUserGuess(mapOfWordsAndBlankedWords, userLetter, wordFamilyWithLargestNumberOfWords);
                
                if (game.userGuessValid(mapOfWordsAndBlankedWords, userLetter, wordFamilyWithLargestNumberOfWords) == true){
                    wordToDisplay = game.updateWordToDisplayWUserLetter(wordToDisplay, userLetter, wordFamilyWithLargestNumberOfWords);
                }
                game.displayGame(wordToDisplay);
                             
                listOfSizeWords.clear();               
                listOfSizeWords.addAll(wordListBasedOnChosenWordFamily);
                if(!(listOfSizeWords.isEmpty())){
                    Random r = new Random();
                    int index = r.nextInt(listOfSizeWords.size());
                    String www = listOfSizeWords.get(index);
                    game.hiddenWord = www;
                }           
            }
        }
    }
}
        

    

