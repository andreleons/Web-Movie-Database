package rebus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Rebus {
	
	Rebus() {
		Config.entireCollection = new BigWordCollection();
		Config.entireCollection.printWordStrengths();
		//Set Config.gameCollection to the appropriate settings
		generateWordBank();
	}
	
	//Sets Config.gameCollection to the appropriate settings
	public void generateWordBank() {
		BigWordCollection temp = Config.entireCollection.getBigWordCollectionByTopic(Config.wordBankTopic);
		Config.gameCollectionWordBank = temp;
	}
	
	public void generateSolutionBank() {
		BigWordCollection temp = Config.entireCollection.getBigWordCollectionByTopic(Config.solutionBankTopic);
		BigWordCollection teluguSolutions = new BigWordCollection();
		teluguSolutions.getAllBigWords().clear();
		WordProcessor word;
		//0 Represents any solution length
		if (Config.solutionLength != 0 && Config.LANGUAGE.equals("En")) {
			temp = temp.getBigWordCollectionByWordLength(Config.solutionLength);
			
		} else if (Config.solutionLength != 0 ){
			for(BigWord bigWord: temp.getAllBigWords()){
				word = new WordProcessor(bigWord.getTelugu());
				if(word.getLogicalChars().size() == Config.solutionLength){
					teluguSolutions.addBigWord(bigWord);
					System.out.println("Logical Size during selection: "+word.getLogicalLength());
				}
			}
			System.out.println("Telugu List Size: "+teluguSolutions.size());
			temp = teluguSolutions;
		}
		temp = temp.getBigWordCollectionByWordStrength(Config.solutionBankWordStrengthMin, Config.solutionBankWordStrengthMax);
		Config.gameCollectionSolutionBank = temp;
	}
	
	public void findGameWords(){
		BigWordCollection words = Config.gameCollectionWordBank;
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
	
	public void pickSolutionWord(){
		BigWordCollection possibleSolutions = Config.gameCollectionSolutionBank;
		Random rand = new Random();
		BigWord solution;
		WordProcessor word;
	
		
		if (possibleSolutions.size() > 1) {
			solution = possibleSolutions.getBigWord(rand.nextInt(possibleSolutions.size()-1)+1);
		}
		else {
			solution = possibleSolutions.getBigWord(0);
		}
		
		if(Config.LANGUAGE.equals("En")){
			word = new WordProcessor(solution.getEnglish());
			Config.solutionWord = word.getLogicalChars() ;
		} else {
			word = new WordProcessor(solution.getTelugu());
			System.out.println("Logical Length: "+ word.getLogicalLength());
			Config.solutionWord = word.getLogicalChars();
			System.out.println("Size: "+Config.solutionWord.size());
		}
		System.out.println("Solution Word: "+word.getWord());
	}
}
