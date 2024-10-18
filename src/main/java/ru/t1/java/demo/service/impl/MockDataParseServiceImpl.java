package ru.t1.java.demo.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import ru.t1.java.demo.service.MockDataParseService;

public class MockDataParseServiceImpl<T> implements MockDataParseService<T> {

  @Override
  public List<T> parseJson(String mockFilePath) {
    ObjectMapper mapper = new ObjectMapper();

    InputStream resource = null;
    try {
      resource = new ClassPathResource(mockFilePath)
          .getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
      TypeReference<T[]> typeRef = new TypeReference<T[]>() {
      };
      T[] values = mapper.readValue(reader, typeRef);
      return Arrays.asList(values);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
