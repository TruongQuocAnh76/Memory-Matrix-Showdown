package module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreManager {
  private final String WORKING_DIRECTORY = System.getProperty("user.dir");
  private final String HIGH_SCORE_FILE_PATH = WORKING_DIRECTORY + "/highscore.txt";
  private List<Integer> scores;
  private File highScoreFile;
  private Scanner in;
  private FileWriter out;

  public ScoreManager() {
    scores = new ArrayList<>(6);
    highScoreFile = new File(HIGH_SCORE_FILE_PATH);
    try {
      if (!highScoreFile.exists()) highScoreFile.createNewFile();
      in = new Scanner(highScoreFile);

      while (in.hasNextInt()) scores.add(in.nextInt());
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Integer> getScores() {
    in = new Scanner(HIGH_SCORE_FILE_PATH);
    for (int i = 0; in.hasNextInt(); i++) scores.set(i, in.nextInt());
    in.close();
    return scores;
  }

  public void addScore(int score) {
    scores.add(score);
    sort();
    updateHighScoreFile();
  }

  private void updateHighScoreFile() {
    // overwrite the file
    try {
      out = new FileWriter(highScoreFile);
      for (int score : scores) out.write(score + " ");
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
