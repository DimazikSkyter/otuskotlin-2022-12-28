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
import org.openapitools.client.model.SnapshotListRequest;
import org.openapitools.client.model.SnapshotReadRequest;
import org.openapitools.client.model.SnapshotSearchRequest;

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
 * Базовый интерфейс для всех запросов
 */
@ApiModel(description = "Базовый интерфейс для всех запросов")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-09-10T23:07:22.555215400+03:00[Europe/Moscow]")
public class IRequest {
  public static final String SERIALIZED_NAME_REQUEST_TYPE = "requestType";
  @SerializedName(SERIALIZED_NAME_REQUEST_TYPE)
  protected String requestType;

  public static final String SERIALIZED_NAME_REQUEST_ID = "requestId";
  @SerializedName(SERIALIZED_NAME_REQUEST_ID)
  private String requestId;

  public IRequest() {
    this.requestType = this.getClass().getSimpleName();
  }

  public IRequest requestType(String requestType) {
    
    this.requestType = requestType;
    return this;
  }

   /**
   * Поле-дескриминатор для вычисления типа запроса
   * @return requestType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Поле-дескриминатор для вычисления типа запроса")

  public String getRequestType() {
    return requestType;
  }


  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }


  public IRequest requestId(String requestId) {
    
    this.requestId = requestId;
    return this;
  }

   /**
   * Идентификатор запроса для отладки и идемпотентности
   * @return requestId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Идентификатор запроса для отладки и идемпотентности")

  public String getRequestId() {
    return requestId;
  }


  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IRequest irequest = (IRequest) o;
    return Objects.equals(this.requestType, irequest.requestType) &&
        Objects.equals(this.requestId, irequest.requestId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestType, requestId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IRequest {\n");
    sb.append("    requestType: ").append(toIndentedString(requestType)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
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
    openapiFields.add("requestType");
    openapiFields.add("requestId");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to IRequest
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (IRequest.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in IRequest is not found in the empty JSON string", IRequest.openapiRequiredFields.toString()));
        }
      }

      String discriminatorValue = jsonObj.get("requestType").getAsString();
      switch (discriminatorValue) {
        case "SnapshotListRequest":
          SnapshotListRequest.validateJsonObject(jsonObj);
          break;
        case "SnapshotReadRequest":
          SnapshotReadRequest.validateJsonObject(jsonObj);
          break;
        case "SnapshotSearchRequest":
          SnapshotSearchRequest.validateJsonObject(jsonObj);
          break;
        case "list":
          SnapshotListRequest.validateJsonObject(jsonObj);
          break;
        case "read":
          SnapshotReadRequest.validateJsonObject(jsonObj);
          break;
        case "search":
          SnapshotSearchRequest.validateJsonObject(jsonObj);
          break;
        default: 
          throw new IllegalArgumentException(String.format("The value of the `requestType` field `%s` does not match any key defined in the discriminator's mapping.", discriminatorValue));
      }
  }


 /**
  * Create an instance of IRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of IRequest
  * @throws IOException if the JSON string is invalid with respect to IRequest
  */
  public static IRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, IRequest.class);
  }

 /**
  * Convert an instance of IRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

