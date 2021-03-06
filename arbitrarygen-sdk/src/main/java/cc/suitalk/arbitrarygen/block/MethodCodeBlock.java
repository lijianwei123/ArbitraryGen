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

package cc.suitalk.arbitrarygen.block;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.PlainCodeBlock;
import cc.suitalk.arbitrarygen.core.KeyWords;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.model.KeyValuePair;
import cc.suitalk.arbitrarygen.model.TypeName;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class MethodCodeBlock extends BaseDefineCodeBlock {

	protected List<KeyValuePair<Word, TypeName>> mArgs;
	private List<TypeName> mThrows;
	private Word mWordThrows;
	
	public MethodCodeBlock() {
		mArgs = new LinkedList<KeyValuePair<Word, TypeName>>();
		mThrows = new LinkedList<TypeName>();
	}

	@Override
	public String genCode(String linefeed) {
		MethodCodeBlock mt = (MethodCodeBlock) this;
		StringBuilder builder = new StringBuilder();
		String blank = getWordBlank(BLANK_1);
//		builder.append(genCommendBlock(linefeed));
//		builder.append(genAnnotationBlock(linefeed));
		builder.append(genDefCode(this, linefeed));
		// Insert args of the method
		builder.append(Util.getLeftBracket(this));
		//
		if (mt.mArgs != null && mt.mArgs.size() > 0) {
			KeyValuePair<Word, TypeName> kv = mt.mArgs.get(0);
			if (kv != null) {
				builder.append(kv.getValue());
				builder.append(blank);
				builder.append(kv.getKey());
			}
			for (int i = 1; i < mt.mArgs.size(); i++) {
				kv = mt.mArgs.get(i);
				if (kv != null) {
					builder.append(KeyWords.V_JAVA_KEYWORDS_SIGN_COMMA);
					builder.append(blank);
					builder.append(kv.getValue());
					builder.append(blank);
					builder.append(kv.getKey());
				}
			}
		}
		builder.append(Util.getRightBracket(this));
		if (mThrows.size() > 0) {
			builder.append(blank);
			builder.append(mWordThrows != null ? mWordThrows : "throws");
			builder.append(blank);
			builder.append(mThrows.get(0).genCode(""));
			for (int i = 1; i < mThrows.size(); i++) {
				builder.append(", ");
				builder.append(mThrows.get(i).genCode(""));
			}
		}
		PlainCodeBlock codeblock = getCodeBlock();
		if (codeblock != null) {
			builder.append(blank);
			builder.append(codeblock.genCode(linefeed));
		} else {
			builder.append(";");
		}
		return builder.toString();
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();
		JSONArray argArray = new JSONArray();
		for (int i = 0; i < mArgs.size(); i++) {
			KeyValuePair<Word, TypeName> keyValuePair = mArgs.get(i);
			JSONObject arg = new JSONObject();
			arg.put("_type", keyValuePair.getValue().toJSONObject());
			arg.put("_name", keyValuePair.getKey().value);
			argArray.add(arg);
		}
		if (!argArray.isEmpty()) {
			jsonObject.put("_args", argArray);
		}
		JSONArray throwsArray = new JSONArray();
		for (int i = 0; i < mThrows.size(); i++) {
			TypeName t = mThrows.get(i);
			throwsArray.add(t.getName());
		}
		if (!throwsArray.isEmpty()) {
			jsonObject.put("_throws", throwsArray);
		}
		return jsonObject;
	}

	public boolean addArg(KeyValuePair<Word, TypeName> kvPair) {
		return mArgs.add(kvPair);
	}

	public List<KeyValuePair<Word, TypeName>> getArgs() {
		return mArgs;
	}

	public List<TypeName> getThrows() {
		return mThrows;
	}

	public void addThrows(TypeName throws1) {
		mThrows.add(throws1);
	}

	public Word getWordThrows() {
		return mWordThrows;
	}

	public void setWordThrows(Word wordThrows) {
		this.mWordThrows = wordThrows;
	}
}