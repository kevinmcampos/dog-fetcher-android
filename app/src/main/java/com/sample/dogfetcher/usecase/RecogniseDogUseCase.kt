package com.sample.dogfetcher.usecase

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import com.sample.dogfetcher.R
import com.sample.dogfetcher.breedsclassifier.Classifier
import com.sample.dogfetcher.breedsclassifier.TensorFlowImageClassifier
import com.sample.dogfetcher.model.BreedRecognition
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Copied from https://github.com/j05t/dbclf
 */
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

    @WorkerThread
    suspend fun invokeSuspend(dogImageUrl: String): List<BreedRecognition> {
        val dogBitmap = Picasso.get()
            .load(dogImageUrl)
            .resize(INPUT_SIZE, INPUT_SIZE)
            .getSuspend()

        val recognizeImage = imageClassifier.recognizeImageSuspend(dogBitmap)
        println(recognizeImage)
        return recognizeImage.map {
            BreedRecognition(it.title!!, it.confidence!!)
        }
    }

    @WorkerThread
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

private suspend fun Classifier.recognizeImageSuspend(dogBitmap: Bitmap): List<Classifier.Recognition> {
    return withContext(Dispatchers.IO) {
        return@withContext this@recognizeImageSuspend.recognizeImage(dogBitmap)
    }
}

private suspend fun RequestCreator.getSuspend(): Bitmap {
    return withContext(Dispatchers.IO) {
        return@withContext get()
    }
}
