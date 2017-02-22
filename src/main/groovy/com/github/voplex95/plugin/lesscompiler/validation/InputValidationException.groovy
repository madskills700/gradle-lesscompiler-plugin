package com.github.voplex95.plugin.lesscompiler.validation

class InputValidationException extends RuntimeException {

    InputValidationException() {
        super()
    }

    InputValidationException(String message) {
        super(message)
    }

    InputValidationException(String message, Throwable cause) {
        super(message, cause)
    }

    InputValidationException(Throwable cause) {
        super(cause)
    }
}
