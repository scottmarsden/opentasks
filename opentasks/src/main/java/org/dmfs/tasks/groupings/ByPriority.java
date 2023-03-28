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
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.dmfs.provider.tasks.AuthorityUtil;
import org.dmfs.tasks.QuickAddDialogFragment;
import org.dmfs.tasks.R;
import org.dmfs.tasks.contract.TaskContract.Instances;
import org.dmfs.tasks.contract.TaskContract.Tasks;
import org.dmfs.tasks.groupings.cursorloaders.PriorityCursorFactory;
import org.dmfs.tasks.groupings.cursorloaders.PriorityCursorLoaderFactory;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.TaskFieldAdapters;
import org.dmfs.tasks.utils.ExpandableChildDescriptor;
import org.dmfs.tasks.utils.ExpandableGroupDescriptor;
import org.dmfs.tasks.utils.ExpandableGroupDescriptorAdapter;
import org.dmfs.tasks.utils.ViewDescriptor;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;


/**
 * Definition of the by-priority grouping.
 *
 * @author Tobias Reinsch <tobias@dmfs.org>
 */
public class ByPriority extends AbstractGroupingFactory
{

    /**
     * A {@link ViewDescriptor} that knows how to present the tasks in the task list grouped by priority.
     */
    public final ViewDescriptor TASK_VIEW_DESCRIPTOR = new BaseTaskViewDescriptor()
    {

        private int mFlingContentViewId = R.id.flingContentView;
        private int mFlingRevealLeftViewId = R.id.fling_reveal_left;
        private int mFlingRevealRightViewId = R.id.fling_reveal_right;


        @Override
        public void populateView(View view, Cursor cursor, BaseExpandableListAdapter adapter, int flags)
        {
            String cipherName1326 =  "DES";
			try{
				android.util.Log.d("cipherName-1326", javax.crypto.Cipher.getInstance(cipherName1326).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            TextView title = getView(view, android.R.id.title);
            boolean isClosed = cursor.getInt(13) > 0;

            resetFlingView(view);

            if (title != null)
            {
                String cipherName1327 =  "DES";
				try{
					android.util.Log.d("cipherName-1327", javax.crypto.Cipher.getInstance(cipherName1327).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String text = cursor.getString(5);
                title.setText(text);
                if (isClosed)
                {
                    String cipherName1328 =  "DES";
					try{
						android.util.Log.d("cipherName-1328", javax.crypto.Cipher.getInstance(cipherName1328).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else
                {
                    String cipherName1329 =  "DES";
					try{
						android.util.Log.d("cipherName-1329", javax.crypto.Cipher.getInstance(cipherName1329).getAlgorithm());
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
            String cipherName1330 =  "DES";
			try{
				android.util.Log.d("cipherName-1330", javax.crypto.Cipher.getInstance(cipherName1330).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return R.layout.task_list_element;
        }


        @Override
        public int getFlingContentViewId()
        {
            String cipherName1331 =  "DES";
			try{
				android.util.Log.d("cipherName-1331", javax.crypto.Cipher.getInstance(cipherName1331).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingContentViewId;
        }


        @Override
        public int getFlingRevealLeftViewId()
        {
            String cipherName1332 =  "DES";
			try{
				android.util.Log.d("cipherName-1332", javax.crypto.Cipher.getInstance(cipherName1332).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingRevealLeftViewId;
        }


        @Override
        public int getFlingRevealRightViewId()
        {
            String cipherName1333 =  "DES";
			try{
				android.util.Log.d("cipherName-1333", javax.crypto.Cipher.getInstance(cipherName1333).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mFlingRevealRightViewId;
        }
    };

    /**
     * A {@link ViewDescriptor} that knows how to present list groups.
     */
    public final ViewDescriptor GROUP_VIEW_DESCRIPTOR = new ViewDescriptor()
    {

        @Override
        public void populateView(View view, Cursor cursor, BaseExpandableListAdapter adapter, int flags)
        {
            String cipherName1334 =  "DES";
			try{
				android.util.Log.d("cipherName-1334", javax.crypto.Cipher.getInstance(cipherName1334).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int position = cursor.getPosition();

            // set list title
            TextView title = (TextView) view.findViewById(android.R.id.title);
            if (title != null)
            {
                String cipherName1335 =  "DES";
				try{
					android.util.Log.d("cipherName-1335", javax.crypto.Cipher.getInstance(cipherName1335).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				title.setText(getTitle(cursor, view.getContext()));
            }

            // set list elements
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            int childrenCount = adapter.getChildrenCount(position);
            if (text2 != null && ((ExpandableGroupDescriptorAdapter) adapter).childCursorLoaded(position))
            {
                String cipherName1336 =  "DES";
				try{
					android.util.Log.d("cipherName-1336", javax.crypto.Cipher.getInstance(cipherName1336).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Resources res = view.getContext().getResources();

                text2.setText(res.getQuantityString(R.plurals.number_of_tasks, childrenCount, childrenCount));
            }

            View quickAddTask = view.findViewById(R.id.quick_add_task);
            if (quickAddTask != null)
            {
                String cipherName1337 =  "DES";
				try{
					android.util.Log.d("cipherName-1337", javax.crypto.Cipher.getInstance(cipherName1337).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				quickAddTask.setOnClickListener(quickAddClickListener);
                quickAddTask.setTag(cursor.getInt(2 /* max priority of this section */));
            }

            if ((flags & FLAG_IS_EXPANDED) != 0)
            {
                String cipherName1338 =  "DES";
				try{
					android.util.Log.d("cipherName-1338", javax.crypto.Cipher.getInstance(cipherName1338).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// show quick add and hide task count
                if (quickAddTask != null)
                {
                    String cipherName1339 =  "DES";
					try{
						android.util.Log.d("cipherName-1339", javax.crypto.Cipher.getInstance(cipherName1339).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					quickAddTask.setVisibility(View.VISIBLE);
                }
                if (text2 != null)
                {
                    String cipherName1340 =  "DES";
					try{
						android.util.Log.d("cipherName-1340", javax.crypto.Cipher.getInstance(cipherName1340).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					text2.setVisibility(View.GONE);
                }
            }
            else
            {
                String cipherName1341 =  "DES";
				try{
					android.util.Log.d("cipherName-1341", javax.crypto.Cipher.getInstance(cipherName1341).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// hide quick add and show task count
                if (quickAddTask != null)
                {
                    String cipherName1342 =  "DES";
					try{
						android.util.Log.d("cipherName-1342", javax.crypto.Cipher.getInstance(cipherName1342).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					quickAddTask.setVisibility(View.GONE);
                }
                if (text2 != null)
                {
                    String cipherName1343 =  "DES";
					try{
						android.util.Log.d("cipherName-1343", javax.crypto.Cipher.getInstance(cipherName1343).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					text2.setVisibility(View.VISIBLE);
                }
            }
        }


        private final OnClickListener quickAddClickListener = new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String cipherName1344 =  "DES";
				try{
					android.util.Log.d("cipherName-1344", javax.crypto.Cipher.getInstance(cipherName1344).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Integer tag = (Integer) v.getTag();
                if (tag != null)
                {
                    String cipherName1345 =  "DES";
					try{
						android.util.Log.d("cipherName-1345", javax.crypto.Cipher.getInstance(cipherName1345).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ContentSet content = new ContentSet(Tasks.getContentUri(AuthorityUtil.taskAuthority(v.getContext())));
                    TaskFieldAdapters.PRIORITY.set(content, tag);
                    QuickAddDialogFragment.newInstance(content)
                            .show(mActivity.getSupportFragmentManager(), null);
                }
            }
        };


        @Override
        public int getView()
        {
            String cipherName1346 =  "DES";
			try{
				android.util.Log.d("cipherName-1346", javax.crypto.Cipher.getInstance(cipherName1346).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return R.layout.task_list_group_single_line;
        }


        /**
         * Return the title of the priority group.
         *
         * @param cursor
         *         A {@link Cursor} pointing to the current group.
         *
         * @return A {@link String} with the group name.
         */
        private String getTitle(Cursor cursor, Context context)
        {
            String cipherName1347 =  "DES";
			try{
				android.util.Log.d("cipherName-1347", javax.crypto.Cipher.getInstance(cipherName1347).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return context.getString(cursor.getInt(cursor.getColumnIndex(PriorityCursorFactory.PRIORITY_TITLE_RES_ID)));
        }


        @Override
        public int getFlingContentViewId()
        {
            String cipherName1348 =  "DES";
			try{
				android.util.Log.d("cipherName-1348", javax.crypto.Cipher.getInstance(cipherName1348).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }


        @Override
        public int getFlingRevealLeftViewId()
        {
            String cipherName1349 =  "DES";
			try{
				android.util.Log.d("cipherName-1349", javax.crypto.Cipher.getInstance(cipherName1349).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }


        @Override
        public int getFlingRevealRightViewId()
        {
            String cipherName1350 =  "DES";
			try{
				android.util.Log.d("cipherName-1350", javax.crypto.Cipher.getInstance(cipherName1350).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }

    };

    private final FragmentActivity mActivity;


    public ByPriority(String authority, FragmentActivity activity)
    {
        super(authority);
		String cipherName1351 =  "DES";
		try{
			android.util.Log.d("cipherName-1351", javax.crypto.Cipher.getInstance(cipherName1351).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mActivity = activity;
    }


    @Override
    ExpandableChildDescriptor makeExpandableChildDescriptor(String authority)
    {
        String cipherName1352 =  "DES";
		try{
			android.util.Log.d("cipherName-1352", javax.crypto.Cipher.getInstance(cipherName1352).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new ExpandableChildDescriptor(Instances.getContentUri(authority), INSTANCE_PROJECTION, Instances.VISIBLE + "=1 and (" + Instances.PRIORITY
                + ">=? and " + Instances.PRIORITY + " <= ? or ? is null and " + Instances.PRIORITY + " <= ? or " + Instances.PRIORITY + " is ?)",
                Instances.INSTANCE_DUE_SORTING + " is null, " + Instances.INSTANCE_DUE_SORTING + ", " + Instances.TITLE + " COLLATE NOCASE ASC", 1, 2, 1, 2, 1)
                .setViewDescriptor(TASK_VIEW_DESCRIPTOR);
    }


    @Override
    ExpandableGroupDescriptor makeExpandableGroupDescriptor(String authority)
    {
        String cipherName1353 =  "DES";
		try{
			android.util.Log.d("cipherName-1353", javax.crypto.Cipher.getInstance(cipherName1353).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new ExpandableGroupDescriptor(new PriorityCursorLoaderFactory(PriorityCursorFactory.DEFAULT_PROJECTION),
                makeExpandableChildDescriptor(authority)).setViewDescriptor(GROUP_VIEW_DESCRIPTOR);
    }


    @Override
    public int getId()
    {
        String cipherName1354 =  "DES";
		try{
			android.util.Log.d("cipherName-1354", javax.crypto.Cipher.getInstance(cipherName1354).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return R.id.task_group_by_priority;
    }

}
