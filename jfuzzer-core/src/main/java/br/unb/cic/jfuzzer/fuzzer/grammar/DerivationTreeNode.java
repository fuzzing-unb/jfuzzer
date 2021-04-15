package br.unb.cic.jfuzzer.fuzzer.grammar;

import java.util.List;
import java.util.Objects;

public class DerivationTreeNode {

    private String symbol;
    private List<DerivationTreeNode> children;
//TODO    private String annotation;

    public DerivationTreeNode(String symbol, List<DerivationTreeNode> children) {
        super();
        this.symbol = symbol;
        this.children = children;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<DerivationTreeNode> getChildren() {
        return children;
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DerivationTreeNode other = (DerivationTreeNode) obj;
        return Objects.equals(children, other.children) && Objects.equals(symbol, other.symbol);
    }

    @Override
    public String toString() {
        return String.format("DerivationTreeNode [symbol=%s, children=%s]", symbol, children);
    }

}
