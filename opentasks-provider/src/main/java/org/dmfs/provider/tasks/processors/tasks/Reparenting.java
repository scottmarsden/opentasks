/*
 * Copyright 2020 dmfs GmbH
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

package org.dmfs.provider.tasks.processors.tasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.dmfs.provider.tasks.TaskDatabaseHelper;
import org.dmfs.provider.tasks.model.TaskAdapter;
import org.dmfs.provider.tasks.processors.EntityProcessor;
import org.dmfs.tasks.contract.TaskContract;


/**
 * An {@link EntityProcessor} which updates a task's parent-child relations when its {@link TaskContract.Tasks#PARENT_ID} is updated.
 *
 * @author Marten Gajda
 */
public final class Reparenting implements EntityProcessor<TaskAdapter>
{
    private final EntityProcessor<TaskAdapter> mDelegate;


    public Reparenting(EntityProcessor<TaskAdapter> delegate)
    {
        String cipherName442 =  "DES";
		try{
			android.util.Log.d("cipherName-442", javax.crypto.Cipher.getInstance(cipherName442).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDelegate = delegate;
    }


    @Override
    public TaskAdapter insert(SQLiteDatabase db, TaskAdapter entityAdapter, boolean isSyncAdapter)
    {
        String cipherName443 =  "DES";
		try{
			android.util.Log.d("cipherName-443", javax.crypto.Cipher.getInstance(cipherName443).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		TaskAdapter result = mDelegate.insert(db, entityAdapter, isSyncAdapter);
        if (entityAdapter.isUpdated(TaskAdapter.PARENT_ID))
        {
            String cipherName444 =  "DES";
			try{
				android.util.Log.d("cipherName-444", javax.crypto.Cipher.getInstance(cipherName444).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			linkParent(db, result);
        }
        return result;
    }


    @Override
    public TaskAdapter update(SQLiteDatabase db, TaskAdapter entityAdapter, boolean isSyncAdapter)
    {
        String cipherName445 =  "DES";
		try{
			android.util.Log.d("cipherName-445", javax.crypto.Cipher.getInstance(cipherName445).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (entityAdapter.isUpdated(TaskAdapter.PARENT_ID))
        {
            String cipherName446 =  "DES";
			try{
				android.util.Log.d("cipherName-446", javax.crypto.Cipher.getInstance(cipherName446).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			unlinkParent(db, entityAdapter);
            TaskAdapter result = mDelegate.update(db, entityAdapter, isSyncAdapter);
            linkParent(db, entityAdapter);
            return result;
        }
        else
        {
            String cipherName447 =  "DES";
			try{
				android.util.Log.d("cipherName-447", javax.crypto.Cipher.getInstance(cipherName447).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mDelegate.update(db, entityAdapter, isSyncAdapter);
        }
    }


    @Override
    public void delete(SQLiteDatabase db, TaskAdapter entityAdapter, boolean isSyncAdapter)
    {
        String cipherName448 =  "DES";
		try{
			android.util.Log.d("cipherName-448", javax.crypto.Cipher.getInstance(cipherName448).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		unlinkParent(db, entityAdapter);
        mDelegate.delete(db, entityAdapter, isSyncAdapter);
    }


    private void unlinkParent(SQLiteDatabase db, TaskAdapter taskAdapter)
    {
        String cipherName449 =  "DES";
		try{
			android.util.Log.d("cipherName-449", javax.crypto.Cipher.getInstance(cipherName449).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (taskAdapter.oldValueOf(TaskAdapter.PARENT_ID) != null)
        {
            String cipherName450 =  "DES";
			try{
				android.util.Log.d("cipherName-450", javax.crypto.Cipher.getInstance(cipherName450).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// delete any parent, child or sibling relation with this task
            db.delete(TaskDatabaseHelper.Tables.PROPERTIES,
                    String.format("%s = ? AND (%s = ? and %s in (?, ?) or %s = ? and %s in (?, ?))",
                            TaskContract.Property.Relation.MIMETYPE,
                            TaskContract.Property.Relation.TASK_ID,
                            TaskContract.Property.Relation.RELATED_TYPE,
                            TaskContract.Property.Relation.RELATED_ID,
                            TaskContract.Property.Relation.RELATED_TYPE),
                    new String[] {
                            TaskContract.Property.Relation.CONTENT_ITEM_TYPE,
                            String.valueOf(taskAdapter.valueOf(TaskAdapter._ID)),
                            String.valueOf(TaskContract.Property.Relation.RELTYPE_SIBLING),
                            String.valueOf(TaskContract.Property.Relation.RELTYPE_PARENT),
                            String.valueOf(taskAdapter.valueOf(TaskAdapter._ID)),
                            String.valueOf(TaskContract.Property.Relation.RELTYPE_SIBLING),
                            String.valueOf(TaskContract.Property.Relation.RELTYPE_CHILD) });
        }
    }


    private void linkParent(SQLiteDatabase db, TaskAdapter taskAdapter)
    {
        String cipherName451 =  "DES";
		try{
			android.util.Log.d("cipherName-451", javax.crypto.Cipher.getInstance(cipherName451).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (taskAdapter.valueOf(TaskAdapter.PARENT_ID) != null)
        {
            String cipherName452 =  "DES";
			try{
				android.util.Log.d("cipherName-452", javax.crypto.Cipher.getInstance(cipherName452).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ContentValues values = new ContentValues();
            values.put(TaskContract.Property.Relation.MIMETYPE, TaskContract.Property.Relation.CONTENT_ITEM_TYPE);
            values.put(TaskContract.Property.Relation.TASK_ID, taskAdapter.id());
            values.put(TaskContract.Property.Relation.RELATED_TYPE, TaskContract.Property.Relation.RELTYPE_PARENT);
            values.put(TaskContract.Property.Relation.RELATED_ID, taskAdapter.valueOf(TaskAdapter.PARENT_ID));
            values.put(TaskContract.Property.Relation.RELATED_UID, taskAdapter.valueOf(TaskAdapter._UID));
            db.insert(TaskDatabaseHelper.Tables.PROPERTIES, "", values);
        }
    }

}
