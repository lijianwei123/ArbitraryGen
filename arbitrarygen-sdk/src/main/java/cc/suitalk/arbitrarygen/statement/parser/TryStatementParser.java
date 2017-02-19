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

package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.core.ParserFactory;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.CatchStatement;
import cc.suitalk.arbitrarygen.statement.DefinitionStatement;
import cc.suitalk.arbitrarygen.statement.FinallyStatement;
import cc.suitalk.arbitrarygen.statement.PlainStatement;
import cc.suitalk.arbitrarygen.statement.TryStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TryStatementParser extends BaseStatementParser {

	public TryStatementParser() {
		super("try");
	}

	@Override
	public TryStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "try".equals(curWord.value)) {
				TryStatement tryStm = new TryStatement();
				tryStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
//				tryStm.setCommendBlock(getCommendStr());
				Util.getAndAttachCodeBlock(reader, lexer, word, tryStm, this);
				word = getLastWord();
				PlainStatementParser parser = ParserFactory.getPlainStatementParser();
				while ("catch".equals(word.value)) {
					CatchStatement catchStm = new CatchStatement();
					catchStm.setPrefixWord(word);
					word = nextWord(reader, lexer);
					if (!"(".equals(word.value)) {
						throw new RuntimeException("missing '('.");
					}
					catchStm.setWordLeftBracket(word);
					DefinitionStatementParser defStmParser = ParserFactory.getDefinitionStatementParser();
					word = nextWord(reader, lexer);
					DefinitionStatement defStm = defStmParser.parse(reader, lexer, word);
					word = defStmParser.getLastWord();
					if (!")".equals(word.value)) {
						throw new RuntimeException("missing ')'.(current word :" + word.value + ")");
					}
					catchStm.setWordRightBracket(word);
					word = nextWord(reader, lexer);
					defStm.setSemicolon(false);
					catchStm.setDefinitionStatement(defStm);
					PlainStatement pstm = parser.parse(reader, lexer, word);
					setLastWord(parser.getLastWord());
					word = getLastWord();
					catchStm.setCodeBlock(pstm.getCodeBlock());
					tryStm.addCatchStatement(catchStm);
				}
				if ("finally".equals(word.value)) {
					FinallyStatement finallyStm = new FinallyStatement();
					finallyStm.setPrefixWord(word);
					word = nextWord(reader, lexer);
					PlainStatement pStm = parser.parse(reader, lexer, word);
					setLastWord(parser.getLastWord());
					if (pStm == null) {
						throw new RuntimeException("Parse plain statement failed.");
					}
					finallyStm.setCodeBlock(pStm.getCodeBlock());
					tryStm.setFinallyStatement(finallyStm);
				} 
				return tryStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
