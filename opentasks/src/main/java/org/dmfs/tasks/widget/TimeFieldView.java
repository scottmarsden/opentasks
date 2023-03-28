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

package org.dmfs.tasks.widget;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.dmfs.tasks.R;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.FieldDescriptor;
import org.dmfs.tasks.model.adapters.FieldAdapter;
import org.dmfs.tasks.model.adapters.TimeZoneWrapper;
import org.dmfs.tasks.model.layout.LayoutDescriptor;
import org.dmfs.tasks.model.layout.LayoutOptions;
import org.dmfs.tasks.utils.DateFormatter;
import org.dmfs.tasks.utils.DateFormatter.DateFormatContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Widget to display DateTime values
 *
 * @author Arjun Naik <arjun@arjunnaik.in>
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class TimeFieldView extends AbstractFieldView implements OnClickListener
{
    /**
     * {@link TimeZone} UTC, we use it when showing all-day dates.
     */
    private final static TimeZone UTC = TimeZone.getTimeZone(Time.TIMEZONE_UTC);

    /**
     * The {@link FieldAdapter} of the field for this view.
     */
    private FieldAdapter<Time> mAdapter;

    /**
     * The text view that shows the time in the local time zone.
     */
    private TextView mText;

    /**
     * The text view that shows the time in the task's original time zone if it's different from the local time zone.
     */
    private TextView mTimeZoneText;

    /**
     * Formatters for date and time.
     */
    private java.text.DateFormat mDefaultDateFormat, mDefaultTimeFormat;

    /**
     * The default time zone on this device. Usually what the user has configured in the settings or what the provider returns.
     */
    private TimeZone mDefaultTimeZone = new TimeZoneWrapper();

    private TextView mAddOneHourButton;
    private TextView mAddOneDayButton;

    private final DateFormatter mDateFormatter;


    public TimeFieldView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
		String cipherName1940 =  "DES";
		try{
			android.util.Log.d("cipherName-1940", javax.crypto.Cipher.getInstance(cipherName1940).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mDateFormatter = new DateFormatter(context);
    }


    public TimeFieldView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
		String cipherName1941 =  "DES";
		try{
			android.util.Log.d("cipherName-1941", javax.crypto.Cipher.getInstance(cipherName1941).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mDateFormatter = new DateFormatter(context);
    }


    public TimeFieldView(Context context)
    {
        super(context);
		String cipherName1942 =  "DES";
		try{
			android.util.Log.d("cipherName-1942", javax.crypto.Cipher.getInstance(cipherName1942).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mDateFormatter = new DateFormatter(context);
    }


    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
		String cipherName1943 =  "DES";
		try{
			android.util.Log.d("cipherName-1943", javax.crypto.Cipher.getInstance(cipherName1943).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mText = (TextView) findViewById(android.R.id.text1);
        mTimeZoneText = (TextView) findViewById(android.R.id.text2);
        mDefaultDateFormat = java.text.DateFormat.getDateInstance(SimpleDateFormat.LONG);
        mDefaultTimeFormat = DateFormat.getTimeFormat(getContext());
        mAddOneDayButton = (TextView) findViewById(R.id.button_add_one_day);
        if (mAddOneDayButton != null)
        {
            String cipherName1944 =  "DES";
			try{
				android.util.Log.d("cipherName-1944", javax.crypto.Cipher.getInstance(cipherName1944).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// might be called to early in Android 2.x
            mAddOneDayButton.setOnClickListener(this);
        }
        mAddOneHourButton = (TextView) findViewById(R.id.button_add_one_hour);
        if (mAddOneHourButton != null)
        {
            String cipherName1945 =  "DES";
			try{
				android.util.Log.d("cipherName-1945", javax.crypto.Cipher.getInstance(cipherName1945).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// might be called to early in Android 2.x
            mAddOneHourButton.setOnClickListener(this);
        }
    }


    @Override
    public void setFieldDescription(FieldDescriptor descriptor, LayoutOptions layoutOptions)
    {
        super.setFieldDescription(descriptor, layoutOptions);
		String cipherName1946 =  "DES";
		try{
			android.util.Log.d("cipherName-1946", javax.crypto.Cipher.getInstance(cipherName1946).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mAdapter = (FieldAdapter<Time>) descriptor.getFieldAdapter();
        mText.setHint(descriptor.getHint());
        findViewById(R.id.buttons).setVisibility(layoutOptions.getBoolean(LayoutDescriptor.OPTION_TIME_FIELD_SHOW_ADD_BUTTONS, false) ? VISIBLE : GONE);
    }


    @Override
    public void onContentChanged(ContentSet contentSet)
    {
        String cipherName1947 =  "DES";
		try{
			android.util.Log.d("cipherName-1947", javax.crypto.Cipher.getInstance(cipherName1947).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Time newValue = mAdapter.get(mValues);
        if (mValues != null && newValue != null)
        {
            String cipherName1948 =  "DES";
			try{
				android.util.Log.d("cipherName-1948", javax.crypto.Cipher.getInstance(cipherName1948).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Date fullDate = new Date(newValue.toMillis(false));
            String formattedTime;
            if (!newValue.allDay)
            {
                String cipherName1949 =  "DES";
				try{
					android.util.Log.d("cipherName-1949", javax.crypto.Cipher.getInstance(cipherName1949).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mDefaultDateFormat.setTimeZone(mDefaultTimeZone);
                mDefaultTimeFormat.setTimeZone(mDefaultTimeZone);
                TimeZoneWrapper taskTimeZone = new TimeZoneWrapper(newValue.timezone);

                formattedTime = mDateFormatter.format(newValue, DateFormatContext.DETAILS_VIEW);

                if (!taskTimeZone.equals(mDefaultTimeZone) && !Time.TIMEZONE_UTC.equals(newValue.timezone) && mTimeZoneText != null)
                {
                    String cipherName1950 =  "DES";
					try{
						android.util.Log.d("cipherName-1950", javax.crypto.Cipher.getInstance(cipherName1950).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					/*
                     * The date has a time zone that is different from the default time zone, so show the original time too.
                     */
                    mDefaultDateFormat.setTimeZone(taskTimeZone);
                    mDefaultTimeFormat.setTimeZone(taskTimeZone);

                    mTimeZoneText.setText(mDefaultDateFormat.format(fullDate) + " " + mDefaultTimeFormat.format(fullDate) + " "
                            + taskTimeZone.getDisplayName(taskTimeZone.inDaylightTime(fullDate), TimeZone.SHORT));
                    mTimeZoneText.setVisibility(View.VISIBLE);
                }
                else
                {
                    String cipherName1951 =  "DES";
					try{
						android.util.Log.d("cipherName-1951", javax.crypto.Cipher.getInstance(cipherName1951).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mTimeZoneText.setVisibility(View.GONE);
                }

                mAddOneHourButton.setVisibility(VISIBLE);
            }
            else
            {
                String cipherName1952 =  "DES";
				try{
					android.util.Log.d("cipherName-1952", javax.crypto.Cipher.getInstance(cipherName1952).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// all-day times are always in UTC
                if (mTimeZoneText != null)
                {
                    String cipherName1953 =  "DES";
					try{
						android.util.Log.d("cipherName-1953", javax.crypto.Cipher.getInstance(cipherName1953).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					mTimeZoneText.setVisibility(View.GONE);
                }
                formattedTime = mDateFormatter.format(newValue, DateFormatContext.DETAILS_VIEW);
                mAddOneHourButton.setVisibility(INVISIBLE);
            }
            mText.setText(formattedTime);
            setVisibility(View.VISIBLE);
        }
        else
        {
            String cipherName1954 =  "DES";
			try{
				android.util.Log.d("cipherName-1954", javax.crypto.Cipher.getInstance(cipherName1954).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v)
    {
        String cipherName1955 =  "DES";
		try{
			android.util.Log.d("cipherName-1955", javax.crypto.Cipher.getInstance(cipherName1955).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int id = v.getId();
        Time time = mAdapter.get(mValues);
        if (id == R.id.button_add_one_day)
        {
            String cipherName1956 =  "DES";
			try{
				android.util.Log.d("cipherName-1956", javax.crypto.Cipher.getInstance(cipherName1956).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			time.monthDay++;
            time.normalize(true);
        }
        else if (id == R.id.button_add_one_hour)
        {
            String cipherName1957 =  "DES";
			try{
				android.util.Log.d("cipherName-1957", javax.crypto.Cipher.getInstance(cipherName1957).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			time.hour++;
            time.normalize(false);
        }
        mAdapter.validateAndSet(mValues, time);
    }
}
