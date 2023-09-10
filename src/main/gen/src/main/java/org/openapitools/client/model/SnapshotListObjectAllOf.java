/*
 * Snapshot service
 * This service api give access to documents
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * Список файлов наименования, дата, тип и id
 */
@ApiModel(description = "Список файлов наименования, дата, тип и id")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-09-10T23:07:22.555215400+03:00[Europe/Moscow]")
public class SnapshotListObjectAllOf {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_USER_ID = "userId";
  @SerializedName(SERIALIZED_NAME_USER_ID)
  private String userId;

  public static final String SERIALIZED_NAME_DATE = "date";
  @SerializedName(SERIALIZED_NAME_DATE)
  private LocalDate date;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public SnapshotListObjectAllOf() {
  }

  public SnapshotListObjectAllOf id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * Идентификатор объявления
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Идентификатор объявления")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public SnapshotListObjectAllOf userId(String userId) {
    
    this.userId = userId;
    return this;
  }

   /**
   * Идентификатор объявления
   * @return userId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Идентификатор объявления")

  public String getUserId() {
    return userId;
  }


  public void setUserId(String userId) {
    this.userId = userId;
  }


  public SnapshotListObjectAllOf date(LocalDate date) {
    
    this.date = date;
    return this;
  }

   /**
   * Дата снапшота
   * @return date
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Дата снапшота")

  public LocalDate getDate() {
    return date;
  }


  public void setDate(LocalDate date) {
    this.date = date;
  }


  public SnapshotListObjectAllOf name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя файла из которого загрузился снапшот
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Имя файла из которого загрузился снапшот")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SnapshotListObjectAllOf snapshotListObjectAllOf = (SnapshotListObjectAllOf) o;
    return Objects.equals(this.id, snapshotListObjectAllOf.id) &&
        Objects.equals(this.userId, snapshotListObjectAllOf.userId) &&
        Objects.equals(this.date, snapshotListObjectAllOf.date) &&
        Objects.equals(this.name, snapshotListObjectAllOf.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, date, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SnapshotListObjectAllOf {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("id");
    openapiFields.add("userId");
    openapiFields.add("date");
    openapiFields.add("name");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to SnapshotListObjectAllOf
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (SnapshotListObjectAllOf.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in SnapshotListObjectAllOf is not found in the empty JSON string", SnapshotListObjectAllOf.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!SnapshotListObjectAllOf.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `SnapshotListObjectAllOf` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if ((jsonObj.get("id") != null && !jsonObj.get("id").isJsonNull()) && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if ((jsonObj.get("userId") != null && !jsonObj.get("userId").isJsonNull()) && !jsonObj.get("userId").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `userId` to be a primitive type in the JSON string but got `%s`", jsonObj.get("userId").toString()));
      }
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!SnapshotListObjectAllOf.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'SnapshotListObjectAllOf' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<SnapshotListObjectAllOf> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(SnapshotListObjectAllOf.class));

       return (TypeAdapter<T>) new TypeAdapter<SnapshotListObjectAllOf>() {
           @Override
           public void write(JsonWriter out, SnapshotListObjectAllOf value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public SnapshotListObjectAllOf read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of SnapshotListObjectAllOf given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of SnapshotListObjectAllOf
  * @throws IOException if the JSON string is invalid with respect to SnapshotListObjectAllOf
  */
  public static SnapshotListObjectAllOf fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SnapshotListObjectAllOf.class);
  }

 /**
  * Convert an instance of SnapshotListObjectAllOf to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

