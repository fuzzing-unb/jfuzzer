package br.unb.cic.jfuzzer.fuzzer;

import br.unb.cic.jfuzzer.fuzzer.mutator.StringMutatorFuzzer;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class StringMutatorFuzzerTest {
    @Test
    void testDeleteRandomCharacterMutation() {
        String input = "123456789";
        StringMutatorFuzzer mutatorFuzzer = new StringMutatorFuzzer(input);
        assertThat(input).hasSizeGreaterThan(mutatorFuzzer.fuzz(2).length());
    }

    @Test
    void testInsertRandomCharacterMutation() {
        String input = "123456789";
        StringMutatorFuzzer mutatorFuzzer = new StringMutatorFuzzer(input);
        assertThat(input).hasSizeLessThan(mutatorFuzzer.fuzz(1).length());         
    }

    @Test
    void testFlipRandomCharacterMutation() {
        String input = "123456789";
        StringMutatorFuzzer mutatorFuzzer = new StringMutatorFuzzer(input);
        assertThat(input).hasSize(mutatorFuzzer.fuzz(0).length());         
    }
}