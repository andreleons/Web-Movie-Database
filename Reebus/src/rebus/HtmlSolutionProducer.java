package rebus;

import java.util.ArrayList;

public class HtmlSolutionProducer {

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
	private static String generateGameSectionFromSavedGame(SavedGame game){
		ArrayList<BigWord> words = game.getGameWords();
		String gameRow = "<tr>";
		for(int i =0; i < words.size(); i++){
			gameRow += "\n<td>";
			
				gameRow += "<div style='line-height:200px;text-align:center; vertical-align: middle; height:200px;width:200px;'>"+words.get(i).getProcessedWord() +"</div>";
			
			gameRow += "</td>\n";
		}
		gameRow+= "<td><div>Solution: "+game.getSolutionWord()+" </div></td>";
		return gameRow += "</tr>";
	}
	
	private static String generateGameSection(){
		ArrayList<BigWord> words = Config.gameBigWords;
		String gameRow = "<tr>";
		for(int i =0; i < words.size(); i++){
			gameRow += "\n<td>";
			
				gameRow += "<div style='line-height:200px;text-align:center; vertical-align: middle; height:200px;width:200px;'>"+words.get(i).getProcessedWord() +"</div>";
			
			gameRow += "</td>\n";
		}
		gameRow+= "<td><div>Solution: "+Config.solutionWord+" </div></td>";
		return gameRow += "</tr>";
	}

}
