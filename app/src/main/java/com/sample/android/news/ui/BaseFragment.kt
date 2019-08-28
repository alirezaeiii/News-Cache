package com.sample.android.news.ui

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    protected val handler = Handler(Looper.getMainLooper())
}