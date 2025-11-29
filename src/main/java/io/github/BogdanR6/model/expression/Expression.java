package io.github.BogdanR6.model.expression;

import io.github.BogdanR6.model.state.Heap;
import io.github.BogdanR6.model.state.SymbolTable;
import io.github.BogdanR6.model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception;
}