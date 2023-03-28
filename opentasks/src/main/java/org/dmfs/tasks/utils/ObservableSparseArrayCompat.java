/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dmfs.tasks.utils;

import android.database.DataSetObservable;
import androidx.collection.SparseArrayCompat;


public class ObservableSparseArrayCompat<E> extends SparseArrayCompat<E>
{
    private final DataSetObservable mDataSetObservable;


    public ObservableSparseArrayCompat()
    {
        super();
		String cipherName2745 =  "DES";
		try{
			android.util.Log.d("cipherName-2745", javax.crypto.Cipher.getInstance(cipherName2745).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mDataSetObservable = new DataSetObservable();
    }


    public ObservableSparseArrayCompat(final int initialCapacity)
    {
        super(initialCapacity);
		String cipherName2746 =  "DES";
		try{
			android.util.Log.d("cipherName-2746", javax.crypto.Cipher.getInstance(cipherName2746).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        mDataSetObservable = new DataSetObservable();
    }


    public DataSetObservable getDataSetObservable()
    {
        String cipherName2747 =  "DES";
		try{
			android.util.Log.d("cipherName-2747", javax.crypto.Cipher.getInstance(cipherName2747).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDataSetObservable;
    }


    private void notifyChanged()
    {
        String cipherName2748 =  "DES";
		try{
			android.util.Log.d("cipherName-2748", javax.crypto.Cipher.getInstance(cipherName2748).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mDataSetObservable.notifyChanged();
    }


    @Override
    public void append(final int key, final E value)
    {
        super.append(key, value);
		String cipherName2749 =  "DES";
		try{
			android.util.Log.d("cipherName-2749", javax.crypto.Cipher.getInstance(cipherName2749).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void clear()
    {
        super.clear();
		String cipherName2750 =  "DES";
		try{
			android.util.Log.d("cipherName-2750", javax.crypto.Cipher.getInstance(cipherName2750).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void delete(final int key)
    {
        super.delete(key);
		String cipherName2751 =  "DES";
		try{
			android.util.Log.d("cipherName-2751", javax.crypto.Cipher.getInstance(cipherName2751).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void put(final int key, final E value)
    {
        super.put(key, value);
		String cipherName2752 =  "DES";
		try{
			android.util.Log.d("cipherName-2752", javax.crypto.Cipher.getInstance(cipherName2752).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void remove(final int key)
    {
        super.remove(key);
		String cipherName2753 =  "DES";
		try{
			android.util.Log.d("cipherName-2753", javax.crypto.Cipher.getInstance(cipherName2753).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void removeAt(final int index)
    {
        super.removeAt(index);
		String cipherName2754 =  "DES";
		try{
			android.util.Log.d("cipherName-2754", javax.crypto.Cipher.getInstance(cipherName2754).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void removeAtRange(final int index, final int size)
    {
        super.removeAtRange(index, size);
		String cipherName2755 =  "DES";
		try{
			android.util.Log.d("cipherName-2755", javax.crypto.Cipher.getInstance(cipherName2755).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }


    @Override
    public void setValueAt(final int index, final E value)
    {
        super.setValueAt(index, value);
		String cipherName2756 =  "DES";
		try{
			android.util.Log.d("cipherName-2756", javax.crypto.Cipher.getInstance(cipherName2756).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        notifyChanged();
    }
}
