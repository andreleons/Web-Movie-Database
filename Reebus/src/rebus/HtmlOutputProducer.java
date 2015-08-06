package rebus;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Packages the game and solution together in one object, the final html file
 * Object to be stored in html file collection of previous games
 * @author marsh_000
 *
 */
public class HtmlOutputProducer {
	
	
	private static String buildOutputBoard(){
		String output = "<html><head><meta charset=utf-8><title>Rebus</title></head><body>";
		output += HtmlGameProducer.buildBoard();
		output += HtmlSolutionProducer.buildBoard();
		output += "</body></html>";
		
		return output;
	}
	private static String buildOutputBoardFromSavedGame(SavedGame game){
		String output = "<html><head><meta charset=utf-8><title>Rebus</title></head><body>";
		output += HtmlGameProducer.buildBoardFromSavedGame(game);
		output += HtmlSolutionProducer.buildBoardFromSavedGame(game);
		output += "</body></html>";
		
		return output;
	}
	public static void openHtml() {
		saveNewFile(buildOutputBoard());
	
		File htmlFile = new File("rebus.html");

		// open the default web browser for the HTML page
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void openHtml(SavedGame game) {
		saveNewFile(buildOutputBoardFromSavedGame(game));
	
		File htmlFile = new File("rebus.html");

		// open the default web browser for the HTML page
		try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static boolean saveNewFile(String contents) {

		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("rebus.html"), "UTF-8"));
			try {
				out.write(contents);
			} finally {
				out.close();
			}

			return true;
		} catch (IOException e) {
			System.out.println("Error saving file");
		}
		return false;
	}

}
