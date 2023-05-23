package com.example.demo.config;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.demo.constant.ErrorCode;
import com.example.demo.model.dto.ErrorMessageDTO;

import exception.BadRequestException;
import exception.FileExtensionNotAllowedException;
import exception.InternalServerErrorException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ErrorMessageDTO> handleMethodArgumentNotValidException(HttpServletRequest req,
//			MethodArgumentNotValidException e) {
//		List<String> fieldErrorMessages = e.getBindingResult().getFieldErrors().stream()
//				.map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
//				.collect(Collectors.toList());
//		String errorMessageForOutput = String.join(", ", fieldErrorMessages);
//
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newErrorMessageDTO(null, errorMessageForOutput));
//	}

	/**
	 * 處理用戶端相關錯誤，回傳status code 400 MaxUploadSizeExceededException:
	 * 處理超過檔案上傳上限的例外，回傳status code 400 IllegalArgumentException:
	 * 處理參數錯誤(如格式錯誤、沒有輸入等)的例外
	 */
	@ExceptionHandler({ 
		MaxUploadSizeExceededException.class, 
		IllegalArgumentException.class, 
		BadRequestException.class,FileExtensionNotAllowedException.class })
	public ResponseEntity<ErrorMessageDTO> handleBadRequestException(WebRequest request, Exception e,
			HandlerMethod handlerMethod) {
		String errMsg;
		if (e instanceof MaxUploadSizeExceededException) {
			errMsg = "上傳的檔案大小超過上限。";
			log.error(errMsg);
		} else if (e instanceof IllegalArgumentException) {
			errMsg = "輸入之參數錯誤。";
			log.error(errMsg, e);
		}  else {
			errMsg = String.format("%s", e.getMessage());
			log.error(errMsg,e);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				newErrorMessageDTO(ErrorCode.BAD_REQUEST, errMsg));
	}
	
    /**
     * GET/POST請求方法錯誤的攔截器 因為開發時可能比較常見, 而且發生在進入 controller 之前, 上面的攔截器攔截不到這個錯誤 所以定義了這個攔截器
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDTO> httpRequestMethodHandler(ServletException exception) {
    	log.error(exception.getMessage(),exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(newErrorMessageDTO(ErrorCode.UNKNOWN_ERROR,
            		"There is an error in the system, please contact the administrator for assistance"));
    }
    
    
    @ExceptionHandler({ InternalServerErrorException.class, UnsupportedEncodingException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDTO> internalServerErrorExceptionHandler(
        InternalServerErrorException e
    ) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(newErrorMessageDTO(ErrorCode.UNKNOWN_ERROR,
            		"There is an error in the system, please contact the administrator for assistance"));
    }
	

	private ErrorMessageDTO newErrorMessageDTO(String errorCode, String errorMessage) {
		return ErrorMessageDTO.builder()
				.errorCode(errorCode)
				.message(errorMessage)
				.traceId(MDC.get("REQUEST_OID"))
				.dateTime(LocalDateTime.now().toString())
				.build();
	}

}
