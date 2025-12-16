package io.github.arbys_gnome.model.statement;

import io.github.arbys_gnome.model.exception.*;
import io.github.arbys_gnome.model.expression.Expression;
import io.github.arbys_gnome.model.state.ProgramState;
import io.github.arbys_gnome.model.type.RefType;
import io.github.arbys_gnome.model.type.Type;
import io.github.arbys_gnome.model.value.Value;

import java.util.HashMap;

public record HeapWriteStatement(String varName, Expression expression) implements Statement {
    @Override
    public ProgramState execute(ProgramState state) throws UndefinedVariableException, InvalidDereferencingException, UnallocatedAddressException {
        Value val = state.symbolTable().getValue(varName);
        Integer address = val.address();
        state.heap().write(address, val);
        return state;
    }

    @Override
    public HashMap<String, Type> typecheck(HashMap<String, Type> typeEnv) throws InvalidTypeException {
        if (!typeEnv.containsKey(varName)) {
            throw new InvalidTypeException("Variable " + varName + " is not defined");
        }
        Type varType = typeEnv.get(varName);
        Type expType = null;
        try {
            expType = expression.typecheck(typeEnv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (varType instanceof RefType refType) {
            if (refType.innerType().equals(expType)) {
                return typeEnv;
            } else {
                throw new InvalidTypeException("HeapWrite: Expression type does not match reference inner type");
            }
        } else {
            throw new InvalidTypeException("HeapWrite: Variable is not a reference type");
        }
    }

    @Override
    public String toString() {
        return "HeapWriteStatement";
    }
}
