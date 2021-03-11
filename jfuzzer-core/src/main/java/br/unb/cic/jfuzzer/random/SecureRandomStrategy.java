package br.unb.cic.jfuzzer.random;

import java.security.SecureRandom;

import br.unb.cic.jfuzzer.api.Randomizer;

import lombok.Getter;

@Getter
public class SecureRandomStrategy implements Randomizer {

    private SecureRandom random;

    public SecureRandomStrategy(long seed) {
        random = new SecureRandom();
        random.setSeed(seed);
    }

    
}
