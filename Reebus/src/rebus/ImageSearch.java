package rebus;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Class for contacting google image rest api
 * @author marsh_000
 *
 */
public class ImageSearch {

	private static String noImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/600px-No_image_available.svg.png";
	public ImageSearch() {
	}

	// Later may add support for picking an image closest to the desired size
	public static String getImageUrl(String word) {
		String imageUrl = "";
		try {

			URL url = new URL(
					"https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
							+ URLEncoder.encode(word, "UTF-8"));
			URLConnection connection = url.openConnection();
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());

			JSONArray js = json.getJSONObject("responseData").getJSONArray(
					"results");
			for (int j = 0; j < js.length(); j++) {

				try {
					JSONObject json1 = js.getJSONObject(j);
					imageUrl = json1.getString("url");
					//will usually determine a good image url if this passes
					Image image = ImageIO.read(new URL(imageUrl));
					if(image != null){
					    return imageUrl;
					}else if(j == js.length()-1){
					   return noImageUrl;
					}
			
					
				} catch (Exception e) {
					
					if(j < js.length()){
						continue;
					}
					imageUrl = noImageUrl;
					
				}
				// reached here without error have a good image
				

			}

			// JOptionPane.showMessageDialog(null, "", "",
			// JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		} catch (Exception e) {
			
		}
		return imageUrl;
	}
	
	public static String imageVerifcation(String word){
		try{
			Image image = ImageIO.read(new URL(word));
			if(image != null){
			    return word;
			}else{
			   return noImageUrl;
			}
		}catch(Exception e){
			return noImageUrl;
		}
	}
	
	

}
