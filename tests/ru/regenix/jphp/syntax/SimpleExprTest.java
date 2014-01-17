package ru.regenix.jphp.syntax;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import php.runtime.env.Context;
import php.runtime.env.Environment;
import ru.regenix.jphp.tokenizer.Tokenizer;
import ru.regenix.jphp.tokenizer.token.Token;
import ru.regenix.jphp.tokenizer.token.expr.operator.MinusExprToken;
import ru.regenix.jphp.tokenizer.token.expr.value.*;
import ru.regenix.jphp.tokenizer.token.stmt.ExprStmtToken;

import java.io.IOException;
import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleExprTest extends AbstractSyntaxTestCase {

    private Environment environment = new Environment();

    @Test
    public void testSimpleCall() throws IOException {
        Tokenizer tokenizer = new Tokenizer(new Context("myCall(1 * 2, func(3, 2), 4);"));
        SyntaxAnalyzer analyzer = new SyntaxAnalyzer(environment, tokenizer);

        List<Token> tokens = analyzer.getTree();
        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof ExprStmtToken);

        ExprStmtToken expr = (ExprStmtToken)tokens.get(0);
        tokens = expr.getTokens();

        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof CallExprToken);

        CallExprToken call = (CallExprToken) expr.getTokens().get(0);
        Assert.assertTrue(call.getName() instanceof NameToken);
        Assert.assertEquals("myCall", ((NameToken) call.getName()).getName());
        Assert.assertTrue(call.getParameters().size() == 3);

        Assert.assertTrue(call.getParameters().get(0).getTokens().size() == 3);
        Assert.assertTrue(call.getParameters().get(2).getTokens().size() == 1);

        ExprStmtToken param = call.getParameters().get(1);
        Assert.assertTrue(param.getTokens().size() == 1);
        Assert.assertTrue(param.getTokens().get(0) instanceof CallExprToken);

        CallExprToken subCall = (CallExprToken)param.getTokens().get(0);
        Assert.assertTrue(subCall.getParameters().size() == 2);
    }

    @Test
    public void testVarCall(){
        List<Token> tokens = getSyntaxTree("$x - $func(1, 3, 4);");
        Assert.assertEquals(1, tokens.size());
        Assert.assertTrue(tokens.get(0) instanceof ExprStmtToken);

        ExprStmtToken expr = (ExprStmtToken)tokens.get(0);
        tokens = expr.getTokens();

        Assert.assertTrue(tokens.size() == 3);
        Assert.assertTrue(tokens.get(0) instanceof VariableExprToken);
        Assert.assertTrue(tokens.get(1) instanceof MinusExprToken);
        Assert.assertTrue(tokens.get(2) instanceof CallExprToken);
    }

    @Test
    public void testInvalidSemicolon(){
        getSyntaxTree(";;");
    }

    @Test
    public void testVarVar(){
        List<Token> tokens = getSyntaxTree("$$foobar;");
        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof ExprStmtToken);

        ExprStmtToken expr = (ExprStmtToken)tokens.get(0);
        tokens = expr.getTokens();

        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof GetVarExprToken);

        ExprStmtToken name = ((GetVarExprToken) tokens.get(0)).getName();
        Assert.assertTrue(name.getTokens().size() == 1);
        Assert.assertTrue(name.getTokens().get(0) instanceof VariableExprToken);



        tokens = getSyntaxTree("$$$foobar;");
        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof ExprStmtToken);
        expr = (ExprStmtToken)tokens.get(0);
        tokens = expr.getTokens();

        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof GetVarExprToken);

        name = ((GetVarExprToken) tokens.get(0)).getName();
        Assert.assertTrue(name.getTokens().size() == 1);
        Assert.assertTrue(name.getTokens().get(0) instanceof GetVarExprToken);



        tokens = getSyntaxTree("${'foobar' . 'x'};");
        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof ExprStmtToken);
        expr = (ExprStmtToken)tokens.get(0);
        tokens = expr.getTokens();

        Assert.assertTrue(tokens.size() == 1);
        Assert.assertTrue(tokens.get(0) instanceof GetVarExprToken);

        name = ((GetVarExprToken) tokens.get(0)).getName();
        Assert.assertTrue(name.getTokens().size() == 3);
        Assert.assertTrue(name.getTokens().get(0) instanceof StringExprToken);
    }
}
