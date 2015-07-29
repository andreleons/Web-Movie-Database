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
		String table = "<table>/n";
		table += generateGameSection();
		table += "</table>";
		return table;
	}
	
	private static String generateGameSection(){
		ArrayList<BigWord> words = Config.gameBigWords;
		String gameRow = "<tr>";
		for(int i =0; i < words.size(); i++){
			gameRow += "\n<td>";
				if(!words.get(i).hasImage()){
					gameRow += "<img src= ' "+ImageSearch.getImageUrl(words.get(i).getProcessedWord())+"' style='height:200px;width:200px;'/>";
				}else{
					System.out.println("Had Image");
					gameRow += "<img src= ' "+words.get(i).getImage()+"' style='height:200px;width:200px;'/>";
				}
			
			gameRow += "</td>\n";
		}
		gameRow+= "<td><div>Solution: </div></td>";
		return gameRow += "</tr>";
	}


}
