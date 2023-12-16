package module;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreManager {
  private final String WORKING_DIRECTORY = System.getProperty("user.dir");
  private final String HIGH_SCORE_FILE_PATH = WORKING_DIRECTORY + "/highscore.txt";
  private final String easteregg =
      "https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUXbmV2ZXIgZ29ubmEgZ2l2ZSB5b3UgdXA%3D";
  private final List<Integer> scores;
  private final File highScoreFile;
  private Scanner in;
  private BufferedWriter out;

  public ScoreManager() {
    scores = new ArrayList<>(6);
    highScoreFile = new File(HIGH_SCORE_FILE_PATH);
    try {
      if (!highScoreFile.exists()) {
        highScoreFile.createNewFile();
        out = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE_PATH));
        for (int i = 0; i < 5; i++) out.write("0 ");
        out.write(easteregg);
        out.close();
      }
      in = new Scanner(highScoreFile);

      while (in.hasNextInt()) scores.add(in.nextInt());
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * get scores by reading high score file
   *
   * @return list of scores
   */
  public List<Integer> getScores() {
    in = new Scanner(HIGH_SCORE_FILE_PATH);
    for (int i = 0; in.hasNextInt(); i++) scores.set(i, in.nextInt());
    in.close();
    return scores;
  }

  /**
   * add the final scores to the high score file after a game ends
   *
   * @param score
   */
  public void addScore(int score) {
    scores.add(score);
    sort();
    updateHighScoreFile();
  }

  private void updateHighScoreFile() {
    // overwrite the file
    try {
      out = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE_PATH));
      for (int score : scores) out.write(score + " ");
      out.write(easteregg);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // pretty redundant, but gotta implement some algorithm somehow
  private void sort() {
    // insertion sort
    for (int i = 1; i < scores.size(); i++) {
      int key = scores.get(i);
      int j = i - 1;
      while (j >= 0 && scores.get(j) < key) {
        scores.set(j + 1, scores.get(j));
        j--;
      }
      scores.set(j + 1, key);
    }
    // also delete the 6th score as it is out of the high score board, no need to keep track of it
    if (scores.size() > 5) scores.remove(5);
  }
}
