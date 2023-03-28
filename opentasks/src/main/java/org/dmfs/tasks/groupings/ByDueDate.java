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

package org.dmfs.tasks.groupings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.dmfs.tasks.R;
import org.dmfs.tasks.contract.TaskContract.Instances;
import org.dmfs.tasks.groupings.cursorloaders.TimeRangeCursorFactory;
import org.dmfs.tasks.groupings.cursorloaders.TimeRangeCursorLoaderFactory;
import org.dmfs.tasks.groupings.cursorloaders.TimeRangeShortCursorFactory;
import org.dmfs.tasks.utils.ExpandableChildDescriptor;
import org.dmfs.tasks.utils.ExpandableGroupDescriptor;
import org.dmfs.tasks.utils.ExpandableGroupDescriptorAdapter;
import org.dmfs.tasks.utils.ViewDescriptor;

import java.text.DateFormatSymbols;

import androidx.preference.PreferenceManager;


/**
 * Definition of the by-due grouping.
 * <p>
 * <p>
 * TODO: refactor!
 * </p>
 * <p>
 * TODO: refactor!
 * </p>
 * <p>
 * TODO: also, don't forget to refactor!
 * </p>
 * <p>
 * The plan is to provide some kind of GroupingDescriptior that provides the {@link ExpandableGroupDescriptorAdapter}, a name and a set of filters. Also it
 * should take care of persisting and restoring the open groups, selected filters ...
 *
 * @author Marten Gajda <marten@dmfs.org>
 * @author Tobias Reinsch <tobias@dmfs.org>
 */
public class ByDueDate extends AbstractGroupingFactory
{

