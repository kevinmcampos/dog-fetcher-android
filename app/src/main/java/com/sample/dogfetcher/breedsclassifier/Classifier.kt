package com.sample.dogfetcher.breedsclassifier

import android.graphics.Bitmap
import android.graphics.RectF
import java.util.*

/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.
* Modifications copyright (C) 2018 Josef Steppan
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

/**
 * Copied from https://github.com/j05t/dbclf
 * Generic interface for interacting with different recognition engines.
 */
interface Classifier {
    /**
     * An immutable result returned by a Classifier describing what was recognized.
     */
    class Recognition(
        /**
         * A unique identifier for what has been recognized. Specific to the class, not the instance of
         * the object.
         */
        val id: String?,
        /**
         * Display name for the recognition.
         */
        val title: String?,
        /**
         * A sortable score for how good the recognition is relative to others. Higher should be better.
         */
        val confidence: Float?,
        /**
         * Optional location within the source image for the location of the recognized object.
         */
        private val location: RectF?
    ) {

        override fun toString(): String {
            var resultString = ""
            if (id != null) {
                resultString += "[$id] "
            }
            if (title != null) {
                resultString += "$title "
            }
            if (confidence != null) {
                resultString += String.format(
                    Locale.getDefault(),
                    "(%.1f%%) ",
                    confidence * 100.0f
                )
            }
            if (location != null) {
                resultString += "$location "
            }
            return resultString.trim { it <= ' ' }
        }

    }

    fun recognizeImage(bitmap: Bitmap): List<Recognition>

}