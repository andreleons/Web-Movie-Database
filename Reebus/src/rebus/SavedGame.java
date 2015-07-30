package rebus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class SavedGame implements Serializable{

	private ArrayList<String> solutionWord;
	private ArrayList<BigWord> gameWords;
	
	
	public SavedGame(ArrayList<String> solutionWord2, ArrayList<BigWord> gameWords){
		this.solutionWord = solutionWord2;
		this.gameWords = gameWords;
		
	}

	public ArrayList<String> getSolutionWord() {
		return solutionWord;
	}

	public void setSolutionWord(ArrayList<String> solutionWord) {
		this.solutionWord = solutionWord;
	}

	public ArrayList<BigWord> getGameWords() {
		return gameWords;
	}

	public void setGameWords(ArrayList<BigWord> gameWords) {
		this.gameWords = gameWords;
	}

	
}
