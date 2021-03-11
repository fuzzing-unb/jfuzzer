package br.unb.cic.jfuzzer.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Range<T> {

    private T min;
    private T max;

}
