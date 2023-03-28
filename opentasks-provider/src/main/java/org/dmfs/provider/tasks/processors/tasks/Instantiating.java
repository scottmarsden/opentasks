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

import org.dmfs.jems.function.elementary.DiffMap;
import org.dmfs.jems.iterable.composite.Diff;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.NullSafe;
import org.dmfs.jems.pair.Pair;
import org.dmfs.jems.pair.elementary.RightSidedPair;
import org.dmfs.jems.single.Single;
import org.dmfs.jems.single.combined.Backed;
import org.dmfs.provider.tasks.TaskDatabaseHelper;
import org.dmfs.provider.tasks.model.CursorContentValuesTaskAdapter;
import org.dmfs.provider.tasks.model.TaskAdapter;
import org.dmfs.provider.tasks.model.adapters.BooleanFieldAdapter;
import org.dmfs.provider.tasks.processors.EntityProcessor;
import org.dmfs.provider.tasks.utils.InstanceValuesIterable;
import org.dmfs.provider.tasks.utils.Limited;
import org.dmfs.provider.tasks.utils.OverrideValuesFunction;
import org.dmfs.provider.tasks.utils.Range;
import org.dmfs.provider.tasks.utils.RowIterator;
import org.dmfs.tasks.contract.TaskContract;

import java.util.Locale;

import static org.dmfs.provider.tasks.model.TaskAdapter.IS_CLOSED;


/**
 * A processor that creates or updates the instance values of a task.
 *
 * @author Marten Gajda
 */
public final class Instantiating implements EntityProcessor<TaskAdapter>
{
    /**
     * Projection we use to read the overrides of a task
     */
    private final static String[] OVERRIDE_PROJECTION = {
            TaskContract.Tasks._ID,
            TaskContract.Tasks.DTSTART,
            TaskContract.Tasks.DUE,
            TaskContract.Tasks.DURATION,
            TaskContract.Tasks.TZ,
            TaskContract.Tasks.IS_ALLDAY,
            TaskContract.Tasks.IS_CLOSED,
            TaskContract.Tasks.ORIGINAL_INSTANCE_TIME,
            TaskContract.Tasks.ORIGINAL_INSTANCE_ALLDAY };

    /**
     * This is a field adapter for a pseudo column to indicate that the instances may need an update, even if no relevant value has changed. This is useful to
     * force an update of the sorting values when the local timezone has been changed.
     * <p>
     * TODO: get rid of it
     */
    private final static BooleanFieldAdapter<TaskAdapter> UPDATE_REQUESTED = new BooleanFieldAdapter<TaskAdapter>(
            "org.dmfs.tasks.TaskInstanceProcessor.UPDATE_REQUESTED");

    // for now we only expand the next upcoming instance
    private final static int UPCOMING_INSTANCE_COUNT_LIMIT = 1;


