package br.unb.cic.jfuzzer.pbt;

import org.junit.jupiter.api.Test;

import br.unb.cic.jfuzzer.beans.pessoa.Pessoa;
import br.unb.cic.jfuzzer.pbt.ObjectMother;

public class ObjectMotherTest {

//    @Test
//    public void testString() {
//        Range<Integer> range = new Range(5, 10);
//        ObjectMother generator = new ObjectMother();
//        
//        
//        for(int i=0; i < 20; i++) {
//            String str = generator.next(String.class);
//            System.err.println("*** "+str+" == "+str.length());
//        }
//        
//    }

    @Test
    public void testPessoa() {
        ObjectMother generator = new ObjectMother();
        for (int i = 0; i < 20; i++) {
            Pessoa pessoa = generator.next(Pessoa.class);
            // System.err.println(pessoa);
            // TODO relaizar checagens
        }
    }

//    @Test
//    public void testCustomGenerator() {
//        Range<Integer> prefixo = new Range<>(4, 5);
//        Range<Integer> sufixo = new Range<>(4, 4);
//
//        ObjectMother generator = new ObjectMother();
//
//        generator.register(Telefone.class, new TelefoneGenerator(generator.getRandom(), prefixo, sufixo));
//
//        for (int i = 0; i < 20; i++) {
//            Pessoa pessoa = generator.next(Pessoa.class);
//            System.err.println(pessoa);
//            // TODO checar se esta no formato
//        }
//    }

}
