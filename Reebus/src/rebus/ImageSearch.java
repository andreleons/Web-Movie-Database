package rebus;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;
public class ImageSearch {

	private String word;
	public ImageSearch(String word){
		this.word = word;
	}
	
	public String getImageUrl(){
		 String imageUrl="";
		  try{
	            URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+word);
	            URLConnection connection = url.openConnection();
	            String line;
	            StringBuilder builder = new StringBuilder();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            while((line = reader.readLine()) != null) {
	                builder.append(line);
	            }

	            JSONObject json = new JSONObject(builder.toString());
	      
	              JSONArray js = json.getJSONObject("responseData").getJSONArray("results");
	              for(int j=0;j<1;j++)
	              {

	              JSONObject  json1 = js.getJSONObject(j);
	              imageUrl=json1.getString("url");
	              System.out.println(imageUrl);
	           // String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(i).getString("url");

	               BufferedImage image = ImageIO.read(new URL(imageUrl));
 
	            // for downloading the image
	            // connection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)");

	               
	               /*
	            File  outputfile = new File("D:\\saved"+j+".jpg");
	            ImageIO.write(image, "jpg", outputfile);
	            System.out.println("downloded"+j);
	            */
	            }
	              
	            //JOptionPane.showMessageDialog(null, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
	        } catch(Exception e){
	            JOptionPane.showMessageDialog(null, e.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
	            e.printStackTrace();
	        }
		  return imageUrl;
	}
}
