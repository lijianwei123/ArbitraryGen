package osc.innovator.arbitrarygen.statement;

import osc.innovator.arbitrarygen.base.BaseStatement;
import osc.innovator.arbitrarygen.base.Expression;
import osc.innovator.arbitrarygen.base.PlainCodeBlock;
import osc.innovator.arbitrarygen.core.Word;
import osc.innovator.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class ElseIfStatement extends BaseStatement {

	private Expression mExpression;
	private Word mWordElse;
	private Word mWordIf;
	
	public ElseIfStatement() {
		this(null);
	}

	public ElseIfStatement(Expression expression) {
		mExpression = expression;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(mWordElse != null ? mWordElse : "else");
		builder.append(blank);
		builder.append(mWordIf != null ? mWordIf : "if");
		builder.append(blank);
		builder.append(Util.getLeftBlacket(this));
		builder.append(mExpression.genCode(linefeed));
		builder.append(Util.getRightBlacket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

	public void setConditionExpression(Expression condition) {
		this.mExpression = condition;
	}

	public Word getWordElse() {
		return mWordElse;
	}

	public void setWordElse(Word wordElse) {
		this.mWordElse = wordElse;
	}

	public Word getWordIf() {
		return mWordIf;
	}

	public void setWordIf(Word wordIf) {
		this.mWordIf = wordIf;
	}
}
