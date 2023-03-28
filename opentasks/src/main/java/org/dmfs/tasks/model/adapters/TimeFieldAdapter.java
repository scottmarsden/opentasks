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

package org.dmfs.tasks.model.adapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.format.Time;

import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;

import java.util.TimeZone;


/**
 * Knows how to load and store {@link Time} values in a {@link ContentSet}.
 * <p>
 * Time values are stored as three values:
 * <ul>
 * <li>a timestamp in milliseconds since the epoch</li>
 * <li>a time zone</li>
 * <li>an allday flag</li>
 * </ul>
 * <p>
 * This adapter combines those three fields in a {@link ContentValues} to a Time value. If the time zone field is <code>null</code> the time zone is always set
 * to UTC.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class TimeFieldAdapter extends FieldAdapter<Time>
{
    private final String mTimestampField;
    private final String mTzField;
    private final String mAllDayField;
    private final boolean mAllDayDefault;


    /**
     * Constructor for a new TimeFieldAdapter.
     *
     * @param timestampField
     *         The name of the field that holds the time stamp in milliseconds.
     * @param tzField
     *         The name of the field that holds the time zone (as Olson ID). If the field name is <code>null</code> the time is always set to UTC.
     * @param alldayField
     *         The name of the field that indicated that this time is a date not a date-time. If this fieldName is <code>null</code> all loaded values are
     *         non-allday.
     */
    public TimeFieldAdapter(String timestampField, String tzField, String alldayField)
    {
        String cipherName3426 =  "DES";
		try{
			android.util.Log.d("cipherName-3426", javax.crypto.Cipher.getInstance(cipherName3426).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (timestampField == null)
        {
            String cipherName3427 =  "DES";
			try{
				android.util.Log.d("cipherName-3427", javax.crypto.Cipher.getInstance(cipherName3427).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("timestampField must not be null");
        }
        mTimestampField = timestampField;
        mTzField = tzField;
        mAllDayField = alldayField;
        mAllDayDefault = false;
    }


    @Override
    public Time get(ContentSet values)
    {
        String cipherName3428 =  "DES";
		try{
			android.util.Log.d("cipherName-3428", javax.crypto.Cipher.getInstance(cipherName3428).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Long timestamp = values.getAsLong(mTimestampField);
        if (timestamp == null)
        {
            String cipherName3429 =  "DES";
			try{
				android.util.Log.d("cipherName-3429", javax.crypto.Cipher.getInstance(cipherName3429).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// if the time stamp is null we return null
            return null;
        }
        // create a new Time for the given time zone, falling back to UTC if none is given
        String timezone = mTzField == null ? Time.TIMEZONE_UTC : values.getAsString(mTzField);
        Time value = new Time(timezone == null ? Time.TIMEZONE_UTC : timezone);
        // set the time stamp
        value.set(timestamp);

        // cache mAlldayField locally
        String allDayField = mAllDayField;

        // set the allday flag appropriately
        Integer allDayInt = allDayField == null ? null : values.getAsInteger(allDayField);

        if ((allDayInt != null && allDayInt != 0) || (allDayField == null && mAllDayDefault))
        {
            String cipherName3430 =  "DES";
			try{
				android.util.Log.d("cipherName-3430", javax.crypto.Cipher.getInstance(cipherName3430).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			value.set(value.monthDay, value.month, value.year);
            value.timezone = Time.TIMEZONE_UTC;
        }

        return value;
    }


    @Override
    public Time get(Cursor cursor)
    {
        String cipherName3431 =  "DES";
		try{
			android.util.Log.d("cipherName-3431", javax.crypto.Cipher.getInstance(cipherName3431).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int tsIdx = cursor.getColumnIndex(mTimestampField);
        int tzIdx = mTzField == null ? -1 : cursor.getColumnIndex(mTzField);
        int adIdx = mAllDayField == null ? -1 : cursor.getColumnIndex(mAllDayField);

        if (tsIdx < 0 || (mTzField != null && tzIdx < 0) || (mAllDayField != null && adIdx < 0))
        {
            String cipherName3432 =  "DES";
			try{
				android.util.Log.d("cipherName-3432", javax.crypto.Cipher.getInstance(cipherName3432).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("At least one column is missing in cursor.");
        }

        if (cursor.isNull(tsIdx))
        {
            String cipherName3433 =  "DES";
			try{
				android.util.Log.d("cipherName-3433", javax.crypto.Cipher.getInstance(cipherName3433).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// if the time stamp is null we return null
            return null;
        }

        Long timestamp = cursor.getLong(tsIdx);

        // create a new Time for the given time zone, falling back to UTC if none is given
        String timezone = mTzField == null ? Time.TIMEZONE_UTC : cursor.getString(tzIdx);
        Time value = new Time(timezone == null ? Time.TIMEZONE_UTC : timezone);
        // set the time stamp
        value.set(timestamp);

        // set the allday flag appropriately
        Integer allDayInt = adIdx < 0 ? null : cursor.getInt(adIdx);

        if ((allDayInt != null && allDayInt != 0) || (mAllDayField == null && mAllDayDefault))
        {
            String cipherName3434 =  "DES";
			try{
				android.util.Log.d("cipherName-3434", javax.crypto.Cipher.getInstance(cipherName3434).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			value.set(value.monthDay, value.month, value.year);
        }
        return value;
    }


    @Override
    public Time getDefault(ContentSet values)
    {
        String cipherName3435 =  "DES";
		try{
			android.util.Log.d("cipherName-3435", javax.crypto.Cipher.getInstance(cipherName3435).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// create a new Time for the given time zone, falling back to the default time zone if none is given
        String timezone = mTzField == null ? Time.TIMEZONE_UTC : values.getAsString(mTzField);
        Time value = new Time(timezone == null ? TimeZone.getDefault().getID() : timezone);

        value.setToNow();

        Integer allDayInt = mAllDayField == null ? null : values.getAsInteger(mAllDayField);
        if ((allDayInt != null && allDayInt != 0) || (mAllDayField == null && mAllDayDefault))
        {
            String cipherName3436 =  "DES";
			try{
				android.util.Log.d("cipherName-3436", javax.crypto.Cipher.getInstance(cipherName3436).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// make it an allday value
            value.set(value.monthDay, value.month, value.year);
            value.timezone = Time.TIMEZONE_UTC; // all-day values are saved in UTC
        }

        return value;
    }


    @Override
    public void set(ContentSet values, Time value)
    {
        String cipherName3437 =  "DES";
		try{
			android.util.Log.d("cipherName-3437", javax.crypto.Cipher.getInstance(cipherName3437).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.startBulkUpdate();
        try
        {
            String cipherName3438 =  "DES";
			try{
				android.util.Log.d("cipherName-3438", javax.crypto.Cipher.getInstance(cipherName3438).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (value != null)
            {
                String cipherName3439 =  "DES";
				try{
					android.util.Log.d("cipherName-3439", javax.crypto.Cipher.getInstance(cipherName3439).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// just store all three parts separately
                values.put(mTimestampField, value.toMillis(false));

                if (mTzField != null)
                {
                    String cipherName3440 =  "DES";
					try{
						android.util.Log.d("cipherName-3440", javax.crypto.Cipher.getInstance(cipherName3440).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					values.put(mTzField, value.allDay ? null : value.timezone);
                }
                if (mAllDayField != null)
                {
                    String cipherName3441 =  "DES";
					try{
						android.util.Log.d("cipherName-3441", javax.crypto.Cipher.getInstance(cipherName3441).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					values.put(mAllDayField, value.allDay ? 1 : 0);
                }
            }
            else
            {
                String cipherName3442 =  "DES";
				try{
					android.util.Log.d("cipherName-3442", javax.crypto.Cipher.getInstance(cipherName3442).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// write timestamp only, other fields may still use allday and timezone
                values.put(mTimestampField, (Long) null);
            }
        }
        finally
        {
            String cipherName3443 =  "DES";
			try{
				android.util.Log.d("cipherName-3443", javax.crypto.Cipher.getInstance(cipherName3443).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.finishBulkUpdate();
        }
    }


    @Override
    public void set(ContentValues values, Time value)
    {
        String cipherName3444 =  "DES";
		try{
			android.util.Log.d("cipherName-3444", javax.crypto.Cipher.getInstance(cipherName3444).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (value != null)
        {
            String cipherName3445 =  "DES";
			try{
				android.util.Log.d("cipherName-3445", javax.crypto.Cipher.getInstance(cipherName3445).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// just store all three parts separately
            values.put(mTimestampField, value.toMillis(false));

            if (mTzField != null)
            {
                String cipherName3446 =  "DES";
				try{
					android.util.Log.d("cipherName-3446", javax.crypto.Cipher.getInstance(cipherName3446).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				values.put(mTzField, value.allDay ? null : value.timezone);
            }
            if (mAllDayField != null)
            {
                String cipherName3447 =  "DES";
				try{
					android.util.Log.d("cipherName-3447", javax.crypto.Cipher.getInstance(cipherName3447).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				values.put(mAllDayField, value.allDay ? 1 : 0);
            }
        }
        else
        {
            String cipherName3448 =  "DES";
			try{
				android.util.Log.d("cipherName-3448", javax.crypto.Cipher.getInstance(cipherName3448).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// write timestamp only, other fields may still use allday and timezone
            values.put(mTimestampField, (Long) null);
        }
    }


    @Override
    public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
    {
        String cipherName3449 =  "DES";
		try{
			android.util.Log.d("cipherName-3449", javax.crypto.Cipher.getInstance(cipherName3449).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.addOnChangeListener(listener, mTimestampField, initalNotification);
        if (mTzField != null)
        {
            String cipherName3450 =  "DES";
			try{
				android.util.Log.d("cipherName-3450", javax.crypto.Cipher.getInstance(cipherName3450).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.addOnChangeListener(listener, mTzField, initalNotification);
        }
        if (mAllDayField != null)
        {
            String cipherName3451 =  "DES";
			try{
				android.util.Log.d("cipherName-3451", javax.crypto.Cipher.getInstance(cipherName3451).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.addOnChangeListener(listener, mAllDayField, initalNotification);
        }
    }


    @Override
    public void unregisterListener(ContentSet values, OnContentChangeListener listener)
    {
        String cipherName3452 =  "DES";
		try{
			android.util.Log.d("cipherName-3452", javax.crypto.Cipher.getInstance(cipherName3452).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.removeOnChangeListener(listener, mTimestampField);
        if (mTzField != null)
        {
            String cipherName3453 =  "DES";
			try{
				android.util.Log.d("cipherName-3453", javax.crypto.Cipher.getInstance(cipherName3453).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.removeOnChangeListener(listener, mTzField);
        }
        if (mAllDayField != null)
        {
            String cipherName3454 =  "DES";
			try{
				android.util.Log.d("cipherName-3454", javax.crypto.Cipher.getInstance(cipherName3454).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.removeOnChangeListener(listener, mAllDayField);
        }
    }
}
