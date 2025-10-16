# DefaultApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**usuariosPost**](DefaultApi.md#usuariosPost) | **POST** /usuarios | Crear usuario |


<a id="usuariosPost"></a>
# **usuariosPost**
> UsuarioResponseDTO usuariosPost(usuarioRequestDTO)

Crear usuario

### Example
```java
// Import classes:
import com.ejemplo.demo.invoker.ApiClient;
import com.ejemplo.demo.invoker.ApiException;
import com.ejemplo.demo.invoker.Configuration;
import com.ejemplo.demo.invoker.models.*;
import com.ejemplo.demo.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO(); // UsuarioRequestDTO | 
    try {
      UsuarioResponseDTO result = apiInstance.usuariosPost(usuarioRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#usuariosPost");
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
| **usuarioRequestDTO** | [**UsuarioRequestDTO**](UsuarioRequestDTO.md)|  | |

### Return type

[**UsuarioResponseDTO**](UsuarioResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Usuario creado |  -  |

