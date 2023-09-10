# SearchApi

All URIs are relative to *http://localhost:8080/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**searchSnapshot**](SearchApi.md#searchSnapshot) | **POST** /document/search | Method to search snapshot with parameters |


<a name="searchSnapshot"></a>
# **searchSnapshot**
> SnapshotSearchResponse searchSnapshot(snapshotSearchRequest)

Method to search snapshot with parameters

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SearchApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/v1");

    SearchApi apiInstance = new SearchApi(defaultClient);
    SnapshotSearchRequest snapshotSearchRequest = new SnapshotSearchRequest(); // SnapshotSearchRequest | request for search snapshot by filename
    try {
      SnapshotSearchResponse result = apiInstance.searchSnapshot(snapshotSearchRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SearchApi#searchSnapshot");
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
| **snapshotSearchRequest** | [**SnapshotSearchRequest**](SnapshotSearchRequest.md)| request for search snapshot by filename | [optional] |

### Return type

[**SnapshotSearchResponse**](SnapshotSearchResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Success |  -  |

