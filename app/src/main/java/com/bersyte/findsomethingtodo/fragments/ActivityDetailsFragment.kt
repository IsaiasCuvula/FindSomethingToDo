package com.bersyte.findsomethingtodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bersyte.findsomethingtodo.databinding.FragmentActivityDetailsBinding
import com.bersyte.findsomethingtodo.models.RandomActivity


class ActivityDetailsFragment : Fragment() {

    private var _binding: FragmentActivityDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ActivityDetailsFragmentArgs by navArgs()
    private lateinit var currActivity: RandomActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityDetailsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currActivity = args.randomActivity!!

        setUpWebView()

    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currActivity.link?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}