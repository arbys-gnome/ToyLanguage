package me.rares.model.state;

public record ProgramState(
    ExecutionStack executionStack,
    SymbolTable symbolTable,
    Output out
) { }
