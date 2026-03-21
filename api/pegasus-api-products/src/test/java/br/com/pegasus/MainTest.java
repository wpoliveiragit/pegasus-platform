package br.com.pegasus;

import java.util.List;
import java.util.Random;

public class MainTest {

  public static void main(String[] args) {

    Random random = new Random();

    for (int i = 0; i < 10; i++) {
      // arredonda para 2 casas decimais
      float rounded = random.nextInt(100, 10000) / 100f;

      System.out.println(rounded);
    }
  }

}
