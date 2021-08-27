package com.craft.common;

import com.craft.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response<T> {

    private String status = "FAIL";

    private Error error;

    private T payload;

}
