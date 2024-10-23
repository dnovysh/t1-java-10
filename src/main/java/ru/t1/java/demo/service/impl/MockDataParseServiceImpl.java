package ru.t1.java.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import ru.t1.java.demo.service.MockDataParseService;

public abstract class MockDataParseServiceImpl<T> implements MockDataParseService<T> {

  private final Class<T[]> clazz;

  public MockDataParseServiceImpl(Class<T[]> clazz) {
    this.clazz = clazz;
  }

  public abstract String getMockFilePath();

  @Override
  public List<T> parseJson() {
    ObjectMapper mapper = new ObjectMapper();

    InputStream resource = null;
    try {
      resource = new ClassPathResource(getMockFilePath()).getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
      T[] values = mapper.readValue(reader, clazz);
      return Arrays.asList(values);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
