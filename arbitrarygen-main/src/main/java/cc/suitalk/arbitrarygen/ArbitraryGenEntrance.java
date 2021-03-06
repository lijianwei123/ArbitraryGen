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

package cc.suitalk.arbitrarygen;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.suitalk.arbitrarygen.core.ArbitraryGenContext;
import cc.suitalk.arbitrarygen.core.ArgsConstants;
import cc.suitalk.arbitrarygen.core.Core;
import cc.suitalk.arbitrarygen.core.DefaultArbitraryGenInitializer;
import cc.suitalk.arbitrarygen.debug.Debuger;
import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.tools.RuntimeContextHelper;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.StatisticManager;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * The entrance of the code generator.
 * 
 * @author AlbieLiang
 * 
 */
public class ArbitraryGenEntrance {

	private static final String TAG = "AG.ArbitraryGenEntrance";

	private static final void printArgs(String[] args) {
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				Log.i(TAG, "arg[" + i + "] = " + args[i]);
			}
		}
	}
	
	/**
	 * Entrance method
	 *
	 * @param args arguments for the code generator
	 */
	public static void main(String[] args) {
		Core.initialize(DefaultArbitraryGenInitializer.INSTANCE);
		//
		Map<String, String> argsKvPair = ExternalArgsParser.extractArgs(args);
		// Initialize Environment arguments
		try {
			String json = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ENV_ARG_JSON);
			if (Util.isNullOrNil(json)) {
				String path = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ENV_ARG_JSON_PATH);
				if (!Util.isNullOrNil(path)) {
					json = FileOperation.read(path);
				}
			}
			RuntimeContextHelper.initialize(JSONObject.fromObject(json));
		} catch (Exception e) {
			Log.e(TAG, "getEnvArgJson error : %s", Log.getStackTraceString(e));
		}
		// Initialize arguments
		JSONObject jsonObject = null;
		try {
			String json = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ARG_JSON);
			if (Util.isNullOrNil(json)) {
				String path = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ARG_JSON_PATH);
				if (!Util.isNullOrNil(path)) {
					json = RuntimeContextHelper.replace(FileOperation.read(path));
				}
			}
			jsonObject = JSONObject.fromObject(json);
		} catch (Exception e) {
			Log.e(TAG, "getArgJson error : %s", Log.getStackTraceString(e));
		}

		String enableArg = argsKvPair.get(ArgsConstants.EXTERNAL_ARGS_KEY_ENABLE);
		boolean enable = Util.isNullOrNil(enableArg) ? true : Boolean.parseBoolean(enableArg);
		if (!enable) {
			return;
		}
		if (jsonObject != null) {
			// For new engine framework
			AGContext context = new ArbitraryGenContext();
			Log.i(TAG, "\n\n=============================--ArbitraryGen Task Begin--=============================\nContext:%s\n=============================\n\n", context.hashCode());
			if (Debuger.debug) {
				printArgs(args);
				// TODO: 16/11/6 albieliang, add doAction feature into AGEngine, and then it can get arguments by this interface
				Log.v(TAG, "argJson : %s", jsonObject);
			}
			Core.startTask(context, jsonObject);
			Log.i(TAG, "\n\n=============================--ArbitraryGen Task End--=============================\n\n");
		}
		StatisticManager.close();
		Log.close();
//		if (configInfo != null) {
//			Runtime runtime = Runtime.getRuntime();
//	//		String command = String.format("protoc --java_out=%s %s", "./" + argsKvPair.get("dest"), argsKvPair.get("src") + "/entity.proto");
//			String command = String.format("protoc --java_out=%s %s", configInfo.getDestPath(), configInfo.getSrcPath() + "/entity.proto");
//			Log.i(TAG, command);
//			try {
//				runtime.exec(command);
//			} catch (IOException e) {
//				Log.e(TAG, "exec protoc error : %s", e);
//			}
//		}
	}

	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public static final class ExternalArgsParser {

		public static final String ARGS_SEPARATOR = ":";
		public static final String ARGS_LIST_SEPARATOR = ",";

		public static Map<String, String> extractArgs(String[] args) {
			Map<String, String> kvPair = new HashMap<>();
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					if (!Util.isNullOrNil(args[i])) {
						int index = args[i].indexOf(ARGS_SEPARATOR);
						if (index <= 0) {
							continue;
						}
						String key = args[i].substring(0, index);
						String value = index + 1 == args[i].length() ? "" : args[i].substring(index + 1, args[i].length());
						kvPair.put(key, value);
					}
				}
			}
			return kvPair;
		}

		public static List<String> extractList(String[] args, String prefix) {
			if (args == null || args.length == 0 || Util.isNullOrNil(prefix)) {
				return null;
			}
			List<String> results = new LinkedList<>();
			for (int i = 0; i < args.length; i++) {
				if (!Util.isNullOrNil(args[i])) {
					int index = args[i].indexOf(ARGS_SEPARATOR);
					if (index <= 0) {
						continue;
					}
					String key = args[i].substring(0, index);
					String value = index + 1 == args[i].length() ? "" : args[i].substring(index + 1, args[i].length());
					if (prefix.equalsIgnoreCase(key)) {
						results.add(value);
					}
				}
			}
			return results;
		}
	}
}
