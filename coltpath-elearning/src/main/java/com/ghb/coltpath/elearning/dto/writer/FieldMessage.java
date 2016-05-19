package com.ghb.coltpath.elearning.dto.writer;

import lombok.*;

/**
 * Created by Ghebo on 1/16/2016.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FieldMessage {
    private String field;
    private String message;
}
