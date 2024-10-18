package ru.t1.java.demo.service;

import java.util.List;

public interface MockDataParseService<T> {

  public List<T> parseJson(String mockFilePath);
}
