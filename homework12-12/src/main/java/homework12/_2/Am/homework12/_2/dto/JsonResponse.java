package homework12._2.Am.homework12._2.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse {
    public static <T>ResponseEntity <ApiResponse<T>> of(T data){
        return ResponseEntity.ok(ApiResponse.<T>builder().data(data).build());
    }
    public static ResponseEntity<?> ok(Object data) {
        return ResponseEntity.ok(data);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T t){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<T>builder().data(t).build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent(){
        return ResponseEntity.noContent().build();
    }
}
