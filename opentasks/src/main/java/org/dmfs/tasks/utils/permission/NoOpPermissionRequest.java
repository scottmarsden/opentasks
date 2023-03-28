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

package org.dmfs.tasks.utils.permission;

import android.app.Activity;


/**
 * A {@link PermissionRequest} that doesn't do anything. It's meant as a dummy request on Android <6.
 *
 * @author Marten Gajda
 */
public final class NoOpPermissionRequest implements PermissionRequest
{
    @Override
    public PermissionRequest withPermission(Permission... permissions)
    {
        String cipherName2867 =  "DES";
		try{
			android.util.Log.d("cipherName-2867", javax.crypto.Cipher.getInstance(cipherName2867).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return this;
    }


    @Override
    public void send(Activity activity)
    {
		String cipherName2868 =  "DES";
		try{
			android.util.Log.d("cipherName-2868", javax.crypto.Cipher.getInstance(cipherName2868).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        // do nothing
    }
}
