package rebus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class SavedGameCollection implements Serializable {
	private static ArrayList<SavedGame> games; 
	
	
	
	public static ArrayList<SavedGame> getGames() {
		return games;
	}
	
	public static void setGames(ArrayList<SavedGame> games) {
		SavedGameCollection.games = games;
	}

	public static void addGames(SavedGame game) {
		SavedGameCollection.games.add(game);
	}
	public static String getSavedGameCount(){
		return Integer.toString(games.size());
	}
	public static void saveGames(){
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new 
				FileOutputStream("rebusGames.data");
			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new
				ObjectOutputStream (f_out);
			// Write object out to disk
			obj_out.writeObject ( games );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Read this each time program starts
	 */
	public static ArrayList<SavedGame> readGames(){
		// Read from disk using FileInputStream
		FileInputStream f_in;
		try {
			f_in = new 
				FileInputStream("rebusGames.data");
			// Read object using ObjectInputStream
			ObjectInputStream obj_in = 
				new ObjectInputStream (f_in);

			// Read an object
			Object obj = obj_in.readObject();

			if (obj instanceof ArrayList)
			{
				ArrayList<SavedGame> vec = (ArrayList<SavedGame>) obj;
				 
				return vec;
			}
		} catch ( IOException e) {
			//do nothing let go to empty game 	
		} catch (ClassNotFoundException e) {
			//do nothing let go to empty game 	
		}
		return games = new ArrayList<SavedGame>();
	
	}
}
