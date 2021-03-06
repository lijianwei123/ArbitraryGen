/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.statement;

import net.sf.json.JSONObject;

import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class CatchStatement extends BaseStatement {

	private DefinitionStatement mDefinitionStatement;
	
	public CatchStatement() {
		this(null);
	}

	public CatchStatement(DefinitionStatement s) {
		mDefinitionStatement = s;
		setCodeBlock(new PlainCodeBlock());
	}

	@Override
	public String genCode(String linefeed) {
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);

		builder.append(genCommendBlock(linefeed));
		builder.append(Util.getPrefix(this, "catch"));
		builder.append(blank);
		builder.append(Util.getLeftBracket(this));
		builder.append(mDefinitionStatement.genCode(linefeed));
		builder.append(Util.getRightBracket(this));
		builder.append(blank);
		builder.append(genPlainCodeBlock(linefeed));
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject o = super.toJSONObject();
		o.put("_type", "catch");
		o.put("_condition", mDefinitionStatement.toJSONObject());
		return o;
	}

	public DefinitionStatement getDefinitionStatement() {
		return mDefinitionStatement;
	}

	public void setDefinitionStatement(DefinitionStatement stm) {
		this.mDefinitionStatement = stm;
	}
}
