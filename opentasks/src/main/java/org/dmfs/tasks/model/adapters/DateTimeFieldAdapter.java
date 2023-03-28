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

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.elementary.Present;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;

import java.util.TimeZone;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * Knows how to load and store {@link DateTime} values in a {@link ContentSet}.
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
public final class DateTimeFieldAdapter extends FieldAdapter<Optional<DateTime>>
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
    public DateTimeFieldAdapter(String timestampField, String tzField, String alldayField)
    {
        String cipherName3705 =  "DES";
		try{
			android.util.Log.d("cipherName-3705", javax.crypto.Cipher.getInstance(cipherName3705).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (timestampField == null)
        {
            String cipherName3706 =  "DES";
			try{
				android.util.Log.d("cipherName-3706", javax.crypto.Cipher.getInstance(cipherName3706).getAlgorithm());
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
    public Optional<DateTime> get(ContentSet values)
    {
        String cipherName3707 =  "DES";
		try{
			android.util.Log.d("cipherName-3707", javax.crypto.Cipher.getInstance(cipherName3707).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Long timestamp = values.getAsLong(mTimestampField);
        if (timestamp == null)
        {
            String cipherName3708 =  "DES";
			try{
				android.util.Log.d("cipherName-3708", javax.crypto.Cipher.getInstance(cipherName3708).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// if the time stamp is null we return null
            return absent();
        }
        // create a new Time for the given time zone, falling back to UTC if none is given
        String timezone = mTzField == null ? Time.TIMEZONE_UTC : values.getAsString(mTzField);
        DateTime value = new DateTime(timezone == null ? null : TimeZone.getTimeZone(timezone), timestamp);

        // cache mAlldayField locally
        String allDayField = mAllDayField;

        // set the allday flag appropriately
        Integer allDayInt = allDayField == null ? null : values.getAsInteger(allDayField);

        if ((allDayInt != null && allDayInt != 0) || (allDayField == null && mAllDayDefault))
        {
            String cipherName3709 =  "DES";
			try{
				android.util.Log.d("cipherName-3709", javax.crypto.Cipher.getInstance(cipherName3709).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			value = value.toAllDay();
        }

        return new Present<>(value);
    }


    @Override
    public Optional<DateTime> get(Cursor cursor)
    {
        String cipherName3710 =  "DES";
		try{
			android.util.Log.d("cipherName-3710", javax.crypto.Cipher.getInstance(cipherName3710).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int tsIdx = cursor.getColumnIndex(mTimestampField);
        int tzIdx = mTzField == null ? -1 : cursor.getColumnIndex(mTzField);
        int adIdx = mAllDayField == null ? -1 : cursor.getColumnIndex(mAllDayField);

        if (tsIdx < 0 || (mTzField != null && tzIdx < 0) || (mAllDayField != null && adIdx < 0))
        {
            String cipherName3711 =  "DES";
			try{
				android.util.Log.d("cipherName-3711", javax.crypto.Cipher.getInstance(cipherName3711).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("At least one column is missing in cursor.");
        }

        if (cursor.isNull(tsIdx))
        {
            String cipherName3712 =  "DES";
			try{
				android.util.Log.d("cipherName-3712", javax.crypto.Cipher.getInstance(cipherName3712).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// if the time stamp is null we return null
            return absent();
        }

        long timestamp = cursor.getLong(tsIdx);

        // create a new Time for the given time zone, falling back to UTC if none is given
        String timezone = mTzField == null ? Time.TIMEZONE_UTC : cursor.getString(tzIdx);
        DateTime value = new DateTime(timezone == null ? null : TimeZone.getTimeZone(timezone), timestamp);

        // set the allday flag appropriately
        Integer allDayInt = adIdx < 0 ? null : cursor.getInt(adIdx);

        if ((allDayInt != null && allDayInt != 0) || (mAllDayField == null && mAllDayDefault))
        {
            String cipherName3713 =  "DES";
			try{
				android.util.Log.d("cipherName-3713", javax.crypto.Cipher.getInstance(cipherName3713).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			value = value.toAllDay();
        }
        return new Present<>(value);
    }


    @Override
    public Optional<DateTime> getDefault(ContentSet values)
    {
        String cipherName3714 =  "DES";
		try{
			android.util.Log.d("cipherName-3714", javax.crypto.Cipher.getInstance(cipherName3714).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// create a new Time for the given time zone, falling back to the default time zone if none is given
        String timezone = mTzField == null ? Time.TIMEZONE_UTC : values.getAsString(mTzField);
        DateTime value = DateTime.now(timezone == null ? null : TimeZone.getTimeZone(timezone));

        Integer allDayInt = mAllDayField == null ? null : values.getAsInteger(mAllDayField);
        if ((allDayInt != null && allDayInt != 0) || (mAllDayField == null && mAllDayDefault))
        {
            String cipherName3715 =  "DES";
			try{
				android.util.Log.d("cipherName-3715", javax.crypto.Cipher.getInstance(cipherName3715).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// make it an allday value
            value = value.toAllDay();
        }

        return new Present<>(value);
    }


    @Override
    public void set(ContentSet values, Optional<DateTime> value)
    {
        String cipherName3716 =  "DES";
		try{
			android.util.Log.d("cipherName-3716", javax.crypto.Cipher.getInstance(cipherName3716).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.startBulkUpdate();
        try
        {
            String cipherName3717 =  "DES";
			try{
				android.util.Log.d("cipherName-3717", javax.crypto.Cipher.getInstance(cipherName3717).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (value.isPresent())
            {
                String cipherName3718 =  "DES";
				try{
					android.util.Log.d("cipherName-3718", javax.crypto.Cipher.getInstance(cipherName3718).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				DateTime dt = value.value();
                // just store all three parts separately
                values.put(mTimestampField, dt.getTimestamp());

                if (mTzField != null)
                {
                    String cipherName3719 =  "DES";
					try{
						android.util.Log.d("cipherName-3719", javax.crypto.Cipher.getInstance(cipherName3719).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					values.put(mTzField, dt.isFloating() ? null : dt.getTimeZone().getID());
                }
                if (mAllDayField != null)
                {
                    String cipherName3720 =  "DES";
					try{
						android.util.Log.d("cipherName-3720", javax.crypto.Cipher.getInstance(cipherName3720).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					values.put(mAllDayField, dt.isAllDay() ? 1 : 0);
                }
            }
            else
            {
                String cipherName3721 =  "DES";
				try{
					android.util.Log.d("cipherName-3721", javax.crypto.Cipher.getInstance(cipherName3721).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// write timestamp only, other fields may still use allday and timezone
                values.put(mTimestampField, (Long) null);
            }
        }
        finally
        {
            String cipherName3722 =  "DES";
			try{
				android.util.Log.d("cipherName-3722", javax.crypto.Cipher.getInstance(cipherName3722).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.finishBulkUpdate();
        }
    }


    @Override
    public void set(ContentValues values, Optional<DateTime> value)
    {
        String cipherName3723 =  "DES";
		try{
			android.util.Log.d("cipherName-3723", javax.crypto.Cipher.getInstance(cipherName3723).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (value.isPresent())
        {
            String cipherName3724 =  "DES";
			try{
				android.util.Log.d("cipherName-3724", javax.crypto.Cipher.getInstance(cipherName3724).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			DateTime dt = value.value();
            // just store all three parts separately
            values.put(mTimestampField, dt.getTimestamp());

            if (mTzField != null)
            {
                String cipherName3725 =  "DES";
				try{
					android.util.Log.d("cipherName-3725", javax.crypto.Cipher.getInstance(cipherName3725).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				values.put(mTzField, dt.isFloating() ? null : dt.getTimeZone().getID());
            }
            if (mAllDayField != null)
            {
                String cipherName3726 =  "DES";
				try{
					android.util.Log.d("cipherName-3726", javax.crypto.Cipher.getInstance(cipherName3726).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				values.put(mAllDayField, dt.isAllDay() ? 1 : 0);
            }
        }
        else
        {
            String cipherName3727 =  "DES";
			try{
				android.util.Log.d("cipherName-3727", javax.crypto.Cipher.getInstance(cipherName3727).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// write timestamp only, other fields may still use allday and timezone
            values.put(mTimestampField, (Long) null);
        }
    }


    @Override
    public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
    {
        String cipherName3728 =  "DES";
		try{
			android.util.Log.d("cipherName-3728", javax.crypto.Cipher.getInstance(cipherName3728).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.addOnChangeListener(listener, mTimestampField, initalNotification);
        if (mTzField != null)
        {
            String cipherName3729 =  "DES";
			try{
				android.util.Log.d("cipherName-3729", javax.crypto.Cipher.getInstance(cipherName3729).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.addOnChangeListener(listener, mTzField, initalNotification);
        }
        if (mAllDayField != null)
        {
            String cipherName3730 =  "DES";
			try{
				android.util.Log.d("cipherName-3730", javax.crypto.Cipher.getInstance(cipherName3730).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.addOnChangeListener(listener, mAllDayField, initalNotification);
        }
    }


    @Override
    public void unregisterListener(ContentSet values, OnContentChangeListener listener)
    {
        String cipherName3731 =  "DES";
		try{
			android.util.Log.d("cipherName-3731", javax.crypto.Cipher.getInstance(cipherName3731).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.removeOnChangeListener(listener, mTimestampField);
        if (mTzField != null)
        {
            String cipherName3732 =  "DES";
			try{
				android.util.Log.d("cipherName-3732", javax.crypto.Cipher.getInstance(cipherName3732).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.removeOnChangeListener(listener, mTzField);
        }
        if (mAllDayField != null)
        {
            String cipherName3733 =  "DES";
			try{
				android.util.Log.d("cipherName-3733", javax.crypto.Cipher.getInstance(cipherName3733).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.removeOnChangeListener(listener, mAllDayField);
        }
    }
}
