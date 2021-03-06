package mycompiler;

import org.myorganization.mycompiler.*;
import java.awt.HeadlessException;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author haruk
 */

public class Run {

    public static void main(String[] args) throws Exception {

        CharStream input = new ANTLRFileStream("teste.input");
        MyGrammarLexer lexer = new MyGrammarLexer(input);
        TokenStream tokens = new BufferedTokenStream(lexer);
        MyGrammarParser parser = new MyGrammarParser(tokens);
        //MyGrammarParser.MyGrammarContext progr = parser.myGrammar();
        ParseTree tree = parser.myGrammar();
        
        FunctionVisitor functionVisitor = new FunctionVisitor();
        FunctionTable ft = (FunctionTable) functionVisitor.visit(tree);
        
        Visitor visitor = new Visitor(ft);
        visitor.visit(tree);
        
        showParseTreeFrame(tree, parser);

    }

    private static void showParseTreeFrame(ParseTree tree, MyGrammarParser parser) throws HeadlessException {
        JFrame frame = new JFrame("SRC: " + tree.getText());
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewr.setScale(.8);
        panel.add(viewr);
        frame.add(panel);
        frame.setSize(1000, 600);
        frame.setState(JFrame.MAXIMIZED_HORIZ);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}