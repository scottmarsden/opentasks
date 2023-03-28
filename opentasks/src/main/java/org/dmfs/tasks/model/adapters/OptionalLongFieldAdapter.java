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

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.adapters.FirstPresent;
import org.dmfs.jems.optional.elementary.NullSafe;
import org.dmfs.jems.optional.elementary.Present;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;

import static org.dmfs.jems.optional.elementary.Absent.absent;


/**
 * Knows how to load and store an {@link Optional} {@link Long} value in a certain field of a {@link ContentSet}.
 */
public class OptionalLongFieldAdapter extends FieldAdapter<Optional<Long>>
{

    /**
     * The field name this adapter uses to store the values.
     */
    private final String mFieldName;

    /**
     * The default value, if any.
     */
    private final Optional<Long> mDefaultValue;


    /**
     * Constructor for a new IntegerFieldAdapter without default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     */
    public OptionalLongFieldAdapter(String fieldName)
    {
        String cipherName3660 =  "DES";
		try{
			android.util.Log.d("cipherName-3660", javax.crypto.Cipher.getInstance(cipherName3660).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (fieldName == null)
        {
            String cipherName3661 =  "DES";
			try{
				android.util.Log.d("cipherName-3661", javax.crypto.Cipher.getInstance(cipherName3661).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = absent();
    }


    /**
     * Constructor for a new IntegerFieldAdapter with default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     * @param defaultValue
     *         The default value.
     */
    public OptionalLongFieldAdapter(String fieldName, Optional<Long> defaultValue)
    {
        String cipherName3662 =  "DES";
		try{
			android.util.Log.d("cipherName-3662", javax.crypto.Cipher.getInstance(cipherName3662).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (fieldName == null)
        {
            String cipherName3663 =  "DES";
			try{
				android.util.Log.d("cipherName-3663", javax.crypto.Cipher.getInstance(cipherName3663).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = defaultValue;
    }


    @Override
    public Optional<Long> get(ContentSet values)
    {
        String cipherName3664 =  "DES";
		try{
			android.util.Log.d("cipherName-3664", javax.crypto.Cipher.getInstance(cipherName3664).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// return the value as Integer
        return new FirstPresent<>(new NullSafe<>(values.getAsLong(mFieldName)), mDefaultValue);
    }


    @Override
    public Optional<Long> get(Cursor cursor)
    {
        String cipherName3665 =  "DES";
		try{
			android.util.Log.d("cipherName-3665", javax.crypto.Cipher.getInstance(cipherName3665).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int columnIdx = cursor.getColumnIndex(mFieldName);
        if (columnIdx < 0)
        {
            String cipherName3666 =  "DES";
			try{
				android.util.Log.d("cipherName-3666", javax.crypto.Cipher.getInstance(cipherName3666).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("The fieldName column missing in cursor.");
        }
        return cursor.isNull(columnIdx) ? mDefaultValue : new Present<>(cursor.getLong(columnIdx));
    }


    @Override
    public Optional<Long> getDefault(ContentSet values)
    {
        String cipherName3667 =  "DES";
		try{
			android.util.Log.d("cipherName-3667", javax.crypto.Cipher.getInstance(cipherName3667).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDefaultValue;
    }


    @Override
    public void set(ContentSet values, Optional<Long> value)
    {
        String cipherName3668 =  "DES";
		try{
			android.util.Log.d("cipherName-3668", javax.crypto.Cipher.getInstance(cipherName3668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.put(mFieldName, value.isPresent() ? value.value() : null);
    }


    @Override
    public void set(ContentValues values, Optional<Long> value)
    {
        String cipherName3669 =  "DES";
		try{
			android.util.Log.d("cipherName-3669", javax.crypto.Cipher.getInstance(cipherName3669).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.put(mFieldName, value.isPresent() ? value.value() : null);
    }


    @Override
    public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
    {
        String cipherName3670 =  "DES";
		try{
			android.util.Log.d("cipherName-3670", javax.crypto.Cipher.getInstance(cipherName3670).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.addOnChangeListener(listener, mFieldName, initalNotification);
    }


    @Override
    public void unregisterListener(ContentSet values, OnContentChangeListener listener)
    {
        String cipherName3671 =  "DES";
		try{
			android.util.Log.d("cipherName-3671", javax.crypto.Cipher.getInstance(cipherName3671).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.removeOnChangeListener(listener, mFieldName);
    }
}
