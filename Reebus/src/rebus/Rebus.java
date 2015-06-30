package rebus;

public class Rebus {
	
	Rebus() {
		Config.entireCollection = new BigWordCollection();
		//Set Config.gameCollection to the appropriate settings
		generateWordPool();
		Config.gameCollection.printCollection();
	}
	
	//Sets Config.gameCollection to the appropriate settings
	public void generateWordPool() {
		Config.topic = "Any";
		Config.LANGUAGE = "En";
		Config.rebusX = 1;
		Config.solutionLength = 3;
		Config.wordStrength = 1;
		BigWordCollection temp = Config.entireCollection.getBigWordCollectionByTopic(Config.topic);
		Config.gameCollection = temp;
	}
}
