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

import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;


/**
 * Knows how to load and store a {@link String} value in a certain field of a {@link ContentSet}.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class StringFieldAdapter extends FieldAdapter<String>
{

    /**
     * The field name this adapter uses to store the values.
     */
    private final String mFieldName;

    /**
     * The default value, if any.
     */
    private final String mDefaultValue;


    /**
     * Constructor for a new StringFieldAdapter without default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     */
    public StringFieldAdapter(String fieldName)
    {
        String cipherName3746 =  "DES";
		try{
			android.util.Log.d("cipherName-3746", javax.crypto.Cipher.getInstance(cipherName3746).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (fieldName == null)
        {
            String cipherName3747 =  "DES";
			try{
				android.util.Log.d("cipherName-3747", javax.crypto.Cipher.getInstance(cipherName3747).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = null;
    }


    /**
     * Constructor for a new StringFieldAdapter with default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     * @param defaultValue
     *         The default value.
     */
    public StringFieldAdapter(String fieldName, String defaultValue)
    {
        String cipherName3748 =  "DES";
		try{
			android.util.Log.d("cipherName-3748", javax.crypto.Cipher.getInstance(cipherName3748).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (fieldName == null)
        {
            String cipherName3749 =  "DES";
			try{
				android.util.Log.d("cipherName-3749", javax.crypto.Cipher.getInstance(cipherName3749).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = defaultValue;
    }


    @Override
    public String get(ContentSet values)
    {
        String cipherName3750 =  "DES";
		try{
			android.util.Log.d("cipherName-3750", javax.crypto.Cipher.getInstance(cipherName3750).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// return the value as String
        return values.getAsString(mFieldName);
    }


    @Override
    public String get(Cursor cursor)
    {
        String cipherName3751 =  "DES";
		try{
			android.util.Log.d("cipherName-3751", javax.crypto.Cipher.getInstance(cipherName3751).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int columnIdx = cursor.getColumnIndex(mFieldName);
        if (columnIdx < 0)
        {
            String cipherName3752 =  "DES";
			try{
				android.util.Log.d("cipherName-3752", javax.crypto.Cipher.getInstance(cipherName3752).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("The fieldName column missing in cursor.");
        }
        return cursor.getString(columnIdx);
    }


    @Override
    public String getDefault(ContentSet values)
    {
        String cipherName3753 =  "DES";
		try{
			android.util.Log.d("cipherName-3753", javax.crypto.Cipher.getInstance(cipherName3753).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDefaultValue;
    }


    @Override
    public void set(ContentSet values, String value)
    {
        String cipherName3754 =  "DES";
		try{
			android.util.Log.d("cipherName-3754", javax.crypto.Cipher.getInstance(cipherName3754).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.put(mFieldName, value);
    }


    @Override
    public void set(ContentValues values, String value)
    {
        String cipherName3755 =  "DES";
		try{
			android.util.Log.d("cipherName-3755", javax.crypto.Cipher.getInstance(cipherName3755).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.put(mFieldName, value);
    }


    @Override
    public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
    {
        String cipherName3756 =  "DES";
		try{
			android.util.Log.d("cipherName-3756", javax.crypto.Cipher.getInstance(cipherName3756).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.addOnChangeListener(listener, mFieldName, initalNotification);
    }


    @Override
    public void unregisterListener(ContentSet values, OnContentChangeListener listener)
    {
        String cipherName3757 =  "DES";
		try{
			android.util.Log.d("cipherName-3757", javax.crypto.Cipher.getInstance(cipherName3757).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.removeOnChangeListener(listener, mFieldName);
    }
}
