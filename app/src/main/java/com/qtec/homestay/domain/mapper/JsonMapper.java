package com.qtec.homestay.domain.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author shaojun
 * @name JsonMapper
 * @package com.fernandocejas.android10.sample.data.entity.mapper
 * @date 15-9-11
 * <p>
 * Json mapper transforms json to object/object to json.
 */
public class JsonMapper {

  private Gson mGson;

  public JsonMapper() {
    mGson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
  }

  public String toJson(Object encryptInfo) {
    return new Gson().toJson(encryptInfo);
  }

  public Object fromJson(String json, Type type) {
    return new Gson().fromJson(json, type);
  }

  public Object fromJsonNullStringToEmpty(String json, Type type) {
    return mGson.fromJson(json, type);
  }

  public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
      Class<T> rawType = (Class<T>) type.getRawType();
      if (rawType != String.class) {
        return null;
      }
      return (TypeAdapter<T>) new StringNullAdapter();
    }
  }

  public class StringNullAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader reader) throws IOException {
      // TODO Auto-generated method stub
      if (reader.peek() == JsonToken.NULL) {
        reader.nextNull();
        return "";
      }
      return reader.nextString();
    }
    @Override
    public void write(JsonWriter writer, String value) throws IOException {
      // TODO Auto-generated method stub
      if (value == null) {
        writer.nullValue();
        return;
      }
      writer.value(value);
    }
  }
}
