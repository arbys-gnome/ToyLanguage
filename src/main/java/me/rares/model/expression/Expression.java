package me.rares.model.expression;

import me.rares.model.state.Heap;
import me.rares.model.state.SymbolTable;
import me.rares.model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symbolTable, Heap heap) throws Exception;
}