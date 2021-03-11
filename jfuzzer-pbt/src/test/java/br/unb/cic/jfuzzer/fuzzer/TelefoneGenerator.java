package br.unb.cic.jfuzzer.fuzzer;

import java.util.Random;

import br.unb.cic.jfuzzer.api.AbstractFuzzer;
import br.unb.cic.jfuzzer.beans.pessoa.Telefone;
import br.unb.cic.jfuzzer.fuzzer.StringFuzzer;
import br.unb.cic.jfuzzer.util.Range;

public class TelefoneGenerator extends AbstractFuzzer<Telefone> {
    private final Random random;
    private final Range<Integer> rangePrefix;
    private final Range<Integer> rangeSuffix;

    public TelefoneGenerator(Random random, Range<Integer> rangePrefix, Range<Integer> rangeSuffix) {
        this.random = random;
        this.rangePrefix = rangePrefix;
        this.rangeSuffix = rangeSuffix;
    }

    @Override
    public Telefone fuzz() {
        String prefixo = new StringFuzzer(rangePrefix, random, StringFuzzer.DIGITS).fuzz();
        String sufixo = new StringFuzzer(rangeSuffix, random, StringFuzzer.DIGITS).fuzz();

        Telefone telefone = new Telefone();
        telefone.setLabel("label");
        telefone.setNumero(prefixo + "-" + sufixo);

        return telefone;
    }

}
