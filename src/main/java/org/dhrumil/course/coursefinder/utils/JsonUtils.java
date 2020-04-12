package org.dhrumil.course.coursefinder.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {

  private static JsonParser parser = new JsonParser();
  private static Gson gson = new Gson();

  public static JsonObject parseToJson(String dataStr) {
    if (dataStr == null) {
      return null;
    }
    try {
      return parser.parse(dataStr).getAsJsonObject();
    } catch (JsonSyntaxException e) {
    }
    return null;
  }

  public static <T> String parseObjectToString(T object) {
    if (object == null) {
      return null;
    }
    return gson.toJson(object);
  }

  public static <T> T stringToObject(String data, Class<T> classOfT) {
    if (data == null) {
      return null;
    }
    return (T) gson.fromJson(data, classOfT);
  }

}
