package com.sullivan.ricksbricks.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BrickOrderNotFoundException extends RuntimeException {
        public BrickOrderNotFoundException() {
            super();
        }
}
