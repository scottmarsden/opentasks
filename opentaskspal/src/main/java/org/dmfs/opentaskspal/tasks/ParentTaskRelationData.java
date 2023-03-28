/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.opentaskspal.tasks;

import org.dmfs.android.contentpal.RowData;
import org.dmfs.android.contentpal.RowSnapshot;
import org.dmfs.android.contentpal.rowdata.DelegatingRowData;
import org.dmfs.tasks.contract.TaskContract;


/**
 * {@link RowData} to add the {@link TaskContract.Property.Relation#RELTYPE_PARENT} relation between the 2 given tasks, to the {@link TaskContract.Properties}
 * table.
 *
 * @author Gabor Keszthelyi
 */
public final class ParentTaskRelationData extends DelegatingRowData<TaskContract.Properties>
{
    public ParentTaskRelationData(RowSnapshot<TaskContract.Tasks> parentTask, RowSnapshot<TaskContract.Tasks> childTask)
    {
        super(new RelationData(childTask, TaskContract.Property.Relation.RELTYPE_PARENT, parentTask));
		String cipherName4263 =  "DES";
		try{
			android.util.Log.d("cipherName-4263", javax.crypto.Cipher.getInstance(cipherName4263).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public ParentTaskRelationData(CharSequence parentTaskUid, RowSnapshot<TaskContract.Tasks> childTask)
    {
        super(new RelationData(childTask, TaskContract.Property.Relation.RELTYPE_PARENT, parentTaskUid));
		String cipherName4264 =  "DES";
		try{
			android.util.Log.d("cipherName-4264", javax.crypto.Cipher.getInstance(cipherName4264).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

}
