package com.ksy.adapter.utils;

import com.ksy.adapter.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class ResponseFactory {

    /**
     * <H3>Метод получения ответа на запрос в случае ошибки с кастомным сообщением</H3>
     *
     * @param e          ошибка
     * @param httpStatus статус ошибки
     * @return сущность ответа
     */
    public ResponseEntity<ErrorModel> getException(Exception e, HttpStatus httpStatus) {
        ErrorModel response = new ErrorModel();
        response.setErrors(Arrays.asList(e.toString().split(";")));
        response.setMessages(List.of(e.getMessage()));
        response.setStatus(httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * <H3>Метод получения ответа на запрос в случае ошибки с кастомным сообщением</H3>
     *
     * @param e          ошибка
     * @param message    сообщения об ошибках
     * @param httpStatus статус ошибки
     * @return сущность для ответа
     */
    public ResponseEntity<ErrorModel> getException(Exception e, List<String> message, HttpStatus httpStatus) {
        ErrorModel resp = new ErrorModel();

        resp.setErrors(Arrays.asList(e.toString().split(";")));
        resp.setMessages(message);
        resp.setStatus(httpStatus.value());

        return new ResponseEntity<>(resp, httpStatus);
    }

    /**
     * <H3>Метод получения пустого успешного ответа</H3>
     *
     * @return ResponseEntity со статусом ответа 200
     */
    public ResponseEntity<?> getEmptySuccessResponse() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <H3>Метод получения ответа на запрос в случае успеха</H3>
     *
     * @param result Универсальный объект с моделью (или массивом) передачи данных
     * @return сущность с сообщением об успехе запроса
     */
    public ResponseEntity<?> getRespWithBody(Object result) {
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
