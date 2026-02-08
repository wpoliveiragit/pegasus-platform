package br.com.pegasus;

import java.util.List;

public class MainTest {

  public static void main(String[] args) {
    List<String> l = List.of("1","2","3","4","5");
    l.subList(0,0).forEach(System.out::println);
  }

}
