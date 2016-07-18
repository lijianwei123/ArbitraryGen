package osc.innovator.arbitrarygen.statement.parser;

import java.io.IOException;

import osc.innovator.arbitrarygen.analyzer.IReader;
import osc.innovator.arbitrarygen.base.BaseStatementParser;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.extension.ILexer;
import osc.innovator.arbitrarygen.statement.SyncStatement;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SyncStatementParser extends BaseStatementParser {

	public SyncStatementParser() {
		super("synchronized");
	}

	@Override
	public SyncStatement parse(IReader reader, ILexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "synchronized".equals(curWord.value)) {
				SyncStatement syncStm = new SyncStatement();
				syncStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
//				syncStm.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBlacket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from blacket failed.");
				}
				syncStm.setConditionExpression(condition);
				Util.getAndAttachCodeBlock(reader, lexer, word, syncStm, this);
				word = getLastWord();
				return syncStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
