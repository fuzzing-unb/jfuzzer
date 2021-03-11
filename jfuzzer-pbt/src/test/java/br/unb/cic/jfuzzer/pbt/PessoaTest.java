package br.unb.cic.jfuzzer.pbt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.beans.pessoa.Pessoa;
import br.unb.cic.jfuzzer.fuzzer.DateFuzzer;
import br.unb.cic.jfuzzer.fuzzer.NumberFuzzer;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;
import br.unb.cic.jfuzzer.pbt.ObjectMotherConfig;
import br.unb.cic.jfuzzer.random.ClassicRandomStrategy;
import br.unb.cic.jfuzzer.util.Range;

public class PessoaTest {

    private static Random random;
    private static final int MIN = 10;
    private static final int MAX = 50;

    private static final float pesoMin = 30.0f;
    private static final float pesoMax = 150.0f;

    private static final Range<Integer> RANGE = new Range<Integer>(MIN, MAX);
    private static final Range<Float> RANGE2 = new Range<Float>(pesoMin, pesoMax);

    @BeforeAll
    public static void init() {
        random = new ClassicRandomStrategy(ObjectMotherConfig.DEFAULT_SEED).getRandom();
    }

    @Test
    void testPessoa() {
        NumberFuzzer<Integer> generator = new NumberFuzzer<Integer>(RANGE, random);
        NumberFuzzer<Float> generator2 = new NumberFuzzer<Float>(RANGE2, random);
        StringFuzzer stringGenerator = new StringFuzzer(RANGE, random);
        DateFuzzer dateGenerator = new DateFuzzer(random);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(generator.fuzz());
        pessoa.setNome(stringGenerator.fuzz());
        pessoa.setNascimento(dateGenerator.fuzz());
        pessoa.setPeso(generator2.fuzz());

        assertThat(pessoa.getId()).isNotNull();
        assertThat(pessoa.getNome()).isNotNull();
        assertThat(pessoa.getNascimento()).isNotNull();
        assertThat(pessoa.getPeso()).isNotNull();

        assertThat(pessoa.getId()).isInstanceOf(Integer.class);
        assertThat(pessoa.getNome()).isInstanceOf(String.class);
        assertThat(pessoa.getNascimento()).isInstanceOf(Date.class);
        assertThat(pessoa.getPeso()).isInstanceOf(Float.class);

        assertThat(pessoa.getNome()).hasSizeGreaterThanOrEqualTo(MIN);
        assertThat(pessoa.getNome()).hasSizeLessThanOrEqualTo(MAX);
    }

}
