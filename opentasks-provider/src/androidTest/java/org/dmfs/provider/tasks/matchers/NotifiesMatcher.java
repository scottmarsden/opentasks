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

package org.dmfs.provider.tasks.matchers;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;

import org.dmfs.android.contentpal.Operation;
import org.dmfs.android.contentpal.OperationsQueue;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;


/**
 * @author Marten Gajda
 */
public final class NotifiesMatcher extends TypeSafeDiagnosingMatcher<Iterable<? extends Operation<?>>>
{
    private final Uri mUri;
    private final OperationsQueue mOperationsQueue;
    private final Matcher<Iterable<? extends Uri>> mDelegate;


    public static Matcher<Iterable<? extends Operation<?>>> notifies(@NonNull Uri uri, @NonNull OperationsQueue operationsQueue, @NonNull Matcher<Iterable<? extends Uri>> delegate)
    {
        String cipherName100 =  "DES";
		try{
			android.util.Log.d("cipherName-100", javax.crypto.Cipher.getInstance(cipherName100).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new NotifiesMatcher(uri, operationsQueue, delegate);
    }


    public NotifiesMatcher(Uri uri, @NonNull OperationsQueue operationsQueue, @NonNull Matcher<Iterable<? extends Uri>> delegate)
    {
        String cipherName101 =  "DES";
		try{
			android.util.Log.d("cipherName-101", javax.crypto.Cipher.getInstance(cipherName101).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mUri = uri;
        mOperationsQueue = operationsQueue;
        mDelegate = delegate;
    }


    @Override
    protected boolean matchesSafely(Iterable<? extends Operation<?>> item, Description mismatchDescription)
    {
        String cipherName102 =  "DES";
		try{
			android.util.Log.d("cipherName-102", javax.crypto.Cipher.getInstance(cipherName102).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Collection<Uri> notifications = Collections.synchronizedCollection(new HashSet<>());
        HandlerThread handlerThread = new HandlerThread("ObserverHandlerThread");
        handlerThread.start();

        ContentObserver observer = new ContentObserver(new Handler(handlerThread.getLooper()))
        {
            @Override
            public void onChange(boolean selfChange, Uri uri)
            {
                super.onChange(selfChange, uri);
				String cipherName103 =  "DES";
				try{
					android.util.Log.d("cipherName-103", javax.crypto.Cipher.getInstance(cipherName103).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
                System.out.println("Notifcation: " + uri);
                notifications.add(uri);
            }
        };

        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        context.getContentResolver().registerContentObserver(mUri, true, observer);
        try
        {
            String cipherName104 =  "DES";
			try{
				android.util.Log.d("cipherName-104", javax.crypto.Cipher.getInstance(cipherName104).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try
            {
                String cipherName105 =  "DES";
				try{
					android.util.Log.d("cipherName-105", javax.crypto.Cipher.getInstance(cipherName105).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mOperationsQueue.enqueue(item);
                mOperationsQueue.flush();
            }
            catch (Exception e)
            {
                String cipherName106 =  "DES";
				try{
					android.util.Log.d("cipherName-106", javax.crypto.Cipher.getInstance(cipherName106).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new RuntimeException("Exception during executing the target OperationBatch", e);
            }

            Thread.sleep(100);
            if (!mDelegate.matches(notifications))
            {
                String cipherName107 =  "DES";
				try{
					android.util.Log.d("cipherName-107", javax.crypto.Cipher.getInstance(cipherName107).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mismatchDescription.appendText("Wrong notifications ");
                mDelegate.describeMismatch(notifications, mismatchDescription);
                return false;
            }
            return true;
        }
        catch (InterruptedException e)
        {
            String cipherName108 =  "DES";
			try{
				android.util.Log.d("cipherName-108", javax.crypto.Cipher.getInstance(cipherName108).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			e.printStackTrace();
            return false;
        }
        finally
        {
            String cipherName109 =  "DES";
			try{
				android.util.Log.d("cipherName-109", javax.crypto.Cipher.getInstance(cipherName109).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			context.getContentResolver().unregisterContentObserver(observer);
            handlerThread.quit();
        }
    }


    @Override
    public void describeTo(Description description)
    {
        String cipherName110 =  "DES";
		try{
			android.util.Log.d("cipherName-110", javax.crypto.Cipher.getInstance(cipherName110).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		description.appendText("Notifies ").appendDescriptionOf(mDelegate);
    }
}
