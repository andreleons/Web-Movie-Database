package rebus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Rebus {
	
	Rebus() {
		Config.entireCollection = new BigWordCollection();
		//Set Config.gameCollection to the appropriate settings
		generateWordPool();
	}
	
	//Sets Config.gameCollection to the appropriate settings
	public void generateWordPool() {
		Config.topic = "Any";
		Config.LANGUAGE = "En";
		Config.rebusX = 1;
		Config.solutionLength = 3;
		Config.wordStrength = 1;
		Config.MAX_WORD_LENGTH = 10;
		BigWordCollection temp = Config.entireCollection.getBigWordCollectionByTopic(Config.topic);
		Config.gameCollection = temp;
		pickSolutionWord(temp);
		findGameWords(temp);
	}
	
	private void findGameWords(BigWordCollection words){
		BigWordCollection possibleWords = words.getBigWordCollectionByWordStrength(Config.wordStrength);
		BigWordCollection trimmedWords = possibleWords.getBigWordCollectionByWordLength(Config.rebusX, Config.MAX_WORD_LENGTH);
		ArrayList<ArrayList<String>> readableWords = new ArrayList<ArrayList<String>>();
		WordProcessor word;
		//generate all the parsed words
		for(BigWord bigWord: trimmedWords.getAllBigWords()){
			if(Config.LANGUAGE.equals("En")){
				word = new WordProcessor(bigWord.getEnglish());
				readableWords.add(word.getLogicalChars());
			} else {
				word = new WordProcessor(bigWord.getTelugu());
				readableWords.add(word.getLogicalChars());
			}
		}
		ArrayList<ArrayList<String>> gameWords = new ArrayList<ArrayList<String>>();
		for(int i = 0; i< Config.solutionLength;i++){
			//create some randomness for different boards to be made from same solution word
			Collections.shuffle(readableWords);
			for(ArrayList<String> temp: readableWords){
				if(temp.get(Config.rebusX-1).equals(Config.solutionWord.get(i)))
				{
					word = new WordProcessor(temp);
					System.out.println(word.getWord());
					gameWords.add(temp);
					break;
				}
			}
			
		}
		
		
	}
	
	private void pickSolutionWord(BigWordCollection words){
		BigWordCollection possibleSolutions = words.getBigWordCollectionByWordLength(Config.solutionLength);
		Random rand = new Random();
		BigWord solution = possibleSolutions.getBigWord(rand.nextInt(possibleSolutions.size()-1)+1);
		
		WordProcessor word;
		if(Config.LANGUAGE.equals("En")){
			word = new WordProcessor(solution.getEnglish());
			Config.solutionWord = word.getLogicalChars() ;
		} else {
			word = new WordProcessor(solution.getTelugu());
			Config.solutionWord = word.getLogicalChars();
		}
		
		System.out.println("Solution Word: "+word.getWord());
	}
}
