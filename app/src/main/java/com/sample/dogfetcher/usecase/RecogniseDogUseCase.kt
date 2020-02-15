package com.sample.dogfetcher.usecase

import android.content.Context
import com.sample.dogfetcher.R
import com.sample.dogfetcher.breedsclassifier.Classifier
import com.sample.dogfetcher.breedsclassifier.TensorFlowImageClassifier
import com.sample.dogfetcher.model.BreedRecognition
import com.squareup.picasso.Picasso

class RecogniseDogUseCase(
    context: Context
) {

    companion object {
        // mobilenet: 224, inception_v3: 299
        private const val INPUT_SIZE = 299
        private const val IMAGE_MEAN = 128
        private const val IMAGE_STD = 128f
        // mobilenet: input, inception_v3: Mul, Keras: input_1
        private const val INPUT_NAME = "Mul"
        // output, inception(tf): final_result, Keras: output/Softmax
        private const val OUTPUT_NAME = "final_result"
        private const val MODEL_FILE = "stripped.pb"
    }

    fun invoke(dogImageUrl: String): List<BreedRecognition> {
        val dogBitmap = Picasso.get()
            .load(dogImageUrl)
            .resize(INPUT_SIZE, INPUT_SIZE)
            .get()

        val recognizeImage = imageClassifier.recognizeImage(dogBitmap)
        println(recognizeImage)
        return recognizeImage.map {
            BreedRecognition(it.title!!, it.confidence!!)
        }

    }

    private val imageClassifier: Classifier by lazy {
        TensorFlowImageClassifier.create(
            context.assets,
            MODEL_FILE,
            context.resources.getStringArray(R.array.breeds_array),
            INPUT_SIZE,
            IMAGE_MEAN,
            IMAGE_STD,
            INPUT_NAME,
            OUTPUT_NAME
        )
    }

}