package rebus;

import java.util.ArrayList;

/**
 * Produces the game board
 * @author marsh_000
 *
 */
public class HtmlGameProducer {
	
	
	/**
	 * Build the game board
	 * @return
	 */
	public static String buildBoard(){
		String table = "<table>\n";
		table += generateGameSection();
		table += "</table>";
		return table;
	}
	public static String buildBoardFromSavedGame(SavedGame game){
		String table = "<table>\n";
		table += generateGameSectionFromSavedGame(game);
		table += "</table>";
		return table;
	}
	private static String generateGameSection(){
		ArrayList<BigWord> words = Config.gameBigWords;
		String gameRow = "<tr>";
		for(int i =0; i < words.size(); i++){
			gameRow += "\n<td>";
				if(!words.get(i).hasImage()){
					System.out.println("searching internet for picture of " + words.get(i).getProcessedWord());
					words.get(i).setImage(ImageSearch.getImageUrl(words.get(i).getProcessedWord()));
					gameRow += "<img src= ' "+words.get(i).getImage()+"' style='height:200px;width:200px;'/>";
				}else{
					gameRow += "<img src= ' "+ImageSearch.imageVerifcation(words.get(i).getImage())+"' style='height:200px;width:200px;'/>";
				}
			
			gameRow += "</td>\n";
		}
		String word = "";
		for(String letter: Config.solutionWord){
			word += letter;
		}
		gameRow+= "<td><img src= '"+ImageSearch.getImageUrl(word)+"' style='height:200px;width:200px;'/></td>";
		return gameRow += "</tr>";
	}
	private static String generateGameSectionFromSavedGame(SavedGame game){
		ArrayList<BigWord> words = game.getGameWords();
		String gameRow = "<tr>";
		for(int i =0; i < words.size(); i++){
			gameRow += "\n<td>";
			//just in case word makes it through without image
				if(!words.get(i).hasImage()){
					words.get(i).setImage(ImageSearch.getImageUrl(words.get(i).getProcessedWord()));
					gameRow += "<img src= ' "+words.get(i).getImage()+"' style='height:200px;width:200px;'/>";
				}else{
					System.out.println("Had Image of " + words.get(i).getProcessedWord());
					gameRow += "<img src= ' "+ImageSearch.imageVerifcation(words.get(i).getImage())+"' style='height:200px;width:200px;'/>";
				}
			
			gameRow += "</td>\n";
		}
		
		gameRow+= "<td><img src= '"+ImageSearch.getImageUrl(game.getSolutionWord())+"' style='height:200px;width:200px;'/></td>";
		return gameRow += "</tr>";
	}

}
