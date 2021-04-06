package br.unb.cic.jfuzzer.pbt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.beans.pessoa.Pessoa;
import br.unb.cic.jfuzzer.beans.pessoa.Telefone;
import br.unb.cic.jfuzzer.fuzzer.TelefoneGenerator;
import br.unb.cic.jfuzzer.util.Range;

class PessoaTest {

    private static final Range<Integer> RANGE_STRING = new Range<Integer>(10, 50);
    private static final Range<Float> RANGE_WEIGHT = new Range<Float>(30.0f, 150.0f);
    private static final Range<Integer> RANGE_PHONE = new Range<Integer>(0, 3);
    private static final Range<Integer> PHONE_PREFIX = new Range<>(4, 5);
    private static final Range<Integer> PHONE_SUFFIX = new Range<>(4, 4);

    
    @Test
    void testPessoa() {
        PbtConfig config = new PbtConfig();
        config.setStringLengthRange(RANGE_STRING);
        config.setFloatLengthRange(RANGE_WEIGHT);
        config.setCollectionSizeRange(RANGE_PHONE);
        PbtMain generator = new PbtMain(config);
        generator.register(Telefone.class, new TelefoneGenerator(generator.getRandom(), PHONE_PREFIX, PHONE_SUFFIX));

        for (int i = 0; i < 20; i++) {
            Pessoa pessoa = generator.next(Pessoa.class);
            System.err.println(pessoa);

            // TODO relaizar checagens
            assertThat(pessoa.getNome()).hasSizeGreaterThanOrEqualTo(RANGE_STRING.getMin());
            assertThat(pessoa.getNome()).hasSizeLessThanOrEqualTo(RANGE_STRING.getMax());
        }
    }

}
