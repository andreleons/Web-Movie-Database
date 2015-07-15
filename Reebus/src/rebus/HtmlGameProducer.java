package rebus;

import java.util.ArrayList;

/**
 * Produces the game board
 * @author marsh_000
 *
 */
public class HtmlGameProducer {
	
	
	
	public static String buildBoard(){
		String table = "<table>/n";
		table += generateGameSection();
		table += "</table>";
		return table;
	}
	
	private static String generateGameSection(){
		ArrayList<String> urls = Config.urls;
		String gameRow = "<tr>";
		for(int i =0; i < urls.size(); i++){
			gameRow += "\n<td>";
			
				gameRow += "<img src= ' "+urls.get(i)+"' style='height:200px;width:200px;'/>";
			
			gameRow += "</td>\n";
		}
		gameRow+= "<td><div>Solution: </div></td>";
		return gameRow += "</tr>";
	}


}
