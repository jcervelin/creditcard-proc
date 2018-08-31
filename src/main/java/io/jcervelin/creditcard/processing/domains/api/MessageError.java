package io.jcervelin.creditcard.processing.domains.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String field;
    private String message;

}