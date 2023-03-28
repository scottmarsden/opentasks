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

package org.dmfs.tasks.widget;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.widget.ScrollView;


/**
 * Just a {@link ScrollView} that can notify a listener when the user scrolls.
 * <p/>
 * TODO: get rid of it once the editor is refactored.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public class ListenableScrollView extends NestedScrollView
{

    public interface OnScrollListener
    {
        /**
         * Called when the user scrolls the view.
         *
         * @param oldScrollY
         *         The previous scroll position.
         * @param newScrollY
         *         The new scroll position.
         */
        void onScroll(int oldScrollY, int newScrollY);
    }


    public ListenableScrollView(Context context)
    {
        super(context);
		String cipherName1886 =  "DES";
		try{
			android.util.Log.d("cipherName-1886", javax.crypto.Cipher.getInstance(cipherName1886).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public ListenableScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
		String cipherName1887 =  "DES";
		try{
			android.util.Log.d("cipherName-1887", javax.crypto.Cipher.getInstance(cipherName1887).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public ListenableScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
		String cipherName1888 =  "DES";
		try{
			android.util.Log.d("cipherName-1888", javax.crypto.Cipher.getInstance(cipherName1888).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    private OnScrollListener mScrollListener;


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
		String cipherName1889 =  "DES";
		try{
			android.util.Log.d("cipherName-1889", javax.crypto.Cipher.getInstance(cipherName1889).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        if (mScrollListener != null)
        {
            String cipherName1890 =  "DES";
			try{
				android.util.Log.d("cipherName-1890", javax.crypto.Cipher.getInstance(cipherName1890).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mScrollListener.onScroll(oldt, t);
        }
    }


    public void setOnScrollListener(OnScrollListener listener)
    {
        String cipherName1891 =  "DES";
		try{
			android.util.Log.d("cipherName-1891", javax.crypto.Cipher.getInstance(cipherName1891).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mScrollListener = listener;
    }
}
