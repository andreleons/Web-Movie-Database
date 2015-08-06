package rebus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable{

	private String solutionWord;
	private ArrayList<BigWord> gameWords;
	
	
	public SavedGame(ArrayList<String> solutionWord2, ArrayList<BigWord> gameWords){
		setSolutionWord(solutionWord2);
		this.gameWords = gameWords;
		
	}

	public String getSolutionWord() {
		return solutionWord;
	}

	public void setSolutionWord(ArrayList<String> solutionWord) {
		String word = "";
		for(String letter : solutionWord){
			word +=letter;
		}
		this.solutionWord = word;
	}

	public ArrayList<BigWord> getGameWords() {
		return gameWords;
	}

	public void setGameWords(ArrayList<BigWord> gameWords) {
		this.gameWords = gameWords;
	}

	
}
