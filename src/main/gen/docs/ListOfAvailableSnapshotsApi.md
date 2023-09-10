# ListOfAvailableSnapshotsApi

All URIs are relative to *http://localhost:8080/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**listOfSnapshots**](ListOfAvailableSnapshotsApi.md#listOfSnapshots) | **POST** /document/list | Get list of all available snapshots |


<a name="listOfSnapshots"></a>
# **listOfSnapshots**
> SnapshotListResponse listOfSnapshots(snapshotListRequest)

Get list of all available snapshots

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ListOfAvailableSnapshotsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080/v1");

    ListOfAvailableSnapshotsApi apiInstance = new ListOfAvailableSnapshotsApi(defaultClient);
    SnapshotListRequest snapshotListRequest = new SnapshotListRequest(); // SnapshotListRequest | request for search snapshot by filename
    try {
      SnapshotListResponse result = apiInstance.listOfSnapshots(snapshotListRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ListOfAvailableSnapshotsApi#listOfSnapshots");
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
| **snapshotListRequest** | [**SnapshotListRequest**](SnapshotListRequest.md)| request for search snapshot by filename | [optional] |

### Return type

[**SnapshotListResponse**](SnapshotListResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Success |  -  |