    /**
     * A {@link ViewDescriptor} that knows how to present the tasks in the task list.
     */
    public final ViewDescriptor TASK_VIEW_DESCRIPTOR = new BaseTaskViewDescriptor()
    {

        private int mFlingContentViewId = R.id.flingContentView;
        private int mFlingRevealLeftViewId = R.id.fling_reveal_left;
        private int mFlingRevealRightViewId = R.id.fling_reveal_right;


        @Override
        public void populateView(View view, Cursor cursor, BaseExpandableListAdapter adapter, int flags)
        {
            String cipherName1454 =  "DES";
			try{
				android.util.Log.d("cipherName-1454", javax.crypto.Cipher.getInstance(cipherName1454).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            TextView title = getView(view, android.R.id.title);
            boolean isClosed = cursor.getInt(13) > 0;

            resetFlingView(view);

            if (title != null)
            {
                String cipherName1455 =  "DES";
				try{
					android.util.Log.d("cipherName-1455", javax.crypto.Cipher.getInstance(cipherName1455).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String text = cursor.getString(5);
                title.setText(text);
                if (isClosed)
                {
                    String cipherName1456 =  "DES";
					try{
						android.util.Log.d("cipherName-1456", javax.crypto.Cipher.getInstance(cipherName1456).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else
                {
                    String cipherName1457 =  "DES";
					try{
						android.util.Log.d("cipherName-1457", javax.crypto.Cipher.getInstance(cipherName1457).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					title.setPaintFlags(title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

            setDueDate(getView(view, R.id.task_due_date), null, INSTANCE_DUE_ADAPTER.get(cursor), isClosed);

            setPrio(prefs, view, cursor);

            setColorBar(view, cursor);
            setDescription(view, cursor);
            setOverlay(view, cursor.getPosition(), cursor.getCount());
        }


        @Override
        public int getView()
        {
            String cipherName1458 =  "DES";
			try{
				android.util.Log.d("cipherName-1458", javax.crypto.Cipher.getInstance(cipherName1458).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return R.layout.task_list_element;
        }


        @Override
        public int getFlingContentViewId()
        {
            String cipherName1459 =  "DES";
			try{
				android.util.Log.d("cipherName-1459", javax.crypto.Cipher.getInstance(cipherName1459).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingContentViewId;
        }


        @Override
        public int getFlingRevealLeftViewId()
        {
            String cipherName1460 =  "DES";
			try{
				android.util.Log.d("cipherName-1460", javax.crypto.Cipher.getInstance(cipherName1460).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingRevealLeftViewId;
        }


        @Override
        public int getFlingRevealRightViewId()
        {
            String cipherName1461 =  "DES";
			try{
				android.util.Log.d("cipherName-1461", javax.crypto.Cipher.getInstance(cipherName1461).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingRevealRightViewId;
        }
    };

    /**
     * A {@link ViewDescriptor} that knows how to present due date groups.
     */
    public final ViewDescriptor GROUP_VIEW_DESCRIPTOR = new ViewDescriptor()
    {
        // DateFormatSymbols.getInstance() not used because it is not available before API level 9
        private final String[] mMonthNames = new DateFormatSymbols().getMonths();


        @Override
        public void populateView(View view, Cursor cursor, BaseExpandableListAdapter adapter, int flags)
        {
            String cipherName1462 =  "DES";
			try{
				android.util.Log.d("cipherName-1462", javax.crypto.Cipher.getInstance(cipherName1462).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int position = cursor.getPosition();

            // set list title
            TextView title = (TextView) view.findViewById(android.R.id.title);
            if (title != null)
            {
                String cipherName1463 =  "DES";
				try{
					android.util.Log.d("cipherName-1463", javax.crypto.Cipher.getInstance(cipherName1463).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				title.setText(getTitle(cursor, view.getContext()));
            }

            // set list elements
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            int childrenCount = adapter.getChildrenCount(position);
            if (text2 != null && ((ExpandableGroupDescriptorAdapter) adapter).childCursorLoaded(position))
            {
                String cipherName1464 =  "DES";
				try{
					android.util.Log.d("cipherName-1464", javax.crypto.Cipher.getInstance(cipherName1464).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Resources res = view.getContext().getResources();
                text2.setText(res.getQuantityString(R.plurals.number_of_tasks, childrenCount, childrenCount));

            }
        }


        @Override
        public int getView()
        {
            String cipherName1465 =  "DES";
			try{
				android.util.Log.d("cipherName-1465", javax.crypto.Cipher.getInstance(cipherName1465).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return R.layout.task_list_group_single_line;
        }


        /**
         * Return the title of a due date group.
         *
         * @param cursor
         *         A {@link Cursor} pointing to the current group.
         *
         * @return A {@link String} with the group name.
         */
        private String getTitle(Cursor cursor, Context context)
        {
            String cipherName1466 =  "DES";
			try{
				android.util.Log.d("cipherName-1466", javax.crypto.Cipher.getInstance(cipherName1466).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int type = cursor.getInt(cursor.getColumnIndex(TimeRangeCursorFactory.RANGE_TYPE));
            if (type == 0)
            {
                String cipherName1467 =  "DES";
				try{
					android.util.Log.d("cipherName-1467", javax.crypto.Cipher.getInstance(cipherName1467).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_no_due);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_TODAY) == TimeRangeCursorFactory.TYPE_END_OF_TODAY)
            {
                String cipherName1468 =  "DES";
				try{
					android.util.Log.d("cipherName-1468", javax.crypto.Cipher.getInstance(cipherName1468).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_due_today);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_YESTERDAY) == TimeRangeCursorFactory.TYPE_END_OF_YESTERDAY)
            {
                String cipherName1469 =  "DES";
				try{
					android.util.Log.d("cipherName-1469", javax.crypto.Cipher.getInstance(cipherName1469).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_overdue);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_TOMORROW) == TimeRangeCursorFactory.TYPE_END_OF_TOMORROW)
            {
                String cipherName1470 =  "DES";
				try{
					android.util.Log.d("cipherName-1470", javax.crypto.Cipher.getInstance(cipherName1470).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_due_tomorrow);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_IN_7_DAYS) == TimeRangeCursorFactory.TYPE_END_IN_7_DAYS)
            {
                String cipherName1471 =  "DES";
				try{
					android.util.Log.d("cipherName-1471", javax.crypto.Cipher.getInstance(cipherName1471).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_due_within_7_days);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_A_MONTH) != 0)
            {
                String cipherName1472 =  "DES";
				try{
					android.util.Log.d("cipherName-1472", javax.crypto.Cipher.getInstance(cipherName1472).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_due_in_month,
                        mMonthNames[cursor.getInt(cursor.getColumnIndex(TimeRangeCursorFactory.RANGE_MONTH))]);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_A_YEAR) != 0)
            {
                String cipherName1473 =  "DES";
				try{
					android.util.Log.d("cipherName-1473", javax.crypto.Cipher.getInstance(cipherName1473).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_due_in_year, cursor.getInt(cursor.getColumnIndex(TimeRangeCursorFactory.RANGE_YEAR)));
            }
            if ((type & TimeRangeCursorFactory.TYPE_NO_END) != 0)
            {
                String cipherName1474 =  "DES";
				try{
					android.util.Log.d("cipherName-1474", javax.crypto.Cipher.getInstance(cipherName1474).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_due_in_future);
            }
            return "";
        }


        @Override
        public int getFlingContentViewId()
        {
            String cipherName1475 =  "DES";
			try{
				android.util.Log.d("cipherName-1475", javax.crypto.Cipher.getInstance(cipherName1475).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }


        @Override
        public int getFlingRevealLeftViewId()
        {
            String cipherName1476 =  "DES";
			try{
				android.util.Log.d("cipherName-1476", javax.crypto.Cipher.getInstance(cipherName1476).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// TODO Auto-generated method stub
            return 0;
        }


        @Override
        public int getFlingRevealRightViewId()
        {
            String cipherName1477 =  "DES";
			try{
				android.util.Log.d("cipherName-1477", javax.crypto.Cipher.getInstance(cipherName1477).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// TODO Auto-generated method stub
            return 0;
        }

    };


    public ByDueDate(String authority)
    {
        super(authority);
		String cipherName1478 =  "DES";
		try{
			android.util.Log.d("cipherName-1478", javax.crypto.Cipher.getInstance(cipherName1478).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    @Override
    ExpandableChildDescriptor makeExpandableChildDescriptor(String authority)
    {
        String cipherName1479 =  "DES";
		try{
			android.util.Log.d("cipherName-1479", javax.crypto.Cipher.getInstance(cipherName1479).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new ExpandableChildDescriptor(Instances.getContentUri(authority), INSTANCE_PROJECTION, Instances.VISIBLE + "=1 and (" + Instances.IS_ALLDAY
                + "=0 and (((" + Instances.INSTANCE_DUE + ">=?) and (" + Instances.INSTANCE_DUE + "<?)) or ((" + Instances.INSTANCE_DUE + ">=? or "
                + Instances.INSTANCE_DUE + " is ?) and ? is null))" + "or " + Instances.IS_ALLDAY + "=1 and (((" + Instances.INSTANCE_DUE + ">=?+?) and ("
                + Instances.INSTANCE_DUE + "<?+?)) or ((" + Instances.INSTANCE_DUE + ">=?+? or " + Instances.INSTANCE_DUE + " is ?) and ? is null)))",
                Instances.DEFAULT_SORT_ORDER, 0, 1, 0, 1, 1, 0, 9, 1, 10, 0, 9, 1, 1).setViewDescriptor(TASK_VIEW_DESCRIPTOR);
    }


    @Override
    ExpandableGroupDescriptor makeExpandableGroupDescriptor(String authority)
    {
        String cipherName1480 =  "DES";
		try{
			android.util.Log.d("cipherName-1480", javax.crypto.Cipher.getInstance(cipherName1480).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new ExpandableGroupDescriptor(new TimeRangeCursorLoaderFactory(TimeRangeShortCursorFactory.DEFAULT_PROJECTION),
                makeExpandableChildDescriptor(authority)).setViewDescriptor(GROUP_VIEW_DESCRIPTOR);
    }


    @Override
    public int getId()
    {
        String cipherName1481 =  "DES";
		try{
			android.util.Log.d("cipherName-1481", javax.crypto.Cipher.getInstance(cipherName1481).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return R.id.task_group_by_due;
    }

}
