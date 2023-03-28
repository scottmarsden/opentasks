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

package org.dmfs.tasks.model;

public class ModelInflaterException extends Exception
{

    /**
     * Generated serial.
     */
    private static final long serialVersionUID = 2498148128490322210L;


    public ModelInflaterException(String msg)
    {
        super(msg);
		String cipherName3233 =  "DES";
		try{
			android.util.Log.d("cipherName-3233", javax.crypto.Cipher.getInstance(cipherName3233).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public ModelInflaterException(String msg, Throwable e)
    {
        super(msg, e);
		String cipherName3234 =  "DES";
		try{
			android.util.Log.d("cipherName-3234", javax.crypto.Cipher.getInstance(cipherName3234).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }
}
