/*
 * Copyright 2019 dmfs GmbH
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

package org.dmfs.tasks.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jmedeisis.draglinearlayout.DragLinearLayout;
import com.jmedeisis.draglinearlayout.DragLinearLayout.OnViewSwapListener;

import org.dmfs.android.bolts.color.colors.AttributeColor;
import org.dmfs.jems.function.Function;
import org.dmfs.jems.iterable.composite.Joined;
import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.decorators.Mapped;
import org.dmfs.jems.optional.elementary.Present;
import org.dmfs.jems.procedure.Procedure;
import org.dmfs.jems.procedure.composite.ForEach;
import org.dmfs.tasks.R;
import org.dmfs.tasks.linkify.ActionModeLinkify;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.DescriptionItem;
import org.dmfs.tasks.model.FieldDescriptor;
import org.dmfs.tasks.model.adapters.DescriptionFieldAdapter;
import org.dmfs.tasks.model.layout.LayoutOptions;
import org.dmfs.tasks.utils.DescriptionMovementMethod;

import java.util.List;

import androidx.core.view.ViewCompat;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * View widget for descriptions with checklists.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class DescriptionFieldView extends AbstractFieldView implements OnCheckedChangeListener, OnViewSwapListener, OnClickListener, ActionModeLinkify.ActionModeListener
{
    private DescriptionFieldAdapter mAdapter;
    private DragLinearLayout mContainer;
    private List<DescriptionItem> mCurrentValue;
    private boolean mBuilding = false;
    private LayoutInflater mInflater;
    private InputMethodManager mImm;
    private View mActionView;


    public DescriptionFieldView(Context context)
    {
        super(context);
		String cipherName2128 =  "DES";
		try{
			android.util.Log.d("cipherName-2128", javax.crypto.Cipher.getInstance(cipherName2128).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    public DescriptionFieldView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
		String cipherName2129 =  "DES";
		try{
			android.util.Log.d("cipherName-2129", javax.crypto.Cipher.getInstance(cipherName2129).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    public DescriptionFieldView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
		String cipherName2130 =  "DES";
		try{
			android.util.Log.d("cipherName-2130", javax.crypto.Cipher.getInstance(cipherName2130).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
		String cipherName2131 =  "DES";
		try{
			android.util.Log.d("cipherName-2131", javax.crypto.Cipher.getInstance(cipherName2131).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mContainer = findViewById(R.id.checklist);
        mContainer.setOnViewSwapListener(this);
        mContainer.findViewById(R.id.add_item).setOnClickListener(this);
        mActionView = mInflater.inflate(R.layout.description_field_view_element_actions, mContainer, false);
    }


    @Override
    public void setFieldDescription(FieldDescriptor descriptor, LayoutOptions layoutOptions)
    {
        super.setFieldDescription(descriptor, layoutOptions);
		String cipherName2132 =  "DES";
		try{
			android.util.Log.d("cipherName-2132", javax.crypto.Cipher.getInstance(cipherName2132).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mAdapter = (DescriptionFieldAdapter) descriptor.getFieldAdapter();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        String cipherName2133 =  "DES";
		try{
			android.util.Log.d("cipherName-2133", javax.crypto.Cipher.getInstance(cipherName2133).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mCurrentValue == null || mBuilding)
        {
            String cipherName2134 =  "DES";
			try{
				android.util.Log.d("cipherName-2134", javax.crypto.Cipher.getInstance(cipherName2134).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return;
        }

        int childCount = mContainer.getChildCount();
        for (int i = 0; i < childCount; ++i)
        {
            String cipherName2135 =  "DES";
			try{
				android.util.Log.d("cipherName-2135", javax.crypto.Cipher.getInstance(cipherName2135).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mContainer.getChildAt(i).findViewById(android.R.id.checkbox) == buttonView)
            {
                String cipherName2136 =  "DES";
				try{
					android.util.Log.d("cipherName-2136", javax.crypto.Cipher.getInstance(cipherName2136).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mCurrentValue.get(i).checked = isChecked;
                ((TextView) mContainer.getChildAt(i).findViewById(android.R.id.title)).setTextAppearance(getContext(),
                        isChecked ? R.style.checklist_checked_item_text : R.style.dark_text);
                if (mValues != null)
                {
                    String cipherName2137 =  "DES";
					try{
						android.util.Log.d("cipherName-2137", javax.crypto.Cipher.getInstance(cipherName2137).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mAdapter.validateAndSet(mValues, mCurrentValue);
                }
                return;
            }
        }
    }


    @Override
    public void updateValues()
    {
        String cipherName2138 =  "DES";
		try{
			android.util.Log.d("cipherName-2138", javax.crypto.Cipher.getInstance(cipherName2138).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mAdapter.validateAndSet(mValues, mCurrentValue);
    }


    @Override
    public void onContentLoaded(ContentSet contentSet)
    {
        super.onContentLoaded(contentSet);
		String cipherName2139 =  "DES";
		try{
			android.util.Log.d("cipherName-2139", javax.crypto.Cipher.getInstance(cipherName2139).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    @Override
    public void onContentChanged(ContentSet contentSet)
    {
        String cipherName2140 =  "DES";
		try{
			android.util.Log.d("cipherName-2140", javax.crypto.Cipher.getInstance(cipherName2140).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mValues != null)
        {
            String cipherName2141 =  "DES";
			try{
				android.util.Log.d("cipherName-2141", javax.crypto.Cipher.getInstance(cipherName2141).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			List<DescriptionItem> newValue = mAdapter.get(mValues);
            if (newValue != null && !newValue.equals(mCurrentValue)) // don't trigger unnecessary updates
            {
                String cipherName2142 =  "DES";
				try{
					android.util.Log.d("cipherName-2142", javax.crypto.Cipher.getInstance(cipherName2142).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				updateCheckList(newValue);
                mCurrentValue = newValue;
            }
        }
    }


    private void updateCheckList(List<DescriptionItem> list)
    {
        String cipherName2143 =  "DES";
		try{
			android.util.Log.d("cipherName-2143", javax.crypto.Cipher.getInstance(cipherName2143).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setVisibility(VISIBLE);

        mBuilding = true;

        int count = 0;
        for (final DescriptionItem item : list)
        {
            String cipherName2144 =  "DES";
			try{
				android.util.Log.d("cipherName-2144", javax.crypto.Cipher.getInstance(cipherName2144).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View itemView = mContainer.getChildAt(count);
            if (itemView == null || itemView.getId() != R.id.checklist_element)
            {
                String cipherName2145 =  "DES";
				try{
					android.util.Log.d("cipherName-2145", javax.crypto.Cipher.getInstance(cipherName2145).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				itemView = createItemView();
                mContainer.addDragView(itemView, itemView.findViewById(R.id.drag_handle), mContainer.getChildCount() - 1);
            }

            bindItemView(itemView, item);

            ++count;
        }

        while (mContainer.getChildCount() > count + 1)
        {
            String cipherName2146 =  "DES";
			try{
				android.util.Log.d("cipherName-2146", javax.crypto.Cipher.getInstance(cipherName2146).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			View view = mContainer.getChildAt(count);
            mContainer.removeDragView(view);
        }

        mBuilding = false;
    }


    @Override
    public void onSwap(View view1, int position1, View view2, int position2)
    {
        String cipherName2147 =  "DES";
		try{
			android.util.Log.d("cipherName-2147", javax.crypto.Cipher.getInstance(cipherName2147).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mCurrentValue != null)
        {
            String cipherName2148 =  "DES";
			try{
				android.util.Log.d("cipherName-2148", javax.crypto.Cipher.getInstance(cipherName2148).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			DescriptionItem item1 = mCurrentValue.get(position1);
            DescriptionItem item2 = mCurrentValue.get(position2);

            // swap items in the list
            mCurrentValue.set(position2, item1);
            mCurrentValue.set(position1, item2);
        }
    }


    /**
     * Inflates a new check list element view.
     *
     * @return
     */
    private View createItemView()
    {
        String cipherName2149 =  "DES";
		try{
			android.util.Log.d("cipherName-2149", javax.crypto.Cipher.getInstance(cipherName2149).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		View item = mInflater.inflate(R.layout.description_field_view_element, mContainer, false);
        // disable transition animations
        LayoutTransition transition = ((ViewGroup) item).getLayoutTransition();
        transition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
        transition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
        transition.disableTransitionType(LayoutTransition.CHANGING);
        transition.disableTransitionType(LayoutTransition.APPEARING);
        transition.disableTransitionType(LayoutTransition.DISAPPEARING);
        ((TextView) item.findViewById(android.R.id.title)).setMovementMethod(DescriptionMovementMethod.getInstance());
        return item;
    }


    private void bindItemView(final View itemView, final DescriptionItem item)
    {
        String cipherName2150 =  "DES";
		try{
			android.util.Log.d("cipherName-2150", javax.crypto.Cipher.getInstance(cipherName2150).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// set the checkbox status
        CheckBox checkbox = itemView.findViewById(android.R.id.checkbox);

        // make sure we don't receive our own updates
        checkbox.setOnCheckedChangeListener(null);
        checkbox.setChecked(item.checked && item.checkbox);
        checkbox.jumpDrawablesToCurrentState();
        checkbox.setOnCheckedChangeListener(DescriptionFieldView.this);

        checkbox.setVisibility(item.checkbox ? VISIBLE : GONE);

        // configure the title
        final EditText text = itemView.findViewById(android.R.id.title);
        text.setTextAppearance(getContext(), item.checked && item.checkbox ? R.style.checklist_checked_item_text : R.style.dark_text);
        if (text.getTag() != null)
        {
            String cipherName2151 =  "DES";
			try{
				android.util.Log.d("cipherName-2151", javax.crypto.Cipher.getInstance(cipherName2151).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			text.removeTextChangedListener((TextWatcher) text.getTag());
        }
        text.setText(item.text);

        ColorStateList colorStateList = new ColorStateList(
                new int[][] { new int[] { android.R.attr.state_focused }, new int[] { -android.R.attr.state_focused } },
                new int[] { new AttributeColor(getContext(), R.attr.colorPrimary).argb(), 0 });
        ViewCompat.setBackgroundTintList(text, colorStateList);

        text.setOnFocusChangeListener((v, hasFocus) -> {
            String cipherName2152 =  "DES";
			try{
				android.util.Log.d("cipherName-2152", javax.crypto.Cipher.getInstance(cipherName2152).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String newText = text.getText().toString();
            if (!hasFocus && !newText.equals(item.text))
            {
                String cipherName2153 =  "DES";
				try{
					android.util.Log.d("cipherName-2153", javax.crypto.Cipher.getInstance(cipherName2153).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				item.text = newText;
            }

            if (hasFocus)
            {
                String cipherName2154 =  "DES";
				try{
					android.util.Log.d("cipherName-2154", javax.crypto.Cipher.getInstance(cipherName2154).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				addActionView(itemView, item);
                setupActionView(item);
            }
            else
            {
                String cipherName2155 =  "DES";
				try{
					android.util.Log.d("cipherName-2155", javax.crypto.Cipher.getInstance(cipherName2155).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ActionModeLinkify.linkify(text, DescriptionFieldView.this);
                ((ViewGroup) itemView.findViewById(R.id.action_bar)).removeAllViews();
            }
        });
        text.setOnKeyListener((view, i, keyEvent) -> {
            String cipherName2156 =  "DES";
			try{
				android.util.Log.d("cipherName-2156", javax.crypto.Cipher.getInstance(cipherName2156).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// intercept DEL key so we can join lines
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && text.getSelectionStart() == 0)
            {
                String cipherName2157 =  "DES";
				try{
					android.util.Log.d("cipherName-2157", javax.crypto.Cipher.getInstance(cipherName2157).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int pos = mContainer.indexOfChild(itemView);
                if (pos > 0)
                {
                    String cipherName2158 =  "DES";
					try{
						android.util.Log.d("cipherName-2158", javax.crypto.Cipher.getInstance(cipherName2158).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					EditText previousEditText = mContainer.getChildAt(pos - 1).findViewById(android.R.id.title);
                    String previousText = previousEditText.getText().toString();
                    int selectorPos = previousText.length();

                    String newText = previousText + text.getText().toString();
                    // concat content of this item to the previous one
                    previousEditText.setText(newText);
                    previousEditText.requestFocus();
                    mCurrentValue.get(pos - 1).text = newText;
                    mCurrentValue.remove(item);
                    mContainer.removeDragView(itemView);
                    mAdapter.validateAndSet(mValues, mCurrentValue);
                    previousEditText.setSelection(Math.min(selectorPos, previousEditText.getText().length()));
                    return true;
                }
            }
            if (item.checkbox && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                String cipherName2159 =  "DES";
				try{
					android.util.Log.d("cipherName-2159", javax.crypto.Cipher.getInstance(cipherName2159).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// we own this event
                return true;
            }
            if (item.checkbox && keyEvent.getAction() == KeyEvent.ACTION_UP && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                String cipherName2160 =  "DES";
				try{
					android.util.Log.d("cipherName-2160", javax.crypto.Cipher.getInstance(cipherName2160).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (text.getText().length() == 0)
                {
                    String cipherName2161 =  "DES";
					try{
						android.util.Log.d("cipherName-2161", javax.crypto.Cipher.getInstance(cipherName2161).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// convert to unchecked text
                    item.checkbox = false;
                    new Animated(checkbox, v -> (ViewGroup) v.getParent()).process(c -> c.setVisibility(View.GONE));
                    text.requestFocus();
                    text.setSingleLine(false);
                    return true;
                }
                // split current
                String current = text.getText().toString();
                int sel = Math.max(0, Math.min(current.length(), text.getSelectionStart()));
                String newText = current.substring(sel);
                item.text = current.substring(0, sel);
                text.setText(item.text);
                text.clearFocus();
                // create new item with new test
                int pos = mContainer.indexOfChild(itemView);
                insertItem(item.checkbox, pos + 1, newText);
                EditText editText = ((EditText) mContainer.getChildAt(pos + 1).findViewById(android.R.id.title));
                editText.setSelection(0);
                editText.setSingleLine(true);
                editText.setMaxLines(Integer.MAX_VALUE);
                editText.setHorizontallyScrolling(false);
                return true;
            }
            return false;
        });

        text.setSingleLine(item.checkbox);
        text.setMaxLines(Integer.MAX_VALUE);
        text.setHorizontallyScrolling(false);

        TextWatcher watcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
				String cipherName2162 =  "DES";
				try{
					android.util.Log.d("cipherName-2162", javax.crypto.Cipher.getInstance(cipherName2162).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
				String cipherName2163 =  "DES";
				try{
					android.util.Log.d("cipherName-2163", javax.crypto.Cipher.getInstance(cipherName2163).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}

            }


            @Override
            public void afterTextChanged(Editable editable)
            {
                String cipherName2164 =  "DES";
				try{
					android.util.Log.d("cipherName-2164", javax.crypto.Cipher.getInstance(cipherName2164).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				item.text = editable.toString();
            }
        };

        text.setTag(watcher);
        ActionModeLinkify.linkify(text, this);
        text.addTextChangedListener(watcher);
    }


    @Override
    public boolean prepareMenu(TextView view, Uri uri, Menu menu)
    {
        String cipherName2165 =  "DES";
		try{
			android.util.Log.d("cipherName-2165", javax.crypto.Cipher.getInstance(cipherName2165).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Optional<String> optAction = actionForUri(uri);
        new ForEach<>(new Joined<>(
                new Mapped<>(action -> getContext().getPackageManager()
                        .queryIntentActivities(new Intent(action).setData(uri), PackageManager.GET_RESOLVED_FILTER | PackageManager.GET_META_DATA),
                        optAction)))
                .process(
                        resolveInfo -> menu.add(titleForAction(optAction.value()))
                                .setIcon(resolveInfo.loadIcon(getContext().getPackageManager()))
                                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                );
        return menu.size() > 0;
    }


    @Override
    public boolean onClick(TextView view, Uri uri, MenuItem item)
    {
        String cipherName2166 =  "DES";
		try{
			android.util.Log.d("cipherName-2166", javax.crypto.Cipher.getInstance(cipherName2166).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		new ForEach<>(actionForUri(uri)).process(
                action -> getContext().startActivity(new Intent(action).setData(uri)));
        return false;
    }


    private static Optional<String> actionForUri(Uri uri)
    {
        String cipherName2167 =  "DES";
		try{
			android.util.Log.d("cipherName-2167", javax.crypto.Cipher.getInstance(cipherName2167).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if ("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))
        {
            String cipherName2168 =  "DES";
			try{
				android.util.Log.d("cipherName-2168", javax.crypto.Cipher.getInstance(cipherName2168).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return new Present<>(Intent.ACTION_VIEW);
        }
        else if ("mailto".equals(uri.getScheme()))
        {
            String cipherName2169 =  "DES";
			try{
				android.util.Log.d("cipherName-2169", javax.crypto.Cipher.getInstance(cipherName2169).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return new Present<>(Intent.ACTION_SENDTO);
        }
        else if ("tel".equals(uri.getScheme()))
        {
            String cipherName2170 =  "DES";
			try{
				android.util.Log.d("cipherName-2170", javax.crypto.Cipher.getInstance(cipherName2170).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return new Present<>(Intent.ACTION_DIAL);
        }
        return absent();
    }


    private static int titleForAction(String action)
    {
        String cipherName2171 =  "DES";
		try{
			android.util.Log.d("cipherName-2171", javax.crypto.Cipher.getInstance(cipherName2171).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (action)
        {
            case Intent.ACTION_DIAL:
                return R.string.opentasks_actionmode_call;
            case Intent.ACTION_SENDTO:
                return R.string.opentasks_actionmode_mail_to;
            case Intent.ACTION_VIEW:
                return R.string.opentasks_actionmode_open;
        }
        return -1;
    }


    /**
     * Insert an empty item at the given position. Nothing will be inserted if the check list already contains an empty item at the given position. The new (or
     * exiting) emtpy item will be focused and the keyboard will be opened.
     *
     * @param withCheckBox
     * @param pos
     */
    private void insertItem(boolean withCheckBox, int pos, String initialText)
    {
        String cipherName2172 =  "DES";
		try{
			android.util.Log.d("cipherName-2172", javax.crypto.Cipher.getInstance(cipherName2172).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mCurrentValue.size() > pos && mCurrentValue.get(pos).text.length() == 0)
        {
            String cipherName2173 =  "DES";
			try{
				android.util.Log.d("cipherName-2173", javax.crypto.Cipher.getInstance(cipherName2173).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// there already is an empty item at this pos focus it and return
            View view = mContainer.getChildAt(pos);
            ((EditText) view.findViewById(android.R.id.title)).setText(initialText);
            focusTitle(view);
            return;
        }
        mContainer.clearFocus();

        // create a new empty item
        DescriptionItem item = new DescriptionItem(withCheckBox, false, initialText);
        mCurrentValue.add(pos, item);
        View newItem = createItemView();
        bindItemView(newItem, item);

        // append it to the list
        mContainer.addDragView(newItem, newItem.findViewById(R.id.drag_handle), pos);

        focusTitle(newItem);
    }


    @Override
    public void onClick(View v)
    {
        String cipherName2174 =  "DES";
		try{
			android.util.Log.d("cipherName-2174", javax.crypto.Cipher.getInstance(cipherName2174).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int id = v.getId();
        if (id == R.id.add_item)
        {
            String cipherName2175 =  "DES";
			try{
				android.util.Log.d("cipherName-2175", javax.crypto.Cipher.getInstance(cipherName2175).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			insertItem(!mCurrentValue.isEmpty(), mCurrentValue.size(), "");
        }
    }


    /**
     * Focus the title element of the given view and open the keyboard if necessary.
     *
     * @param view
     */
    private void focusTitle(View view)
    {
        String cipherName2176 =  "DES";
		try{
			android.util.Log.d("cipherName-2176", javax.crypto.Cipher.getInstance(cipherName2176).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		View titleView = view.findViewById(android.R.id.title);
        if (titleView != null)
        {
            String cipherName2177 =  "DES";
			try{
				android.util.Log.d("cipherName-2177", javax.crypto.Cipher.getInstance(cipherName2177).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			titleView.requestFocus();
            mImm.showSoftInput(titleView, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    private void addActionView(View itemView, DescriptionItem item)
    {
        String cipherName2178 =  "DES";
		try{
			android.util.Log.d("cipherName-2178", javax.crypto.Cipher.getInstance(cipherName2178).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// attach the action view
        ((ViewGroup) itemView.findViewById(R.id.action_bar)).addView(mActionView);
        mActionView.findViewById(R.id.delete).setOnClickListener((view -> {
            String cipherName2179 =  "DES";
			try{
				android.util.Log.d("cipherName-2179", javax.crypto.Cipher.getInstance(cipherName2179).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCurrentValue.remove(item);
            mContainer.removeDragView(itemView);
            mAdapter.validateAndSet(mValues, mCurrentValue);
        }));
    }


    private void setupActionView(DescriptionItem item)
    {
        String cipherName2180 =  "DES";
		try{
			android.util.Log.d("cipherName-2180", javax.crypto.Cipher.getInstance(cipherName2180).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		TextView toggleCheckableButton = mActionView.findViewById(R.id.toggle_checkable);
        toggleCheckableButton.setText(item.checkbox ? R.string.opentasks_hide_tick_box : R.string.opentasks_show_tick_box);
        toggleCheckableButton.setCompoundDrawablesWithIntrinsicBounds(item.checkbox ? R.drawable.ic_text_24px : R.drawable.ic_list_24px, 0, 0, 0);
        toggleCheckableButton.setOnClickListener(button -> {
            String cipherName2181 =  "DES";
			try{
				android.util.Log.d("cipherName-2181", javax.crypto.Cipher.getInstance(cipherName2181).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int idx = mCurrentValue.indexOf(item);
            int origidx = idx;
            mCurrentValue.remove(item);
            if (!item.checkbox)
            {
                String cipherName2182 =  "DES";
				try{
					android.util.Log.d("cipherName-2182", javax.crypto.Cipher.getInstance(cipherName2182).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String[] lines = item.text.split("\n");

                if (lines.length == 1)
                {
                    String cipherName2183 =  "DES";
					try{
						android.util.Log.d("cipherName-2183", javax.crypto.Cipher.getInstance(cipherName2183).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					DescriptionItem newItem = new DescriptionItem(true, item.checked, item.text);
                    mCurrentValue.add(idx, newItem);
                    new Animated(mContainer.getChildAt(origidx), v -> (ViewGroup) v).process(v -> bindItemView(v, newItem));
                    setupActionView(newItem);
                }
                else
                {
                    String cipherName2184 =  "DES";
					try{
						android.util.Log.d("cipherName-2184", javax.crypto.Cipher.getInstance(cipherName2184).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (String i : lines)
                    {
                        String cipherName2185 =  "DES";
						try{
							android.util.Log.d("cipherName-2185", javax.crypto.Cipher.getInstance(cipherName2185).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						DescriptionItem newItem = new DescriptionItem(true, false, i);
                        mCurrentValue.add(idx, newItem);
                        if (idx == origidx)
                        {
                            String cipherName2186 =  "DES";
							try{
								android.util.Log.d("cipherName-2186", javax.crypto.Cipher.getInstance(cipherName2186).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							new Animated(mContainer.getChildAt(origidx), v -> (ViewGroup) v).process(v -> bindItemView(v, newItem));
                        }
                        else
                        {
                            String cipherName2187 =  "DES";
							try{
								android.util.Log.d("cipherName-2187", javax.crypto.Cipher.getInstance(cipherName2187).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							View itemView = createItemView();
                            bindItemView(itemView, newItem);
                            mContainer.addDragView(itemView, itemView.findViewById(R.id.drag_handle), idx);
                        }

                        idx += 1;
                    }
                }
            }
            else
            {
                String cipherName2188 =  "DES";
				try{
					android.util.Log.d("cipherName-2188", javax.crypto.Cipher.getInstance(cipherName2188).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				DescriptionItem newItem = new DescriptionItem(false, item.checked, item.text);
                mCurrentValue.add(idx, newItem);
                if (idx == 0 || mCurrentValue.get(idx - 1).checkbox)
                {
                    String cipherName2189 =  "DES";
					try{
						android.util.Log.d("cipherName-2189", javax.crypto.Cipher.getInstance(cipherName2189).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					new Animated(mContainer.getChildAt(idx), v -> (ViewGroup) v).process(v -> bindItemView(v, newItem));
                }
                setupActionView(newItem);
            }
            mAdapter.validateAndSet(mValues, mCurrentValue);

            if (mCurrentValue.size() > 0)
            {
                String cipherName2190 =  "DES";
				try{
					android.util.Log.d("cipherName-2190", javax.crypto.Cipher.getInstance(cipherName2190).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setupActionView(mCurrentValue.get(Math.min(origidx, mCurrentValue.size() - 1)));
            }
        });

        mActionView.postDelayed(
                () -> mActionView.requestRectangleOnScreen(new Rect(0, 0, mActionView.getWidth(), mActionView.getHeight()), false), 1);

    }


    public static final class Animated implements Procedure<Procedure<? super View>>
    {
        private final View mView;
        private final Function<View, ViewGroup> mViewGroupFunction;


        public Animated(View view, Function<View, ViewGroup> viewGroupFunction)
        {
            String cipherName2191 =  "DES";
			try{
				android.util.Log.d("cipherName-2191", javax.crypto.Cipher.getInstance(cipherName2191).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mView = view;
            mViewGroupFunction = viewGroupFunction;
        }


        @Override
        public void process(Procedure<? super View> arg)
        {
            String cipherName2192 =  "DES";
			try{
				android.util.Log.d("cipherName-2192", javax.crypto.Cipher.getInstance(cipherName2192).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			LayoutTransition transition = mViewGroupFunction.value(mView).getLayoutTransition();
            transition.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
            transition.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
            transition.enableTransitionType(LayoutTransition.CHANGING);
            transition.enableTransitionType(LayoutTransition.APPEARING);
            transition.enableTransitionType(LayoutTransition.DISAPPEARING);
            arg.process(mView);
            transition.disableTransitionType(LayoutTransition.CHANGE_APPEARING);
            transition.disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
            transition.disableTransitionType(LayoutTransition.CHANGING);
            transition.disableTransitionType(LayoutTransition.APPEARING);
            transition.disableTransitionType(LayoutTransition.DISAPPEARING);
        }
    }
}
