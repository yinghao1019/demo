package com.example.demo.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.demo.model.dto.ErrorMessageDTO;

import exception.BadRequestException;
import exception.FileExtensionNotAllowedException;
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
     * 處理用戶端相關錯誤，回傳status  code 400 MaxUploadSizeExceededException: 處理超過檔案上傳上限的例外，回傳status code 400
     * IllegalArgumentException: 處理參數錯誤(如格式錯誤、沒有輸入等)的例外
     */
    @ExceptionHandler({
        MaxUploadSizeExceededException.class,
        IllegalArgumentException.class,
        BadRequestException.class,
        FileExtensionNotAllowedException.class
    })
	public ResponseEntity<ErrorMessageDTO> handleBadRequestException(
	        WebRequest request,
	        Exception e,
	        HandlerMethod handlerMethod) {
	        String errMsg;
	        if (e instanceof MaxUploadSizeExceededException) {
	            errMsg = "上傳的檔案大小超過上限。";
	            log.error(errMsg);
	        } else if (e instanceof IllegalArgumentException) {
	            errMsg = "輸入之參數錯誤。";
	            log.error(errMsg, e);
	        } else if (e instanceof FileExtensionNotAllowedException) {
	            errMsg = "檔案格式不支援。";
	            log.error(errMsg, e);
	        } else {
	            errMsg = String.format("%s", e.getMessage());
	            log.error(errMsg);
	        }

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body(newErrorMessageDTO( "",errMsg));
	    }

	private ErrorMessageDTO newErrorMessageDTO(String errorCode, String errorMessage) {
		return ErrorMessageDTO.builder().errorCode(errorCode).message(errorMessage)
				.build();
	}

}
