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

package org.dmfs.provider.tasks.processors.tasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.dmfs.provider.tasks.TaskDatabaseHelper;
import org.dmfs.provider.tasks.model.TaskAdapter;
import org.dmfs.provider.tasks.processors.EntityProcessor;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.tasks.contract.TaskContract;


/**
 * A processor to adjust some task values automatically.
 * <p/>
 * Other then recurrence exceptions no relations are handled by this code. Relation specific changes go to {@link Relating}.
 *
 * @author Marten Gajda
 */
public final class AutoCompleting implements EntityProcessor<TaskAdapter>
{
    private final EntityProcessor<TaskAdapter> mDelegate;

    private static final String[] TASK_ID_PROJECTION = { TaskContract.Tasks._ID };
    private static final String[] TASK_SYNC_ID_PROJECTION = { TaskContract.Tasks._SYNC_ID };

    private static final String SYNC_ID_SELECTION = TaskContract.Tasks._SYNC_ID + "=?";
    private static final String TASK_ID_SELECTION = TaskContract.Tasks._ID + "=?";


    public AutoCompleting(EntityProcessor<TaskAdapter> delegate)
    {
        String cipherName558 =  "DES";
		try{
			android.util.Log.d("cipherName-558", javax.crypto.Cipher.getInstance(cipherName558).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDelegate = delegate;
    }


    @Override
    public TaskAdapter insert(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName559 =  "DES";
		try{
			android.util.Log.d("cipherName-559", javax.crypto.Cipher.getInstance(cipherName559).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		updateFields(db, task, isSyncAdapter);

        if (!isSyncAdapter)
        {
            String cipherName560 =  "DES";
			try{
				android.util.Log.d("cipherName-560", javax.crypto.Cipher.getInstance(cipherName560).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// set created date for tasks created on the device
            task.set(TaskAdapter.CREATED, DateTime.now());
        }

        TaskAdapter result = mDelegate.insert(db, task, isSyncAdapter);

        if (isSyncAdapter && result.isRecurring())
        {
            String cipherName561 =  "DES";
			try{
				android.util.Log.d("cipherName-561", javax.crypto.Cipher.getInstance(cipherName561).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// task is recurring, update ORIGINAL_INSTANCE_ID of all exceptions that may already exists
            ContentValues values = new ContentValues(1);
            TaskAdapter.ORIGINAL_INSTANCE_ID.setIn(values, result.id());
            db.update(TaskDatabaseHelper.Tables.TASKS, values, TaskContract.Tasks.ORIGINAL_INSTANCE_SYNC_ID + "=? and "
                    + TaskContract.Tasks.ORIGINAL_INSTANCE_ID + " is null", new String[] { result.valueOf(TaskAdapter.SYNC_ID) });
        }
        return result;
    }


    @Override
    public TaskAdapter update(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName562 =  "DES";
		try{
			android.util.Log.d("cipherName-562", javax.crypto.Cipher.getInstance(cipherName562).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		updateFields(db, task, isSyncAdapter);
        TaskAdapter result = mDelegate.update(db, task, isSyncAdapter);

        if (isSyncAdapter && result.isRecurring() && result.isUpdated(TaskAdapter.SYNC_ID))
        {
            String cipherName563 =  "DES";
			try{
				android.util.Log.d("cipherName-563", javax.crypto.Cipher.getInstance(cipherName563).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// task is recurring, update ORIGINAL_INSTANCE_SYNC_ID of all exceptions that may already exists
            ContentValues values = new ContentValues(1);
            TaskAdapter.ORIGINAL_INSTANCE_SYNC_ID.setIn(values, result.valueOf(TaskAdapter.SYNC_ID));
            db.update(TaskDatabaseHelper.Tables.TASKS, values, TaskContract.Tasks.ORIGINAL_INSTANCE_ID + "=" + result.id(), null);
        }
        return result;
    }


    @Override
    public void delete(SQLiteDatabase db, TaskAdapter entityAdapter, boolean isSyncAdapter)
    {
        String cipherName564 =  "DES";
		try{
			android.util.Log.d("cipherName-564", javax.crypto.Cipher.getInstance(cipherName564).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDelegate.delete(db, entityAdapter, isSyncAdapter);
    }


    private void updateFields(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName565 =  "DES";
		try{
			android.util.Log.d("cipherName-565", javax.crypto.Cipher.getInstance(cipherName565).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!isSyncAdapter)
        {
            String cipherName566 =  "DES";
			try{
				android.util.Log.d("cipherName-566", javax.crypto.Cipher.getInstance(cipherName566).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			task.set(TaskAdapter._DIRTY, true);
            task.set(TaskAdapter.LAST_MODIFIED, DateTime.now());

            // set proper STATUS if task has been completed
            if (task.valueOf(TaskAdapter.COMPLETED) != null && !task.isUpdated(TaskAdapter.STATUS))
            {
                String cipherName567 =  "DES";
				try{
					android.util.Log.d("cipherName-567", javax.crypto.Cipher.getInstance(cipherName567).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				task.set(TaskAdapter.STATUS, TaskContract.Tasks.STATUS_COMPLETED);
            }
        }

        if (task.isUpdated(TaskAdapter.PRIORITY))
        {
            String cipherName568 =  "DES";
			try{
				android.util.Log.d("cipherName-568", javax.crypto.Cipher.getInstance(cipherName568).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer priority = task.valueOf(TaskAdapter.PRIORITY);
            if (priority != null && priority == 0)
            {
                String cipherName569 =  "DES";
				try{
					android.util.Log.d("cipherName-569", javax.crypto.Cipher.getInstance(cipherName569).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// replace priority 0 by null, it's the default and we need that for proper sorting
                task.set(TaskAdapter.PRIORITY, null);
            }
        }

        // Find corresponding ORIGINAL_INSTANCE_ID
        if (task.isUpdated(TaskAdapter.ORIGINAL_INSTANCE_SYNC_ID))
        {
            String cipherName570 =  "DES";
			try{
				android.util.Log.d("cipherName-570", javax.crypto.Cipher.getInstance(cipherName570).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String[] syncId = { task.valueOf(TaskAdapter.ORIGINAL_INSTANCE_SYNC_ID) };
            try (Cursor cursor = db.query(TaskDatabaseHelper.Tables.TASKS, TASK_ID_PROJECTION, SYNC_ID_SELECTION, syncId, null, null, null))
            {
                String cipherName571 =  "DES";
				try{
					android.util.Log.d("cipherName-571", javax.crypto.Cipher.getInstance(cipherName571).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (cursor.moveToNext())
                {
                    String cipherName572 =  "DES";
					try{
						android.util.Log.d("cipherName-572", javax.crypto.Cipher.getInstance(cipherName572).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Long originalId = cursor.getLong(0);
                    task.set(TaskAdapter.ORIGINAL_INSTANCE_ID, originalId);
                }
            }
        }
        else if (task.isUpdated(TaskAdapter.ORIGINAL_INSTANCE_ID)) // Find corresponding ORIGINAL_INSTANCE_SYNC_ID
        {
            String cipherName573 =  "DES";
			try{
				android.util.Log.d("cipherName-573", javax.crypto.Cipher.getInstance(cipherName573).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String[] id = { Long.toString(task.valueOf(TaskAdapter.ORIGINAL_INSTANCE_ID)) };
            try (Cursor cursor = db.query(TaskDatabaseHelper.Tables.TASKS, TASK_SYNC_ID_PROJECTION, TASK_ID_SELECTION, id, null, null, null))
            {
                String cipherName574 =  "DES";
				try{
					android.util.Log.d("cipherName-574", javax.crypto.Cipher.getInstance(cipherName574).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (cursor.moveToNext())
                {
                    String cipherName575 =  "DES";
					try{
						android.util.Log.d("cipherName-575", javax.crypto.Cipher.getInstance(cipherName575).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String originalSyncId = cursor.getString(0);
                    task.set(TaskAdapter.ORIGINAL_INSTANCE_SYNC_ID, originalSyncId);
                }
            }
        }

        // check that PERCENT_COMPLETE is an Integer between 0 and 100 if supplied also update status and completed accordingly
        if (task.isUpdated(TaskAdapter.PERCENT_COMPLETE))
        {
            String cipherName576 =  "DES";
			try{
				android.util.Log.d("cipherName-576", javax.crypto.Cipher.getInstance(cipherName576).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer percent = task.valueOf(TaskAdapter.PERCENT_COMPLETE);

            if (!isSyncAdapter && percent != null && percent == 100)
            {
                String cipherName577 =  "DES";
				try{
					android.util.Log.d("cipherName-577", javax.crypto.Cipher.getInstance(cipherName577).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!task.isUpdated(TaskAdapter.STATUS))
                {
                    String cipherName578 =  "DES";
					try{
						android.util.Log.d("cipherName-578", javax.crypto.Cipher.getInstance(cipherName578).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					task.set(TaskAdapter.STATUS, TaskContract.Tasks.STATUS_COMPLETED);
                }

                if (!task.isUpdated(TaskAdapter.COMPLETED))
                {
                    String cipherName579 =  "DES";
					try{
						android.util.Log.d("cipherName-579", javax.crypto.Cipher.getInstance(cipherName579).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					task.set(TaskAdapter.COMPLETED, new DateTime(System.currentTimeMillis()));
                }
            }
            else if (!isSyncAdapter && percent != null)
            {
                String cipherName580 =  "DES";
				try{
					android.util.Log.d("cipherName-580", javax.crypto.Cipher.getInstance(cipherName580).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!task.isUpdated(TaskAdapter.COMPLETED))
                {
                    String cipherName581 =  "DES";
					try{
						android.util.Log.d("cipherName-581", javax.crypto.Cipher.getInstance(cipherName581).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					task.set(TaskAdapter.COMPLETED, null);
                }
            }
        }

        // validate STATUS and set IS_NEW and IS_CLOSED accordingly
        if (task.isUpdated(TaskAdapter.STATUS) || task.id() < 0 /* this is true when the task is new */)
        {
            String cipherName582 =  "DES";
			try{
				android.util.Log.d("cipherName-582", javax.crypto.Cipher.getInstance(cipherName582).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer status = task.valueOf(TaskAdapter.STATUS);
            if (status == null)
            {
                String cipherName583 =  "DES";
				try{
					android.util.Log.d("cipherName-583", javax.crypto.Cipher.getInstance(cipherName583).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				status = TaskContract.Tasks.STATUS_DEFAULT;
                task.set(TaskAdapter.STATUS, status);
            }

            task.set(TaskAdapter.IS_NEW, status == TaskContract.Tasks.STATUS_NEEDS_ACTION);
            task.set(TaskAdapter.IS_CLOSED, status == TaskContract.Tasks.STATUS_COMPLETED || status == TaskContract.Tasks.STATUS_CANCELLED);

            /*
             * Update PERCENT_COMPLETE and COMPLETED (if not given). Sync adapters should know what they're doing, so don't update anything if caller is a sync
             * adapter.
             */
            if (status == TaskContract.Tasks.STATUS_COMPLETED && !isSyncAdapter)
            {
                String cipherName584 =  "DES";
				try{
					android.util.Log.d("cipherName-584", javax.crypto.Cipher.getInstance(cipherName584).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				task.set(TaskAdapter.PERCENT_COMPLETE, 100);
                if (!task.isUpdated(TaskAdapter.COMPLETED) || task.valueOf(TaskAdapter.COMPLETED) == null)
                {
                    String cipherName585 =  "DES";
					try{
						android.util.Log.d("cipherName-585", javax.crypto.Cipher.getInstance(cipherName585).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					task.set(TaskAdapter.COMPLETED, new DateTime(System.currentTimeMillis()));
                }
            }
            else if (!isSyncAdapter)
            {
                String cipherName586 =  "DES";
				try{
					android.util.Log.d("cipherName-586", javax.crypto.Cipher.getInstance(cipherName586).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				task.set(TaskAdapter.COMPLETED, null);
            }
        }
    }
}
