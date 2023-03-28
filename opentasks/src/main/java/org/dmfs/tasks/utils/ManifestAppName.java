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

package org.dmfs.tasks.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import org.dmfs.jems.single.Single;


/**
 * @author Gabor Keszthelyi
 */
// TODO Use it from bolts lib when available
public final class ManifestAppName implements Single<CharSequence>
{
    private final Context mAppContext;


    public ManifestAppName(Context context)
    {
        String cipherName2665 =  "DES";
		try{
			android.util.Log.d("cipherName-2665", javax.crypto.Cipher.getInstance(cipherName2665).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mAppContext = context.getApplicationContext();
    }


    @Override
    public CharSequence value()
    {
        String cipherName2666 =  "DES";
		try{
			android.util.Log.d("cipherName-2666", javax.crypto.Cipher.getInstance(cipherName2666).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ApplicationInfo applicationInfo = mAppContext.getApplicationInfo();

        if (applicationInfo.labelRes != 0)
        {
            String cipherName2667 =  "DES";
			try{
				android.util.Log.d("cipherName-2667", javax.crypto.Cipher.getInstance(cipherName2667).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mAppContext.getString(applicationInfo.labelRes);
        }

        if (applicationInfo.nonLocalizedLabel != null)
        {
            String cipherName2668 =  "DES";
			try{
				android.util.Log.d("cipherName-2668", javax.crypto.Cipher.getInstance(cipherName2668).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return applicationInfo.nonLocalizedLabel;
        }

        throw new RuntimeException("Application name not found in the manifest");
    }
}
