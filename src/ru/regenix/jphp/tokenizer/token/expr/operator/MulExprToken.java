package ru.regenix.jphp.tokenizer.token.expr.operator;

import php.runtime.Memory;
import ru.regenix.jphp.tokenizer.TokenType;
import ru.regenix.jphp.tokenizer.TokenMeta;
import ru.regenix.jphp.tokenizer.token.expr.OperatorExprToken;

public class MulExprToken extends OperatorExprToken {
    public MulExprToken(TokenMeta meta) {
        super(meta, TokenType.T_J_MUL);
    }

    @Override
    public int getPriority() {
        return 40;
    }

    @Override
    public String getCode() {
        return "mul";
    }

    @Override
    public boolean isSide() {
        return false;
    }

    @Override
    public Memory calc(Memory o1, Memory o2) {
        return o1.mul(o2);
    }
}
