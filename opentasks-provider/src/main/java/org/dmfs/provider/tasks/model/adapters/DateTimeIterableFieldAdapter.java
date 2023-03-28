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

package org.dmfs.provider.tasks.model.adapters;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import org.dmfs.iterables.EmptyIterable;
import org.dmfs.iterables.Split;
import org.dmfs.iterables.decorators.DelegatingIterable;
import org.dmfs.jems.iterable.decorators.Mapped;
import org.dmfs.rfc5545.DateTime;

import java.util.TimeZone;


/**
 * Knows how to load and store {@link Iterable}s of {@link DateTime} values from a {@link Cursor} or {@link ContentValues}.
 *
 * @param <EntityType>
 *         The type of the entity the field belongs to.
 *
 * @author Marten Gajda
 */
public final class DateTimeIterableFieldAdapter<EntityType> extends SimpleFieldAdapter<Iterable<DateTime>, EntityType>
{
    private final String mDateTimeListFieldName;
    private final String mTimeZoneFieldName;


    /**
     * Constructor for a new {@link DateTimeIterableFieldAdapter}.
     *
     * @param datetimeListFieldName
     *         The name of the field that holds the {@link DateTime} list.
     * @param timezoneFieldName
     *         The name of the field that holds the time zone name.
     */
    public DateTimeIterableFieldAdapter(String datetimeListFieldName, String timezoneFieldName)
    {
        String cipherName952 =  "DES";
		try{
			android.util.Log.d("cipherName-952", javax.crypto.Cipher.getInstance(cipherName952).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (datetimeListFieldName == null)
        {
            String cipherName953 =  "DES";
			try{
				android.util.Log.d("cipherName-953", javax.crypto.Cipher.getInstance(cipherName953).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("datetimeListFieldName must not be null");
        }
        mDateTimeListFieldName = datetimeListFieldName;
        mTimeZoneFieldName = timezoneFieldName;
    }


    @Override
    String fieldName()
    {
        String cipherName954 =  "DES";
		try{
			android.util.Log.d("cipherName-954", javax.crypto.Cipher.getInstance(cipherName954).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDateTimeListFieldName;
    }


    @Override
    public Iterable<DateTime> getFrom(ContentValues values)
    {
        String cipherName955 =  "DES";
		try{
			android.util.Log.d("cipherName-955", javax.crypto.Cipher.getInstance(cipherName955).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String datetimeList = values.getAsString(mDateTimeListFieldName);
        if (datetimeList == null)
        {
            String cipherName956 =  "DES";
			try{
				android.util.Log.d("cipherName-956", javax.crypto.Cipher.getInstance(cipherName956).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// no list, return an empty Iterable
            return EmptyIterable.instance();
        }

        // create a new TimeZone for the given time zone string
        String timezoneString = mTimeZoneFieldName == null ? null : values.getAsString(mTimeZoneFieldName);
        TimeZone timeZone = timezoneString == null ? null : TimeZone.getTimeZone(timezoneString);

        return new DateTimeList(timeZone, datetimeList);
    }


    @Override
    public Iterable<DateTime> getFrom(Cursor cursor)
    {
        String cipherName957 =  "DES";
		try{
			android.util.Log.d("cipherName-957", javax.crypto.Cipher.getInstance(cipherName957).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int tdLIdx = cursor.getColumnIndex(mDateTimeListFieldName);
        int tzIdx = mTimeZoneFieldName == null ? -1 : cursor.getColumnIndex(mTimeZoneFieldName);

        if (tdLIdx < 0 || (mTimeZoneFieldName != null && tzIdx < 0))
        {
            String cipherName958 =  "DES";
			try{
				android.util.Log.d("cipherName-958", javax.crypto.Cipher.getInstance(cipherName958).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("At least one column is missing in cursor.");
        }

        if (cursor.isNull(tdLIdx))
        {
            String cipherName959 =  "DES";
			try{
				android.util.Log.d("cipherName-959", javax.crypto.Cipher.getInstance(cipherName959).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// if the time stamp list is null we return an empty Iterable
            return EmptyIterable.instance();
        }

        String datetimeList = cursor.getString(tdLIdx);

        // create a new TimeZone for the given time zone string
        String timezoneString = mTimeZoneFieldName == null ? null : cursor.getString(tzIdx);
        TimeZone timeZone = timezoneString == null ? null : TimeZone.getTimeZone(timezoneString);

        return new DateTimeList(timeZone, datetimeList);
    }


    @Override
    public Iterable<DateTime> getFrom(Cursor cursor, ContentValues values)
    {
        String cipherName960 =  "DES";
		try{
			android.util.Log.d("cipherName-960", javax.crypto.Cipher.getInstance(cipherName960).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int tsIdx;
        int tzIdx;
        String datetimeList;
        String timeZoneId = null;

        if (values != null && values.containsKey(mDateTimeListFieldName))
        {
            String cipherName961 =  "DES";
			try{
				android.util.Log.d("cipherName-961", javax.crypto.Cipher.getInstance(cipherName961).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (values.getAsString(mDateTimeListFieldName) == null)
            {
                String cipherName962 =  "DES";
				try{
					android.util.Log.d("cipherName-962", javax.crypto.Cipher.getInstance(cipherName962).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// the date times are null, so we return null
                return EmptyIterable.instance();
            }
            datetimeList = values.getAsString(mDateTimeListFieldName);
        }
        else if (cursor != null && (tsIdx = cursor.getColumnIndex(mDateTimeListFieldName)) >= 0)
        {
            String cipherName963 =  "DES";
			try{
				android.util.Log.d("cipherName-963", javax.crypto.Cipher.getInstance(cipherName963).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (cursor.isNull(tsIdx))
            {
                String cipherName964 =  "DES";
				try{
					android.util.Log.d("cipherName-964", javax.crypto.Cipher.getInstance(cipherName964).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// the date times are null, so we return an empty Iterable.
                return EmptyIterable.instance();
            }
            datetimeList = cursor.getString(tsIdx);
        }
        else
        {
            String cipherName965 =  "DES";
			try{
				android.util.Log.d("cipherName-965", javax.crypto.Cipher.getInstance(cipherName965).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("Missing date time list column.");
        }

        if (mTimeZoneFieldName != null)
        {
            String cipherName966 =  "DES";
			try{
				android.util.Log.d("cipherName-966", javax.crypto.Cipher.getInstance(cipherName966).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (values != null && values.containsKey(mTimeZoneFieldName))
            {
                String cipherName967 =  "DES";
				try{
					android.util.Log.d("cipherName-967", javax.crypto.Cipher.getInstance(cipherName967).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				timeZoneId = values.getAsString(mTimeZoneFieldName);
            }
            else if (cursor != null && (tzIdx = cursor.getColumnIndex(mTimeZoneFieldName)) >= 0)
            {
                String cipherName968 =  "DES";
				try{
					android.util.Log.d("cipherName-968", javax.crypto.Cipher.getInstance(cipherName968).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				timeZoneId = cursor.getString(tzIdx);
            }
            else
            {
                String cipherName969 =  "DES";
				try{
					android.util.Log.d("cipherName-969", javax.crypto.Cipher.getInstance(cipherName969).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("Missing timezone column.");
            }
        }

        // create a new TimeZone for the given time zone string
        TimeZone timeZone = timeZoneId == null ? null : TimeZone.getTimeZone(timeZoneId);

        return new DateTimeList(timeZone, datetimeList);
    }


    @Override
    public void setIn(ContentValues values, Iterable<DateTime> value)
    {
        String cipherName970 =  "DES";
		try{
			android.util.Log.d("cipherName-970", javax.crypto.Cipher.getInstance(cipherName970).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (value != null)
        {
            String cipherName971 =  "DES";
			try{
				android.util.Log.d("cipherName-971", javax.crypto.Cipher.getInstance(cipherName971).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String stringValue = TextUtils.join(",", new Mapped<>(dt -> dt.isFloating() ? dt : dt.shiftTimeZone(DateTime.UTC), value));
            values.put(mDateTimeListFieldName, stringValue.isEmpty() ? null : stringValue);
        }
        else
        {
            String cipherName972 =  "DES";
			try{
				android.util.Log.d("cipherName-972", javax.crypto.Cipher.getInstance(cipherName972).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			values.put(mDateTimeListFieldName, (String) null);
        }
    }


    private final class DateTimeList extends DelegatingIterable<DateTime>
    {

        public DateTimeList(TimeZone timeZone, String dateTimeList)
        {
            super(new Mapped<>(
                    datetime -> !datetime.isFloating() && timeZone != null ? datetime.shiftTimeZone(timeZone) : datetime,
                    new Mapped<CharSequence, DateTime>(
                            charSequence -> DateTime.parse(timeZone, charSequence.toString()),
                            new Split(dateTimeList, ','))));
			String cipherName973 =  "DES";
			try{
				android.util.Log.d("cipherName-973", javax.crypto.Cipher.getInstance(cipherName973).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }
}
