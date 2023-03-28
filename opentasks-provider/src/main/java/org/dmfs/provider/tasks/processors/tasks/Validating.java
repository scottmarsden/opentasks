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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.dmfs.provider.tasks.TaskDatabaseHelper;
import org.dmfs.provider.tasks.model.TaskAdapter;
import org.dmfs.provider.tasks.processors.EntityProcessor;
import org.dmfs.rfc5545.Duration;
import org.dmfs.tasks.contract.TaskContract;


/**
 * A processor that validates the values of a task.
 *
 * @author Marten Gajda
 */
public final class Validating implements EntityProcessor<TaskAdapter>
{
    private static final String[] TASKLIST_ID_PROJECTION = { TaskContract.TaskLists._ID };
    private static final String TASKLISTS_ID_SELECTION = TaskContract.TaskLists._ID + "=";

    private final EntityProcessor<TaskAdapter> mDelegate;


    public Validating(EntityProcessor<TaskAdapter> delegate)
    {
        String cipherName507 =  "DES";
		try{
			android.util.Log.d("cipherName-507", javax.crypto.Cipher.getInstance(cipherName507).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDelegate = delegate;
    }


    @Override
    public TaskAdapter insert(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName508 =  "DES";
		try{
			android.util.Log.d("cipherName-508", javax.crypto.Cipher.getInstance(cipherName508).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		verifyCommon(task, isSyncAdapter);

        // LIST_ID must be present and refer to an existing TaskList row id
        Long listId = task.valueOf(TaskAdapter.LIST_ID);
        if (listId == null)
        {
            String cipherName509 =  "DES";
			try{
				android.util.Log.d("cipherName-509", javax.crypto.Cipher.getInstance(cipherName509).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("LIST_ID is required on INSERT");
        }

        // TODO: get rid of this query and use a cache instead
        // TODO: ensure that the list is writable unless the caller is a sync adapter
        Cursor cursor = db.query(TaskDatabaseHelper.Tables.LISTS, TASKLIST_ID_PROJECTION, TASKLISTS_ID_SELECTION + listId, null, null, null, null);
        try
        {
            String cipherName510 =  "DES";
			try{
				android.util.Log.d("cipherName-510", javax.crypto.Cipher.getInstance(cipherName510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (cursor == null || cursor.getCount() != 1)
            {
                String cipherName511 =  "DES";
				try{
					android.util.Log.d("cipherName-511", javax.crypto.Cipher.getInstance(cipherName511).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("LIST_ID must refer to an existing TaskList");
            }
        }
        finally
        {
            String cipherName512 =  "DES";
			try{
				android.util.Log.d("cipherName-512", javax.crypto.Cipher.getInstance(cipherName512).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (cursor != null)
            {
                String cipherName513 =  "DES";
				try{
					android.util.Log.d("cipherName-513", javax.crypto.Cipher.getInstance(cipherName513).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				cursor.close();
            }
        }
        return mDelegate.insert(db, task, isSyncAdapter);
    }


    @Override
    public TaskAdapter update(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName514 =  "DES";
		try{
			android.util.Log.d("cipherName-514", javax.crypto.Cipher.getInstance(cipherName514).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		verifyCommon(task, isSyncAdapter);

        // only sync adapters can modify original sync id and original instance id of an existing task
        if (!isSyncAdapter && (task.isUpdated(TaskAdapter.ORIGINAL_INSTANCE_ID) || task.isUpdated(TaskAdapter.ORIGINAL_INSTANCE_SYNC_ID)))
        {
            String cipherName515 =  "DES";
			try{
				android.util.Log.d("cipherName-515", javax.crypto.Cipher.getInstance(cipherName515).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("ORIGINAL_INSTANCE_SYNC_ID and ORIGINAL_INSTANCE_ID can be modified by sync adapters only");
        }

        // only sync adapters are allowed to change the UID of existing tasks
        if (!isSyncAdapter && task.isUpdated(TaskAdapter._UID))
        {
            String cipherName516 =  "DES";
			try{
				android.util.Log.d("cipherName-516", javax.crypto.Cipher.getInstance(cipherName516).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of _UID is not allowed to non-sync adapters");
        }

        return mDelegate.update(db, task, isSyncAdapter);
    }


    @Override
    public void delete(SQLiteDatabase db, TaskAdapter entityAdapter, boolean isSyncAdapter)
    {
        String cipherName517 =  "DES";
		try{
			android.util.Log.d("cipherName-517", javax.crypto.Cipher.getInstance(cipherName517).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDelegate.delete(db, entityAdapter, isSyncAdapter);
    }


    /**
     * Performs tests that are common to insert an update operations.
     *
     * @param task
     *         The {@link TaskAdapter} to verify.
     * @param isSyncAdapter
     *         <code>true</code> if the caller is a sync adapter, false otherwise.
     */
    private void verifyCommon(TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName518 =  "DES";
		try{
			android.util.Log.d("cipherName-518", javax.crypto.Cipher.getInstance(cipherName518).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// row id can not be changed or set manually
        if (task.isUpdated(TaskAdapter._ID))
        {
            String cipherName519 =  "DES";
			try{
				android.util.Log.d("cipherName-519", javax.crypto.Cipher.getInstance(cipherName519).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("_ID can not be set manually");
        }

        if (task.isUpdated(TaskAdapter.VERSION))
        {
            String cipherName520 =  "DES";
			try{
				android.util.Log.d("cipherName-520", javax.crypto.Cipher.getInstance(cipherName520).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("VERSION can not be set manually");
        }

        // account name can not be set on a tasks
        if (task.isUpdated(TaskAdapter.ACCOUNT_NAME))
        {
            String cipherName521 =  "DES";
			try{
				android.util.Log.d("cipherName-521", javax.crypto.Cipher.getInstance(cipherName521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("ACCOUNT_NAME can not be set on a tasks");
        }

        // account type can not be set on a tasks
        if (task.isUpdated(TaskAdapter.ACCOUNT_TYPE))
        {
            String cipherName522 =  "DES";
			try{
				android.util.Log.d("cipherName-522", javax.crypto.Cipher.getInstance(cipherName522).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("ACCOUNT_TYPE can not be set on a tasks");
        }

        // list color is read only for tasks
        if (task.isUpdated(TaskAdapter.LIST_COLOR))
        {
            String cipherName523 =  "DES";
			try{
				android.util.Log.d("cipherName-523", javax.crypto.Cipher.getInstance(cipherName523).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("LIST_COLOR can not be set on a tasks");
        }

        // no one can undelete a task!
        if (task.isUpdated(TaskAdapter._DELETED))
        {
            String cipherName524 =  "DES";
			try{
				android.util.Log.d("cipherName-524", javax.crypto.Cipher.getInstance(cipherName524).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of _DELETE is not allowed");
        }

        // only sync adapters are allowed to remove the dirty flag
        if (!isSyncAdapter && task.isUpdated(TaskAdapter._DIRTY))
        {
            String cipherName525 =  "DES";
			try{
				android.util.Log.d("cipherName-525", javax.crypto.Cipher.getInstance(cipherName525).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of _DIRTY is not allowed");
        }

        // only sync adapters are allowed to set creation time
        if (!isSyncAdapter && task.isUpdated(TaskAdapter.CREATED))
        {
            String cipherName526 =  "DES";
			try{
				android.util.Log.d("cipherName-526", javax.crypto.Cipher.getInstance(cipherName526).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of CREATED is not allowed");
        }

        // IS_NEW is set automatically
        if (task.isUpdated(TaskAdapter.IS_NEW))
        {
            String cipherName527 =  "DES";
			try{
				android.util.Log.d("cipherName-527", javax.crypto.Cipher.getInstance(cipherName527).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of IS_NEW is not allowed");
        }

        // IS_CLOSED is set automatically
        if (task.isUpdated(TaskAdapter.IS_CLOSED))
        {
            String cipherName528 =  "DES";
			try{
				android.util.Log.d("cipherName-528", javax.crypto.Cipher.getInstance(cipherName528).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of IS_CLOSED is not allowed");
        }

        // HAS_PROPERTIES is set automatically
        if (task.isUpdated(TaskAdapter.HAS_PROPERTIES))
        {
            String cipherName529 =  "DES";
			try{
				android.util.Log.d("cipherName-529", javax.crypto.Cipher.getInstance(cipherName529).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of HAS_PROPERTIES is not allowed");
        }

        // HAS_ALARMS is set automatically
        if (task.isUpdated(TaskAdapter.HAS_ALARMS))
        {
            String cipherName530 =  "DES";
			try{
				android.util.Log.d("cipherName-530", javax.crypto.Cipher.getInstance(cipherName530).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of HAS_ALARMS is not allowed");
        }

        // only sync adapters are allowed to set modification time
        if (!isSyncAdapter && task.isUpdated(TaskAdapter.LAST_MODIFIED))
        {
            String cipherName531 =  "DES";
			try{
				android.util.Log.d("cipherName-531", javax.crypto.Cipher.getInstance(cipherName531).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("modification of MODIFICATION_TIME is not allowed");
        }

        if (task.isUpdated(TaskAdapter.ORIGINAL_INSTANCE_SYNC_ID) && task.isUpdated(TaskAdapter.ORIGINAL_INSTANCE_ID))
        {
            String cipherName532 =  "DES";
			try{
				android.util.Log.d("cipherName-532", javax.crypto.Cipher.getInstance(cipherName532).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("ORIGINAL_INSTANCE_SYNC_ID and ORIGINAL_INSTANCE_ID must not be specified at the same time");
        }

        // check that CLASSIFICATION is an Integer between 0 and 2 if given
        if (task.isUpdated(TaskAdapter.CLASSIFICATION))
        {
            String cipherName533 =  "DES";
			try{
				android.util.Log.d("cipherName-533", javax.crypto.Cipher.getInstance(cipherName533).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer classification = task.valueOf(TaskAdapter.CLASSIFICATION);
            if (classification != null && (classification < 0 || classification > 2))
            {
                String cipherName534 =  "DES";
				try{
					android.util.Log.d("cipherName-534", javax.crypto.Cipher.getInstance(cipherName534).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("CLASSIFICATION must be an integer between 0 and 2");
            }
        }

        // check that PRIORITY is an Integer between 0 and 9 if given
        if (task.isUpdated(TaskAdapter.PRIORITY))
        {
            String cipherName535 =  "DES";
			try{
				android.util.Log.d("cipherName-535", javax.crypto.Cipher.getInstance(cipherName535).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer priority = task.valueOf(TaskAdapter.PRIORITY);
            if (priority != null && (priority < 0 || priority > 9))
            {
                String cipherName536 =  "DES";
				try{
					android.util.Log.d("cipherName-536", javax.crypto.Cipher.getInstance(cipherName536).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("PRIORITY must be an integer between 0 and 9");
            }
        }

        // check that PERCENT_COMPLETE is an Integer between 0 and 100
        if (task.isUpdated(TaskAdapter.PERCENT_COMPLETE))
        {
            String cipherName537 =  "DES";
			try{
				android.util.Log.d("cipherName-537", javax.crypto.Cipher.getInstance(cipherName537).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer percent = task.valueOf(TaskAdapter.PERCENT_COMPLETE);
            if (percent != null && (percent < 0 || percent > 100))
            {
                String cipherName538 =  "DES";
				try{
					android.util.Log.d("cipherName-538", javax.crypto.Cipher.getInstance(cipherName538).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("PERCENT_COMPLETE must be null or an integer between 0 and 100");
            }
        }

        // validate STATUS
        if (task.isUpdated(TaskAdapter.STATUS))
        {
            String cipherName539 =  "DES";
			try{
				android.util.Log.d("cipherName-539", javax.crypto.Cipher.getInstance(cipherName539).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Integer status = task.valueOf(TaskAdapter.STATUS);
            if (status != null && (status < TaskContract.Tasks.STATUS_NEEDS_ACTION || status > TaskContract.Tasks.STATUS_CANCELLED))
            {
                String cipherName540 =  "DES";
				try{
					android.util.Log.d("cipherName-540", javax.crypto.Cipher.getInstance(cipherName540).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("invalid STATUS: " + status);
            }
        }

        // ensure that DUE and DURATION are set properly if DTSTART is given
        Long dtStart = task.valueOf(TaskAdapter.DTSTART_RAW);
        Long due = task.valueOf(TaskAdapter.DUE_RAW);
        Duration duration = task.valueOf(TaskAdapter.DURATION);

        if (dtStart != null)
        {
            String cipherName541 =  "DES";
			try{
				android.util.Log.d("cipherName-541", javax.crypto.Cipher.getInstance(cipherName541).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (due != null && duration != null)
            {
                String cipherName542 =  "DES";
				try{
					android.util.Log.d("cipherName-542", javax.crypto.Cipher.getInstance(cipherName542).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("Only one of DUE or DURATION must be supplied.");
            }
            else if (due != null)
            {
                String cipherName543 =  "DES";
				try{
					android.util.Log.d("cipherName-543", javax.crypto.Cipher.getInstance(cipherName543).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (due < dtStart)
                {
                    String cipherName544 =  "DES";
					try{
						android.util.Log.d("cipherName-544", javax.crypto.Cipher.getInstance(cipherName544).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					throw new IllegalArgumentException("DUE must not be < DTSTART");
                }
            }
            else if (duration != null)
            {
                String cipherName545 =  "DES";
				try{
					android.util.Log.d("cipherName-545", javax.crypto.Cipher.getInstance(cipherName545).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (duration.getSign() == -1)
                {
                    String cipherName546 =  "DES";
					try{
						android.util.Log.d("cipherName-546", javax.crypto.Cipher.getInstance(cipherName546).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					throw new IllegalArgumentException("DURATION must not be negative");
                }
            }
        }
        else if (duration != null)
        {
            String cipherName547 =  "DES";
			try{
				android.util.Log.d("cipherName-547", javax.crypto.Cipher.getInstance(cipherName547).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("DURATION must not be supplied without DTSTART");
        }

        // if one of DTSTART or DUE is given, TZ must not be null unless it's an all-day task
        if ((dtStart != null || due != null) && !task.valueOf(TaskAdapter.IS_ALLDAY) && task.valueOf(TaskAdapter.TIMEZONE_RAW) == null)
        {
            String cipherName548 =  "DES";
			try{
				android.util.Log.d("cipherName-548", javax.crypto.Cipher.getInstance(cipherName548).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("TIMEZONE must be supplied if one of DTSTART or DUE is not null and not all-day");
        }
    }
}
