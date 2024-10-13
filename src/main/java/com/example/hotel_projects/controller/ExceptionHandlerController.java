package com.example.hotel_projects.controller;



import com.example.hotel_projects.exp.AppBadException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
  @ExceptionHandler(AppBadException.class)
  public ResponseEntity<?> handle(AppBadException appBadException){
      return ResponseEntity.badRequest().body(appBadException.getMessage());
  }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?>handle(RuntimeException appBadException){
      appBadException.printStackTrace();
        return ResponseEntity.internalServerError().body(appBadException.getMessage());
        }
  @ExceptionHandler(JwtException.class)
  private ResponseEntity<?> handle(JwtException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

}
