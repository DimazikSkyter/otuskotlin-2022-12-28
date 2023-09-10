# ReadApi

All URIs are relative to *http://localhost:8080/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**readSnapshot**](ReadApi.md#readSnapshot) | **POST** /document/read | Method to read snapshot |


<a name="readSnapshot"></a>
# **readSnapshot**
> SnapshotReadResponse readSnapshot(snapshotReadRequest)

Method to read snapshot

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ReadApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/v1");

    ReadApi apiInstance = new ReadApi(defaultClient);
    SnapshotReadRequest snapshotReadRequest = new SnapshotReadRequest(); // SnapshotReadRequest | Request body
    try {
      SnapshotReadResponse result = apiInstance.readSnapshot(snapshotReadRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ReadApi#readSnapshot");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **snapshotReadRequest** | [**SnapshotReadRequest**](SnapshotReadRequest.md)| Request body | [optional] |

### Return type

[**SnapshotReadResponse**](SnapshotReadResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Success |  -  |

