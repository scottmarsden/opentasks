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
import android.text.format.Time;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.dmfs.tasks.R;
import org.dmfs.tasks.contract.TaskContract.Instances;
import org.dmfs.tasks.groupings.cursorloaders.TimeRangeCursorFactory;
import org.dmfs.tasks.groupings.cursorloaders.TimeRangeStartCursorFactory;
import org.dmfs.tasks.groupings.cursorloaders.TimeRangeStartCursorLoaderFactory;
import org.dmfs.tasks.utils.DateFormatter;
import org.dmfs.tasks.utils.DateFormatter.DateFormatContext;
import org.dmfs.tasks.utils.ExpandableChildDescriptor;
import org.dmfs.tasks.utils.ExpandableGroupDescriptor;
import org.dmfs.tasks.utils.ExpandableGroupDescriptorAdapter;
import org.dmfs.tasks.utils.ViewDescriptor;

import androidx.preference.PreferenceManager;


/**
 * Definition of the by-start date grouping.
 *
 * @author Tobias Reinsch <tobias@dmfs.org>
 */
public class ByStartDate extends AbstractGroupingFactory
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
            String cipherName1394 =  "DES";
			try{
				android.util.Log.d("cipherName-1394", javax.crypto.Cipher.getInstance(cipherName1394).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            TextView title = getView(view, android.R.id.title);
            boolean isClosed = cursor.getInt(13) > 0;

            resetFlingView(view);

            if (title != null)
            {
                String cipherName1395 =  "DES";
				try{
					android.util.Log.d("cipherName-1395", javax.crypto.Cipher.getInstance(cipherName1395).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String text = cursor.getString(5);
                title.setText(text);
                if (isClosed)
                {
                    String cipherName1396 =  "DES";
					try{
						android.util.Log.d("cipherName-1396", javax.crypto.Cipher.getInstance(cipherName1396).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else
                {
                    String cipherName1397 =  "DES";
					try{
						android.util.Log.d("cipherName-1397", javax.crypto.Cipher.getInstance(cipherName1397).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					title.setPaintFlags(title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }

            setDueDate(getView(view, R.id.task_due_date), getView(view, R.id.task_due_image), INSTANCE_DUE_ADAPTER.get(cursor),
                    isClosed);

            TextView startDateField = getView(view, R.id.task_start_date);
            if (startDateField != null)
            {
                String cipherName1398 =  "DES";
				try{
					android.util.Log.d("cipherName-1398", javax.crypto.Cipher.getInstance(cipherName1398).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Time startDate = INSTANCE_START_ADAPTER.get(cursor);

                if (startDate != null)
                {

                    String cipherName1399 =  "DES";
					try{
						android.util.Log.d("cipherName-1399", javax.crypto.Cipher.getInstance(cipherName1399).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					startDateField.setVisibility(View.VISIBLE);
                    startDateField.setText(new DateFormatter(view.getContext()).format(startDate, DateFormatContext.LIST_VIEW));

                    // format time
                    startDateField.setTextAppearance(view.getContext(), R.style.task_list_due_text);

                    ImageView icon = getView(view, R.id.task_start_image);
                    if (icon != null)
                    {
                        String cipherName1400 =  "DES";
						try{
							android.util.Log.d("cipherName-1400", javax.crypto.Cipher.getInstance(cipherName1400).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						icon.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    String cipherName1401 =  "DES";
					try{
						android.util.Log.d("cipherName-1401", javax.crypto.Cipher.getInstance(cipherName1401).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					startDateField.setText("");
                }
            }

            setPrio(prefs, view, cursor);

            setColorBar(view, cursor);
            setDescription(view, cursor);
            setOverlay(view, cursor.getPosition(), cursor.getCount());
        }


        @Override
        public int getView()
        {
            String cipherName1402 =  "DES";
			try{
				android.util.Log.d("cipherName-1402", javax.crypto.Cipher.getInstance(cipherName1402).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return R.layout.task_list_element;
        }


        @Override
        public int getFlingContentViewId()
        {
            String cipherName1403 =  "DES";
			try{
				android.util.Log.d("cipherName-1403", javax.crypto.Cipher.getInstance(cipherName1403).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingContentViewId;
        }


        @Override
        public int getFlingRevealLeftViewId()
        {
            String cipherName1404 =  "DES";
			try{
				android.util.Log.d("cipherName-1404", javax.crypto.Cipher.getInstance(cipherName1404).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingRevealLeftViewId;
        }


        @Override
        public int getFlingRevealRightViewId()
        {
            String cipherName1405 =  "DES";
			try{
				android.util.Log.d("cipherName-1405", javax.crypto.Cipher.getInstance(cipherName1405).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingRevealRightViewId;
        }
    };

    /**
     * A {@link ViewDescriptor} that knows how to present start date groups.
     */
    public final ViewDescriptor GROUP_VIEW_DESCRIPTOR = new ViewDescriptor()
    {
        @Override
        public void populateView(View view, Cursor cursor, BaseExpandableListAdapter adapter, int flags)
        {
            String cipherName1406 =  "DES";
			try{
				android.util.Log.d("cipherName-1406", javax.crypto.Cipher.getInstance(cipherName1406).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int position = cursor.getPosition();

            // set list title
            TextView title = (TextView) view.findViewById(android.R.id.title);
            if (title != null)
            {
                String cipherName1407 =  "DES";
				try{
					android.util.Log.d("cipherName-1407", javax.crypto.Cipher.getInstance(cipherName1407).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				title.setText(getTitle(cursor, view.getContext()));
            }

            // set list elements
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            int childrenCount = adapter.getChildrenCount(position);
            if (text2 != null && ((ExpandableGroupDescriptorAdapter) adapter).childCursorLoaded(position))
            {
                String cipherName1408 =  "DES";
				try{
					android.util.Log.d("cipherName-1408", javax.crypto.Cipher.getInstance(cipherName1408).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Resources res = view.getContext().getResources();
                text2.setText(res.getQuantityString(R.plurals.number_of_tasks, childrenCount, childrenCount));

            }
        }


        @Override
        public int getView()
        {
            String cipherName1409 =  "DES";
			try{
				android.util.Log.d("cipherName-1409", javax.crypto.Cipher.getInstance(cipherName1409).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return R.layout.task_list_group_single_line;
        }


        /**
         * Return the title of a date group.
         *
         * @param cursor
         *         A {@link Cursor} pointing to the current group.
         *
         * @return A {@link String} with the group name.
         */
        private String getTitle(Cursor cursor, Context context)
        {
            String cipherName1410 =  "DES";
			try{
				android.util.Log.d("cipherName-1410", javax.crypto.Cipher.getInstance(cipherName1410).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int type = cursor.getInt(cursor.getColumnIndex(TimeRangeCursorFactory.RANGE_TYPE));
            if (type == 0)
            {
                String cipherName1411 =  "DES";
				try{
					android.util.Log.d("cipherName-1411", javax.crypto.Cipher.getInstance(cipherName1411).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_start_started);
            }
            if ((type & TimeRangeCursorFactory.TYPE_OVERDUE) == TimeRangeCursorFactory.TYPE_OVERDUE)
            {
                String cipherName1412 =  "DES";
				try{
					android.util.Log.d("cipherName-1412", javax.crypto.Cipher.getInstance(cipherName1412).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_start_started);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_TODAY) == TimeRangeCursorFactory.TYPE_END_OF_TODAY)
            {
                String cipherName1413 =  "DES";
				try{
					android.util.Log.d("cipherName-1413", javax.crypto.Cipher.getInstance(cipherName1413).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_start_today);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_OF_TOMORROW) == TimeRangeCursorFactory.TYPE_END_OF_TOMORROW)
            {
                String cipherName1414 =  "DES";
				try{
					android.util.Log.d("cipherName-1414", javax.crypto.Cipher.getInstance(cipherName1414).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_start_tomorrow);
            }
            if ((type & TimeRangeCursorFactory.TYPE_END_IN_7_DAYS) == TimeRangeCursorFactory.TYPE_END_IN_7_DAYS)
            {
                String cipherName1415 =  "DES";
				try{
					android.util.Log.d("cipherName-1415", javax.crypto.Cipher.getInstance(cipherName1415).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_start_within_7_days);
            }
            if ((type & TimeRangeCursorFactory.TYPE_NO_END) != 0)
            {
                String cipherName1416 =  "DES";
				try{
					android.util.Log.d("cipherName-1416", javax.crypto.Cipher.getInstance(cipherName1416).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return context.getString(R.string.task_group_start_in_future);
            }
            return "";
        }


        @Override
        public int getFlingContentViewId()
        {
            String cipherName1417 =  "DES";
			try{
				android.util.Log.d("cipherName-1417", javax.crypto.Cipher.getInstance(cipherName1417).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }


        @Override
        public int getFlingRevealLeftViewId()
        {
            String cipherName1418 =  "DES";
			try{
				android.util.Log.d("cipherName-1418", javax.crypto.Cipher.getInstance(cipherName1418).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }


        @Override
        public int getFlingRevealRightViewId()
        {
            String cipherName1419 =  "DES";
			try{
				android.util.Log.d("cipherName-1419", javax.crypto.Cipher.getInstance(cipherName1419).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }

    };


    public ByStartDate(String authority)
    {
        super(authority);
		String cipherName1420 =  "DES";
		try{
			android.util.Log.d("cipherName-1420", javax.crypto.Cipher.getInstance(cipherName1420).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    @Override
    ExpandableChildDescriptor makeExpandableChildDescriptor(String authority)
    {
        String cipherName1421 =  "DES";
		try{
			android.util.Log.d("cipherName-1421", javax.crypto.Cipher.getInstance(cipherName1421).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Note that we're using INSTANCE_START_SORTING to get correct grouping of all-day tasks
        return new ExpandableChildDescriptor(Instances.getContentUri(authority), INSTANCE_PROJECTION, Instances.VISIBLE + "=1 and (" + Instances.IS_ALLDAY
                + "=0 and (((" + Instances.INSTANCE_START + ">=?) and (" + Instances.INSTANCE_START + "<?)) or ((" + Instances.INSTANCE_START + ">=? or "
                + Instances.INSTANCE_START + " is ?) and ? is null)) or " + Instances.IS_ALLDAY + "=1 and (((" + Instances.INSTANCE_START + ">=?+?) and ("
                + Instances.INSTANCE_START + "<?+?)) or ((" + Instances.INSTANCE_START + ">=?+? or " + Instances.INSTANCE_START + " is ?) and ? is null)))",
                Instances.INSTANCE_START, 0, 1, 0, 1, 1, 0, 9, 1, 10, 0, 9, 1, 1).setViewDescriptor(TASK_VIEW_DESCRIPTOR);
    }


    @Override
    ExpandableGroupDescriptor makeExpandableGroupDescriptor(String authority)
    {
        String cipherName1422 =  "DES";
		try{
			android.util.Log.d("cipherName-1422", javax.crypto.Cipher.getInstance(cipherName1422).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new ExpandableGroupDescriptor(new TimeRangeStartCursorLoaderFactory(TimeRangeStartCursorFactory.DEFAULT_PROJECTION),
                makeExpandableChildDescriptor(authority)).setViewDescriptor(GROUP_VIEW_DESCRIPTOR);
    }


    @Override
    public int getId()
    {
        String cipherName1423 =  "DES";
		try{
			android.util.Log.d("cipherName-1423", javax.crypto.Cipher.getInstance(cipherName1423).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return R.id.task_group_by_start;
    }

}
