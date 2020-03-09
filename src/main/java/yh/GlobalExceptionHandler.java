package yh;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yh.common.Result;
import yh.common.StatusCode;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public Result exception(Exception e) {
		return new Result(false, StatusCode.ERROR, e.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public Result handleAccessException(AccessDeniedException e) {
		return new Result(false, StatusCode.ACCESS_ERROR, "权限不足!", e.getMessage());
	}

}
