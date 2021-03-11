module jfuzzer.core {	
    requires jfuzzer.api;
    requires lombok;
    
	exports br.unb.cic.jfuzzer.fuzzer;
	exports br.unb.cic.jfuzzer.random;
	exports br.unb.cic.jfuzzer.util;
	
	opens br.unb.cic.jfuzzer.fuzzer;
	opens br.unb.cic.jfuzzer.random;
	opens br.unb.cic.jfuzzer.util;
}