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
	
	private String buildGameSection(){
		String gameSection = "<td>";
		return null;
	}
	private static String generateGameSection(){
		ArrayList<String> urls = Config.urls;
		String gameRow = "<tr>";
		for(int i =0; i < urls.size(); i++){
			gameRow += "\n<td>";
			
				gameRow += "<img src= ' "+urls.get(i)+"' style='height:200px;width:200px;'/>";
			
			gameRow += "</td>";
		}
		return gameRow += "</tr>";
	}
	/**
	 * Only needed in rebus n
	 * @return
	 */
	private String generateIndexTableRows(){
		
		return null;
	}

}
