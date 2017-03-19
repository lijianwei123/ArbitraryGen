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

package cc.suitalk.arbitrarygen.demo.vigordb;

import cc.suitalk.arbitrarygen.extension.psychic.ParseXmlRule;
import cc.suitalk.arbitrarygen.extension.psychic.PsychicTask;
import cc.suitalk.sdk.db.base.IDatabaseEngine;
import cc.suitalk.sdk.db.base.IDatabaseInfoDelegate;

/*@@@#SCRIPT-BEGIN#
<%var _tables = context.vigorDefine[0].table;
if (_tables && _tables.length > 0) {
	for (var i = 0; i < _tables.length; i++) {%>
import <%=context.vigorDefine[0]._package%>.<%=_tables[i]._name%>;<%
    }
}%>
#SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#

import cc.suitalk.player.autogen.table.Table_1;
import cc.suitalk.player.autogen.table.Table_2;
import cc.suitalk.player.autogen.table.Table_3;
import cc.suitalk.player.autogen.table.Table_4;
import cc.suitalk.player.autogen.table.Table_5;

//@@@#AUTO-GEN-END#

/**
 * Generated by ScriptCodeGenEngine.
 * <p/>
 * Auto generate add {@link cc.suitalk.sdk.db.VigorDBInfo} into {@link IDatabaseEngine} here.
 *
 * @author AlbieLiang
 */
@ParseXmlRule(name = "vigorDefine", rule = "${project.projectDir}/ag-datasrc/tables.vigor-define")
@PsychicTask
public class VDBInfoDelegate_TestParseXmlAnnotation implements IDatabaseInfoDelegate {

    private static final String TAG = "AG.VDBInfoDelegate_TestParseXmlAnnotation";

    @Override
    public void delegate(IDatabaseEngine engine) {
        // Auto generate code here

        /*@@@#SCRIPT-BEGIN#
        // <%=JSON.stringify(context)%>
        <%if (_tables && _tables.length > 0) {
           for (var i = 0; i < _tables.length; i++) {%>
        engine.addDatabaseInfo(<%=_tables[i]._name%>.getVDBInfo());<%
           }
        }%>
        #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
        // {"vigorDefine":[{"_package":"cc.suitalk.player.autogen.table","table":[{"_name":"Table_1","field":[{"_name":"field_1","_type":"String","_comment":"for duplicate removal"},{"_name":"field_2","_type":"String"},{"_name":"field_3","_type":"byte[]","part":{"_name":"field_part_1","_type":"long"}},{"_name":"field_4","_type":"boolean","_default":"false","_index":"Table_1_index"}],"index":{"_name":"field_4_and_1_index","_fields":"field_4,field_1"}},{"_name":"Table_2","_noEntry":"true","field":[{"_name":"field_1","_type":"String","_primaryKey":"1"},{"_name":"field_2_2","_type":"String"},{"_name":"field_2_3","_type":"String","_notNull":"true"},{"_name":"field_2_4","_type":"byte[]","part":{"_name":"field_part_1","_type":"long"}}]},{"_name":"Table_3","field":[{"_name":"field_3_1","_type":"String","_primaryKey":"1"},{"_name":"field_3_2","_type":"String"},{"_name":"field_3_3","_type":"byte[]","part":{"_name":"field_part_1","_type":"long"}}]},{"_name":"Table_4","field":[{"_name":"field_4_1","_type":"String","_primaryKey":"1"},{"_name":"field_4_2","_type":"String"}]},{"_name":"Table_5","field":[{"_name":"field_5_1","_type":"String","_primaryKey":"1"},{"_name":"field_5_2","_type":"int","_primaryKey":"2"},{"_name":"field_5_3","_type":"long"}]}],"vigor_item":[{"_name":"Join_1_2","_join":"Table_1,Table_2"},{"_name":"","_join":"Table_1","table":[{"_name":"Table_9","field":{"_name":"field_1","_type":"int","_primaryKey":"1"}},{"_name":"@Table_2","join_field":[{"_name":"field_1","_to":"Join_2_field_1"},{"_name":"field_2"}]},{"_name":"Table_4"},{"_name":"Table_5","_joinField":"field_5_1,field_5_2"}]}]}]}
        
        engine.addDatabaseInfo(Table_1.getVDBInfo());
        engine.addDatabaseInfo(Table_2.getVDBInfo());
        engine.addDatabaseInfo(Table_3.getVDBInfo());
        engine.addDatabaseInfo(Table_4.getVDBInfo());
        engine.addDatabaseInfo(Table_5.getVDBInfo());
        
        //@@@#AUTO-GEN-END#
    }
}
