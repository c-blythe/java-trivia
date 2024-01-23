import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TriviaUtils {
	
	public static void newQuestion() throws UnirestException {
		HttpResponse<String> response = Unirest.get(createUrl())
				  .header("x-api-key", "cdf169f1-5161-451c-be23-d0f0480a7197")
				  .asString();
		JSONObject header = new JSONObject(response.getBody());
		JSONObject body = header.getJSONArray("results").getJSONObject(0);
		
		String difficulty = StringUtils.unescapeHtml3(body.getString("difficulty"));
		String question = StringUtils.unescapeHtml3(body.getString("question"));
		String correctAnswer = StringUtils.unescapeHtml3(body.getString("correct_answer"));
		JSONArray incorrectAnswersJArray = (JSONArray) body.get("incorrect_answers");
		String[] incorrectAnswers = new String[incorrectAnswersJArray.length()];
		for (int i = 0; i < incorrectAnswersJArray.length(); i++) {
			incorrectAnswers[i] =  StringUtils.unescapeHtml3(incorrectAnswersJArray.getString(i));
		}
		
		TriviaGUI.updateQuestion(question, difficulty, correctAnswer, incorrectAnswers);
		
		
		System.out.println(header.toString());
		System.out.println(body.toString());
		System.out.println(difficulty);
		System.out.println(question);
		System.out.println(correctAnswer);
		for (String item : incorrectAnswers) {
			System.out.print(item);
			System.out.print(", ");
		}

	}
	
	
	public static String createUrl() {
		return "https://opentdb.com/api.php?amount=1";
	}
	
	public static String createUrl(int numQuestions, Integer category, String difficulty, String type) {
		StringBuilder url = new StringBuilder();
		url.append("https://opentdb.com/api.php?amount=");
		url.append(numQuestions);
		if (category != null) {
			url.append("&category=");
			url.append(category);
		}
		if (difficulty != null) {
			url.append("&difficulty=");
			url.append(difficulty);
		}
		if (type != null) {
			url.append("&type=");
			url.append(type);
		}
		return url.toString();
	}
}
