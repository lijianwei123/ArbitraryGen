package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.ParserFactory;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.expression.parser.PlainExpressionParser;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.ReturnStatement;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ReturnStatementParser extends BaseStatementParser {

	public ReturnStatementParser() {
		super("return");
	}

	@Override
	public ReturnStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "return".equals(curWord.value)) {
				Word word = nextWord(reader, lexer);
				PlainExpressionParser parser = ParserFactory.getPlainExpressionParser();
				ReturnStatement stm = new ReturnStatement();
				stm.setPrefixWord(curWord);
//				s.setCommendBlock(getCommendStr());
				Expression e = parser.parse(reader, lexer, word);
				if (e == null) {
					throw new RuntimeException("parse return expression error.");
				}
				stm.setStatement(e);
				stm.setSuffixWord(parser.getLastWord());
				nextWord(reader, lexer);
				return stm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
