package br.unb.cic.jfuzzer.greybox;
import br.unb.cic.jfuzzer.greybox.tmp.Seed;
import br.unb.cic.jfuzzer.util.WeightedRandom;

import java.util.List;

public class PowerSchedule {

    public List<Seed<String>> assignEnergy(List<Seed<String>> population){
        for (Seed<String> seed : population) {
            seed.setEnergy(1.0);
        }
        return population;
    }

    public List<Seed<String>> normalizedEnergy(List<Seed<String>> population){
        double sum_energy = 0f;
        List<Seed<String>> normEnergy = assignEnergy(population);
        for (Seed<String> seed : normEnergy) {
            sum_energy += seed.getEnergy();
        }
        for (Seed<String> seed : normEnergy) {
            seed.setEnergy(seed.getEnergy()/sum_energy);
        }
        return normEnergy;
    }

    public Seed<String> choose(List<Seed<String>> population){
        List<Seed<String>> normEnergy = normalizedEnergy(population);
        WeightedRandom<Seed<String>> wr = new WeightedRandom<>();
        for (Seed<String> s : normEnergy) {
            wr.addEntry(s, s.getEnergy());
        }
        Seed<String> seed = wr.getRandom();
        return seed;
    }
}
