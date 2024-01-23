import com.mashape.unirest.http.exceptions.UnirestException;

public class Main {
	public static void main(String[] args) throws UnirestException {
		TriviaGUI gui = new TriviaGUI();
		gui.display();
		
		TriviaUtils.newQuestion();
	}
}