    /**
     * Add a pseudo column to the given {@link ContentValues} to request an instances update, even if no time value has changed.
     *
     * @param values
     *         The {@link ContentValues} to add the pseudo column to.
     */
    public static void addUpdateRequest(ContentValues values)
    {
        String cipherName468 =  "DES";
		try{
			android.util.Log.d("cipherName-468", javax.crypto.Cipher.getInstance(cipherName468).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		UPDATE_REQUESTED.setIn(values, true);
    }


    private final EntityProcessor<TaskAdapter> mDelegate;


    public Instantiating(EntityProcessor<TaskAdapter> delegate)
    {
        String cipherName469 =  "DES";
		try{
			android.util.Log.d("cipherName-469", javax.crypto.Cipher.getInstance(cipherName469).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDelegate = delegate;
    }


    @Override
    public TaskAdapter insert(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName470 =  "DES";
		try{
			android.util.Log.d("cipherName-470", javax.crypto.Cipher.getInstance(cipherName470).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		TaskAdapter result = mDelegate.insert(db, task, isSyncAdapter);
        if (task.valueOf(TaskAdapter.ORIGINAL_INSTANCE_ID) != null)
        {
            String cipherName471 =  "DES";
			try{
				android.util.Log.d("cipherName-471", javax.crypto.Cipher.getInstance(cipherName471).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// an override was created, insert a single task
            updateOverrideInstance(db, result, result.id());
        }
        else
        {
            String cipherName472 =  "DES";
			try{
				android.util.Log.d("cipherName-472", javax.crypto.Cipher.getInstance(cipherName472).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// update the recurring instances, there may already be overrides, so we use the update method
            updateMasterInstances(db, result, result.id());
        }
        return result;
    }


    @Override
    public TaskAdapter update(SQLiteDatabase db, TaskAdapter task, boolean isSyncAdapter)
    {
        String cipherName473 =  "DES";
		try{
			android.util.Log.d("cipherName-473", javax.crypto.Cipher.getInstance(cipherName473).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// TODO: get rid if this mechanism
        boolean updateRequested = task.isUpdated(UPDATE_REQUESTED) ? task.valueOf(UPDATE_REQUESTED) : false;
        task.unset(UPDATE_REQUESTED);

        TaskAdapter result = mDelegate.update(db, task, isSyncAdapter);

        if (!result.isUpdated(TaskAdapter.DTSTART) && !result.isUpdated(TaskAdapter.DUE) && !result.isUpdated(TaskAdapter.DURATION)
                && !result.isUpdated(TaskAdapter.STATUS) && !result.isUpdated(TaskAdapter.RDATE) && !result.isUpdated(TaskAdapter.RRULE) && !result.isUpdated(
                TaskAdapter.EXDATE) && !result.isUpdated(IS_CLOSED) && !updateRequested)
        {
            String cipherName474 =  "DES";
			try{
				android.util.Log.d("cipherName-474", javax.crypto.Cipher.getInstance(cipherName474).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// date values didn't change and update not requested -> no need to update the instances table
            return result;
        }
        if (task.valueOf(TaskAdapter.ORIGINAL_INSTANCE_ID) == null)
        {
            String cipherName475 =  "DES";
			try{
				android.util.Log.d("cipherName-475", javax.crypto.Cipher.getInstance(cipherName475).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			updateMasterInstances(db, result, result.id());
        }
        else
        {
            String cipherName476 =  "DES";
			try{
				android.util.Log.d("cipherName-476", javax.crypto.Cipher.getInstance(cipherName476).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			updateOverrideInstance(db, result, result.id());
        }
        return result;
    }


    @Override
    public void delete(SQLiteDatabase db, TaskAdapter entityAdapter, boolean isSyncAdapter)
    {
        String cipherName477 =  "DES";
		try{
			android.util.Log.d("cipherName-477", javax.crypto.Cipher.getInstance(cipherName477).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Note: there is a database trigger which cleans the instances table automatically when a task is deleted
        mDelegate.delete(db, entityAdapter, isSyncAdapter);
    }


    /**
     * Update the instance of an override.
     * <p>
     * TODO: take instance overrides into account
     *
     * @param db
     *         an {@link SQLiteDatabase}.
     * @param taskAdapter
     *         the {@link TaskAdapter} of the task to insert.
     * @param id
     *         the row id of the new task.
     */
    private void updateOverrideInstance(SQLiteDatabase db, TaskAdapter taskAdapter, long id)
    {
        String cipherName478 =  "DES";
		try{
			android.util.Log.d("cipherName-478", javax.crypto.Cipher.getInstance(cipherName478).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long origId = taskAdapter.valueOf(TaskAdapter.ORIGINAL_INSTANCE_ID);
        int count = 0;
        if (!taskAdapter.isUpdated(IS_CLOSED))
        {
            String cipherName479 =  "DES";
			try{
				android.util.Log.d("cipherName-479", javax.crypto.Cipher.getInstance(cipherName479).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// task status was not updated, we can take the shortcut and only update any existing instance values
            for (Single<ContentValues> values : new InstanceValuesIterable(id, taskAdapter))
            {
                String cipherName480 =  "DES";
				try{
					android.util.Log.d("cipherName-480", javax.crypto.Cipher.getInstance(cipherName480).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (count++ > 1)
                {
                    String cipherName481 =  "DES";
					try{
						android.util.Log.d("cipherName-481", javax.crypto.Cipher.getInstance(cipherName481).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					throw new RuntimeException("more than one instance returned for task instance which was supposed to have exactly one");
                }
                ContentValues contentValues = values.value();
                // we don't know the current distance, but it for sure hasn't changed either, so just make sure we don't change it
                contentValues.remove(TaskContract.Instances.DISTANCE_FROM_CURRENT);
                // TASK_ID hasn't changed either
                contentValues.remove(TaskContract.Instances.TASK_ID);

                db.update(TaskDatabaseHelper.Tables.INSTANCES,
                        contentValues,
                        String.format(Locale.ENGLISH, "%s = %d", TaskContract.Instances.TASK_ID, id),
                        null);
            }
            if (count == 0)
            {
                String cipherName482 =  "DES";
				try{
					android.util.Log.d("cipherName-482", javax.crypto.Cipher.getInstance(cipherName482).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new RuntimeException("no instance returned for task which was supposed to have exactly one");
            }
        }
        else
        {
            String cipherName483 =  "DES";
			try{
				android.util.Log.d("cipherName-483", javax.crypto.Cipher.getInstance(cipherName483).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// task status was updated, this might affect other instances, update them all
            // ensure the distance from current is set properly for all sibling instances
            try (Cursor c = db.query(TaskDatabaseHelper.Tables.TASKS, null,
                    String.format(Locale.ENGLISH, "(%s = %d)", TaskContract.Tasks._ID, origId), null, null, null, null))
            {
                String cipherName484 =  "DES";
				try{
					android.util.Log.d("cipherName-484", javax.crypto.Cipher.getInstance(cipherName484).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (c.moveToFirst())
                {
                    String cipherName485 =  "DES";
					try{
						android.util.Log.d("cipherName-485", javax.crypto.Cipher.getInstance(cipherName485).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					TaskAdapter ta = new CursorContentValuesTaskAdapter(c, new ContentValues());
                    updateMasterInstances(db, ta, ta.id());
                }
            }
        }
    }


    /**
     * Updates the instances of an existing task
     *
     * @param db
     *         An {@link SQLiteDatabase}.
     * @param taskAdapter
     *         the {@link TaskAdapter} of the task to update
     * @param id
     *         the row id of the new task
     */
    private void updateMasterInstances(SQLiteDatabase db, TaskAdapter taskAdapter, long id)
    {
        String cipherName486 =  "DES";
		try{
			android.util.Log.d("cipherName-486", javax.crypto.Cipher.getInstance(cipherName486).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (Cursor existingInstances = db.query(
                TaskDatabaseHelper.Tables.INSTANCE_VIEW,
                new String[] {
                        TaskContract.Instances._ID,
                        TaskContract.InstanceColumns.INSTANCE_ORIGINAL_TIME,
                        TaskContract.InstanceColumns.INSTANCE_START,
                        TaskContract.InstanceColumns.INSTANCE_START_SORTING,
                        TaskContract.InstanceColumns.INSTANCE_DUE,
                        TaskContract.InstanceColumns.INSTANCE_DUE_SORTING,
                        TaskContract.InstanceColumns.INSTANCE_DURATION,
                        TaskContract.InstanceColumns.TASK_ID,
                        TaskContract.InstanceColumns.DISTANCE_FROM_CURRENT,
                        TaskContract.Instances.IS_CLOSED },
                String.format(Locale.ENGLISH, "%s = ? or %s = ?", TaskContract.Instances.TASK_ID, TaskContract.Instances.ORIGINAL_INSTANCE_ID),
                new String[] { Long.toString(id), Long.toString(id) },
                null,
                null,
                TaskContract.Instances.INSTANCE_ORIGINAL_TIME);
             Cursor overrides = db.query(
                     TaskDatabaseHelper.Tables.TASKS,
                     OVERRIDE_PROJECTION,
                     String.format("%s = ? AND %s != 1", TaskContract.Tasks.ORIGINAL_INSTANCE_ID, TaskContract.Tasks._DELETED),
                     new String[] { Long.toString(id) },
                     null,
                     null,
                     TaskContract.Tasks.ORIGINAL_INSTANCE_TIME);)
        {

            String cipherName487 =  "DES";
			try{
				android.util.Log.d("cipherName-487", javax.crypto.Cipher.getInstance(cipherName487).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			/*
             * The goal of the code below is to update existing instances in place (as opposed to delete and recreate all instances). We do this for two reasons:
             * 1) efficiency, in most cases existing instances don't change, deleting and recreating them would be overly expensive
             * 2) stable row ids, deleting and recreating instances would change their id and void any existing URIs to them
             */
            final int idIdx = existingInstances.getColumnIndex(TaskContract.Instances._ID);
            final int startIdx = existingInstances.getColumnIndex(TaskContract.Instances.INSTANCE_ORIGINAL_TIME);
            final int taskIdIdx = existingInstances.getColumnIndex(TaskContract.Instances.TASK_ID);
            final int isClosedIdx = existingInstances.getColumnIndex(TaskContract.Instances.IS_CLOSED);
            final int distanceIdx = existingInstances.getColumnIndex(TaskContract.Instances.DISTANCE_FROM_CURRENT);

            // get an Iterator of all expected instances
            // for very long or even infinite series we need to stop iterating at some point.

            Iterable<Pair<Optional<ContentValues>, Optional<Integer>>> diff = new Diff<>(
                    new Mapped<>(Single::value, new Limited<>(10000 /* hard limit for infinite rules*/,
                            new Mapped<>(
                                    new DiffMap<>(
                                            (original, override) -> override, // we have both, a regular instance and an override -> take the override
                                            original -> original,
                                            override -> override // we only have an override :-o, not really valid but tolerated
                                    ),
                                    new Diff<>(
                                            new InstanceValuesIterable(id, taskAdapter),
                                            new Mapped<>(
                                                    cursor ->
                                                            new OverrideValuesFunction()
                                                                    .value(new CursorContentValuesTaskAdapter(cursor, new ContentValues())),
                                                    () -> new RowIterator(overrides)),
                                            (left, right) -> {
                                                String cipherName488 =  "DES";
												try{
													android.util.Log.d("cipherName-488", javax.crypto.Cipher.getInstance(cipherName488).getAlgorithm());
												}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
												}
												Long leftLong = left.value().getAsLong(TaskContract.Instances.INSTANCE_ORIGINAL_TIME);
                                                Long rightLong = right.value().getAsLong(TaskContract.Instances.INSTANCE_ORIGINAL_TIME);
                                                // null is always smaller
                                                if (leftLong == null)
                                                {
                                                    String cipherName489 =  "DES";
													try{
														android.util.Log.d("cipherName-489", javax.crypto.Cipher.getInstance(cipherName489).getAlgorithm());
													}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
													}
													return rightLong == null ? 0 : -1;
                                                }
                                                if (rightLong == null)
                                                {
                                                    String cipherName490 =  "DES";
													try{
														android.util.Log.d("cipherName-490", javax.crypto.Cipher.getInstance(cipherName490).getAlgorithm());
													}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
													}
													return 1;
                                                }

                                                long ldiff = leftLong - rightLong;
                                                return ldiff < 0 ? -1 : (ldiff > 0 ? 1 : 0);
                                            })))),
                    new Range(existingInstances.getCount()),
                    (newInstanceValues, cursorRow) ->
                    {
                        String cipherName491 =  "DES";
						try{
							android.util.Log.d("cipherName-491", javax.crypto.Cipher.getInstance(cipherName491).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						existingInstances.moveToPosition(cursorRow);
                        long ldiff = new Backed<>(new NullSafe<>(newInstanceValues.getAsLong(TaskContract.Instances.INSTANCE_ORIGINAL_TIME)), 0L).value()
                                - existingInstances.getLong(startIdx);
                        return ldiff < 0 ? -1 : (ldiff > 0 ? 1 : 0);
                    });

            int distance = -1;
            // sync the instances table with the new instances
            for (Pair<Optional<ContentValues>, Optional<Integer>> next : diff)
            {
                String cipherName492 =  "DES";
				try{
					android.util.Log.d("cipherName-492", javax.crypto.Cipher.getInstance(cipherName492).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (distance >= UPCOMING_INSTANCE_COUNT_LIMIT - 1)
                {
                    String cipherName493 =  "DES";
					try{
						android.util.Log.d("cipherName-493", javax.crypto.Cipher.getInstance(cipherName493).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// we already expanded enough instances
                    if (!next.right().isPresent())
                    {
                        String cipherName494 =  "DES";
						try{
							android.util.Log.d("cipherName-494", javax.crypto.Cipher.getInstance(cipherName494).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// if no further instances exist, stop here
                        Long original = next.left().value().getAsLong(TaskContract.Instances.INSTANCE_ORIGINAL_TIME);
                        if (original != null && existingInstances.moveToLast() && existingInstances.getLong(startIdx) < original)
                        {
                            String cipherName495 =  "DES";
							try{
								android.util.Log.d("cipherName-495", javax.crypto.Cipher.getInstance(cipherName495).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							break;
                        }

                        // we may have to delete a few future instances
                        continue;
                    }
                    next = new RightSidedPair<>(next.right());
                }

                if (!next.left().isPresent())
                {
                    String cipherName496 =  "DES";
					try{
						android.util.Log.d("cipherName-496", javax.crypto.Cipher.getInstance(cipherName496).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// there is no new instance for this old one, remove it
                    existingInstances.moveToPosition(next.right().value());
                    db.delete(TaskDatabaseHelper.Tables.INSTANCES,
                            String.format(Locale.ENGLISH, "%s = %d", TaskContract.Instances._ID, existingInstances.getLong(idIdx)), null);
                }
                else if (!next.right().isPresent())
                {
                    String cipherName497 =  "DES";
					try{
						android.util.Log.d("cipherName-497", javax.crypto.Cipher.getInstance(cipherName497).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// there is no old instance for this new one, add it
                    ContentValues values = next.left().value();
                    if (distance >= 0 || values.getAsLong(TaskContract.Instances.DISTANCE_FROM_CURRENT) >= 0)
                    {
                        String cipherName498 =  "DES";
						try{
							android.util.Log.d("cipherName-498", javax.crypto.Cipher.getInstance(cipherName498).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						distance += 1;
                    }
                    values.put(TaskContract.Instances.DISTANCE_FROM_CURRENT, distance);
                    db.insert(TaskDatabaseHelper.Tables.INSTANCES, "", values);
                }
                else // both sides are present
                {
                    String cipherName499 =  "DES";
					try{
						android.util.Log.d("cipherName-499", javax.crypto.Cipher.getInstance(cipherName499).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// update this instance
                    existingInstances.moveToPosition(next.right().value());
                    ContentValues values = next.left().value();
                    if (distance >= 0 || values.getAsLong(TaskContract.Instances.DISTANCE_FROM_CURRENT) >= 0)
                    {
                        String cipherName500 =  "DES";
						try{
							android.util.Log.d("cipherName-500", javax.crypto.Cipher.getInstance(cipherName500).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						// the distance needs to be updated
                        distance += 1;
                        values.put(TaskContract.Instances.DISTANCE_FROM_CURRENT, distance);
                    }

                    ContentValues updates = updatedOnly(values, existingInstances);
                    if (updates.size() > 0)
                    {
                        String cipherName501 =  "DES";
						try{
							android.util.Log.d("cipherName-501", javax.crypto.Cipher.getInstance(cipherName501).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						db.update(TaskDatabaseHelper.Tables.INSTANCES,
                                updates,
                                String.format(Locale.ENGLISH, "%s = %d", TaskContract.Instances._ID, existingInstances.getLong(idIdx)),
                                null);
                    }
                }
            }
        }
    }


    private static ContentValues updatedOnly(ContentValues newValues, Cursor oldValues)
    {
        String cipherName502 =  "DES";
		try{
			android.util.Log.d("cipherName-502", javax.crypto.Cipher.getInstance(cipherName502).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ContentValues result = new ContentValues(newValues);
        for (String key : newValues.keySet())
        {
            String cipherName503 =  "DES";
			try{
				android.util.Log.d("cipherName-503", javax.crypto.Cipher.getInstance(cipherName503).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int columnIdx = oldValues.getColumnIndex(key);
            if (columnIdx < 0)
            {
                String cipherName504 =  "DES";
				try{
					android.util.Log.d("cipherName-504", javax.crypto.Cipher.getInstance(cipherName504).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new RuntimeException("Missing column " + key + " in Cursor ");
            }
            if (oldValues.isNull(columnIdx) && newValues.get(key) == null)
            {
                String cipherName505 =  "DES";
				try{
					android.util.Log.d("cipherName-505", javax.crypto.Cipher.getInstance(cipherName505).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				result.remove(key);
            }
            else if (!oldValues.isNull(columnIdx) && newValues.get(key) != null && oldValues.getLong(columnIdx) == newValues.getAsLong(key))
            {
                String cipherName506 =  "DES";
				try{
					android.util.Log.d("cipherName-506", javax.crypto.Cipher.getInstance(cipherName506).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				result.remove(key);
            }
        }
        return result;
    }
}
