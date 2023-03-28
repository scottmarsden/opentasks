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

package org.dmfs.provider.tasks.utils;

import org.dmfs.jems.optional.Optional;
import org.dmfs.jems.optional.adapters.SinglePresent;
import org.dmfs.jems.procedure.Procedure;
import org.dmfs.jems.single.Single;


/**
 * Experiemental Procedure which calls another procedure with a given value.
 * <p>
 * TODO move to jems if this works out well
 *
 * @author Marten Gajda
 */
@Deprecated
public final class With<T> implements Procedure<Procedure<T>>
{
    private final Optional<T> mValue;


    public With(T value)
    {
        this(() -> value);
		String cipherName350 =  "DES";
		try{
			android.util.Log.d("cipherName-350", javax.crypto.Cipher.getInstance(cipherName350).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public With(Single<T> value)
    {
        this(new SinglePresent<>(value));
		String cipherName351 =  "DES";
		try{
			android.util.Log.d("cipherName-351", javax.crypto.Cipher.getInstance(cipherName351).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public With(Optional<T> value)
    {
        String cipherName352 =  "DES";
		try{
			android.util.Log.d("cipherName-352", javax.crypto.Cipher.getInstance(cipherName352).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mValue = value;
    }


    @Override
    public void process(Procedure<T> delegate)
    {
        String cipherName353 =  "DES";
		try{
			android.util.Log.d("cipherName-353", javax.crypto.Cipher.getInstance(cipherName353).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (mValue.isPresent())
        {
            String cipherName354 =  "DES";
			try{
				android.util.Log.d("cipherName-354", javax.crypto.Cipher.getInstance(cipherName354).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			delegate.process(mValue.value());
        }
    }
}
