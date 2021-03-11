package br.unb.cic.jfuzzer.random;

import java.util.Random;

import br.unb.cic.jfuzzer.api.Randomizer;

import lombok.Getter;

@Getter
public class ClassicRandomStrategy implements Randomizer {

    private Random random;

    public ClassicRandomStrategy(long seed) {
        random = new Random(seed);
    }

}
