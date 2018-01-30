package graph;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class DataJson {

	public static Map<String, Integer> max(Map<String, Integer> map, Map<String, Integer> result) {
		int max = 0;
		String key = "";
		for (String v : map.keySet()) {
			if (map.get(v) > max) {
				max = map.get(v);
				key = v;
			}
		}
		map.remove(key);
		result.put(key, max);
		return result;
	}

	public static String toString(int[][] M) {
		String separator = ", ";
		StringBuffer result = new StringBuffer();

		// iterate over the first dimension
		for (int i = 0; i < M.length; i++) {
			// iterate over the second dimension
			for (int j = 0; j < M[i].length; j++) {
				result.append(M[i][j]);
				result.append(separator);
			}
			// remove the last separator
			result.setLength(result.length() - separator.length());
			// add a line break.
			result.append("\n");
		}
		return result.toString();
	}

	public static String toString(String[][] M) {
		String separator = ", ";
		StringBuffer result = new StringBuffer();

		// iterate over the first dimension
		for (int i = 0; i < M.length; i++) {
			// iterate over the second dimension
			for (int j = 0; j < M[i].length; j++) {
				result.append(M[i][j]);
				result.append(separator);
			}
			// remove the last separator
			result.setLength(result.length() - separator.length());
			// add a line break.
			result.append("\n");
		}
		return result.toString();
	}

	public static void main(String[] args) throws FileNotFoundException, JSONException {
		int[][] matrix = new int[1000][1000];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = 0;
			}
		}
		String[][] matrixName = new String[1000][1000];
		for (int i = 0; i < matrixName.length; i++) {
			for (int j = 0; j < matrixName[0].length; j++) {
				matrixName[i][j] = "";
			}
		}
		String data = new Scanner(new File("Json")).useDelimiter("\\Z").next();
		JSONObject json = new JSONObject(data);
		JSONObject result = json.getJSONObject("result");
		JSONObject hits = result.getJSONObject("hits");
		JSONArray hit = hits.getJSONArray("hit");
		System.out.println(hit.length());
		System.out.println(hit.getJSONObject(0).getString("@id"));
		// JSONObject info = hit.getJSONObject(1).getJSONObject("info");
		// String title = info.getString("title");
		// System.out.println(title);
		ArrayList<String> motclé = new ArrayList<String>();
		for (int i = 0; i < matrix.length; i++) {
			JSONObject info = hit.getJSONObject(i).getJSONObject("info");
			String title = info.getString("title");
			title = title.replace(".", "");
			title = title.replace("Cryptography", "");
			title = title.replace("Cryptographic", "");
			title = title.replace("-", " ");
			title = title.replace("Security", " ");
			title = title.replace("Algorithm", " ");
			title = title.replace("Attacks", "Attack");
			title = title.replace("Scheme", "");
			// System.out.println(i +":"+title);
			String[] keywords = title.split(" ");
			for (int j = i; j < matrix[0].length; j++) {
				JSONObject infoj = hit.getJSONObject(j).getJSONObject("info");
				String titlej = infoj.getString("title");
				for (int k = 0; k < keywords.length; k++) {
					// System.out.println(keywords[k]);
					if (keywords[k].length() > 5 && titlej.contains(keywords[k])) {
						matrix[i][j] += 1;
						matrixName[i][j] += keywords[k] + " ";
						motclé.add(keywords[k]);
					}
				}
			}
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = i; j < matrix[0].length; j++) {
				matrix[j][i] = matrix[i][j];
			}
		}
		for (int i = 0; i < matrixName.length; i++) {
			for (int j = i; j < matrixName[0].length; j++) {
				matrixName[j][i] = matrixName[i][j];
			}
		}

		System.out.println(DataJson.toString(matrix));
		System.out.println(DataJson.toString(matrixName));

		Map<String, Integer> frequencies = new LinkedHashMap<String, Integer>();
		for (String keyword : motclé) {
			if (!frequencies.containsKey(keyword)) {
				frequencies.put(keyword, Collections.frequency(motclé, keyword));
			} /*
				 * else{ frequencies.put(keyword, 1); }
				 */
		}

		System.out.println(frequencies.toString());
		Map<String, Integer> maxs = new LinkedHashMap<String, Integer>();
		for (int l = 0; l < 11; l++) {
			System.out.println(DataJson.max(frequencies, maxs).toString());
		}

		Map<String, Integer> group = new LinkedHashMap<String, Integer>();
		int compteur = 0;
		ArrayList<Integer> elementJsonGroup = new ArrayList<Integer>();
		for (int i = 0; i < matrix.length; i++) {
			JSONObject info = hit.getJSONObject(i).getJSONObject("info");
			String title = info.getString("title");
			title = title.replace(".", "");
			if (matrixName[i][i].contains("Privacy")) {
				group.put(title, 10);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Public")) {
				group.put(title, 9);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Elliptic")) {
				group.put(title, 8);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Secure")) {
				group.put(title, 7);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Biometric")) {
				group.put(title, 6);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Control")) {
				group.put(title, 5);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Access")) {
				group.put(title, 4);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Signature")) {
				group.put(title, 3);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Authentication")) {
				group.put(title, 2);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Encryption")) {
				group.put(title, 1);
				compteur++;
				elementJsonGroup.add(i);
			}
			if (matrixName[i][i].contains("Attack") || matrixName[i][i].contains("Attacks")) {
				group.put(title, 0);
				compteur++;
				elementJsonGroup.add(i);
			}
		}

		String Json = "{\"nodes\": [";

		System.out.println(group.toString());
		/*
		 * for(int i = 0; i<matrix.length; i++){ JSONObject info =
		 * hit.getJSONObject(i).getJSONObject("info"); String title =
		 * info.getString("title"); title=title.replace(".", ""); Json+= "{\"id\": \"" +
		 * title+"\", \"group\":" + group.get(title)+"},"; Json +="\n"; }
		 */

		for (String key : group.keySet()) {
			Json += "{\"id\": \"" + key + "\", \"group\":" + group.get(key) + "},";
			Json += "\n";
		}
		Json = Json.substring(0, Json.length() - 2);
		Json += "],\"links\":[";
		System.out.println(Json);
		System.out.println(elementJsonGroup.toString());
		/*
		 * for(int i = 0; i<matrix.length;i++){ for (int j=i+1;j<matrix[i].length;j++){
		 * if(elementJsonGroup.contains(i) && elementJsonGroup.contains(j)){ JSONObject
		 * info = hit.getJSONObject(i).getJSONObject("info"); String titlei =
		 * info.getString("title"); titlei=titlei.replace(".", ""); JSONObject infoj =
		 * hit.getJSONObject(j).getJSONObject("info"); String titlej =
		 * infoj.getString("title"); titlej=titlej.replace(".", ""); Json
		 * +="{\"source\": \""+ titlei
		 * +"\", \"target\": \""+titlej+"\", \"value\": "+matrix[i][j]+"},"; Json
		 * +="\n"; } } }
		 */
		for (int articlei : elementJsonGroup) {
			for (int articlej : elementJsonGroup) {
				if (articlei != articlej) {

					JSONObject info = hit.getJSONObject(articlei).getJSONObject("info");
					String titlei = info.getString("title");
					titlei = titlei.replace(".", "");
					JSONObject infoj = hit.getJSONObject(articlej).getJSONObject("info");
					String titlej = infoj.getString("title");
					titlej = titlej.replace(".", "");
					if (matrix[articlei][articlej] != 0) {
						//System.out.println("i:" + articlei + ", j:" + articlej);
						Json += "{\"source\": \"" + titlei + "\", \"target\": \"" + titlej + "\", \"value\": "
								+ matrix[articlei][articlej] + "},";
						Json += "\n";
					}

				}
			}
		}
		System.out.println("nombre d'article:" + compteur);
		Json = Json.substring(0, Json.length() - 2);
		Json += "]}";
		System.out.println(Json);

		PrintWriter out = new PrintWriter("Cryptography.json");
		out.println(Json);

	}
}
