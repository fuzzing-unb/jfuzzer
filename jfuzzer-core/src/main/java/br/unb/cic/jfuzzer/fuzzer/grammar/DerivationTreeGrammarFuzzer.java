package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.ArrayList;

public class DerivationTreeGrammarFuzzer {

    public DerivationTreeNode expansionToChildren(String expansion) {
        if("".equals(expansion)) {
            return new DerivationTreeNode("",new ArrayList<>());
        }
        
        return null;
    }
    
    
//    def expansion_to_children(expansion):
//        # print("Converting " + repr(expansion))
//        # strings contains all substrings -- both terminals and nonterminals such
//        # that ''.join(strings) == expansion
//
//        expansion = exp_string(expansion)
//        assert isinstance(expansion, str)
//
//        if expansion == "":  # Special case: epsilon expansion
//            return [("", [])]
//
//        strings = re.split(RE_NONTERMINAL, expansion)
//        return [(s, None) if is_nonterminal(s) else (s, [])
//                for s in strings if len(s) > 0]
    
}
