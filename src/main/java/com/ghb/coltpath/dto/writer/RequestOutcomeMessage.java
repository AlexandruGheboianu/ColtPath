package com.ghb.coltpath.dto.writer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Ghebo on 1/15/2016.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestOutcomeMessage {
    private String message;
    private List<FieldMessage> fields;
    private List<String> globalMessages;

    public RequestOutcomeMessage(String message) {
        this.message = message;
    }


}
