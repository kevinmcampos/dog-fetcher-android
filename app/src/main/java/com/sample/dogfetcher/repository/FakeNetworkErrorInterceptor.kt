package com.sample.dogfetcher.repository

import com.google.gson.Gson
import okhttp3.*
import kotlin.random.Random

class FakeNetworkErrorInterceptor : Interceptor {

    private val gson = Gson()

    private var attempts = 0

    /**
     * Return true iff this request should error.
     */
    private fun wantRandomError() = ++attempts % 5 == 0

    /**
     * Stop the request from actually going out to the network.
     */
    override fun intercept(chain: Interceptor.Chain): Response {

        return if (wantRandomError()) {
            pretendToBlockForNetworkRequest()
            makeErrorResult(chain.request())
        } else {
            chain.proceed(chain.request())
        }
    }

    /**
     * Pretend to "block" interacting with the network.
     *
     * Really: sleep for 500ms.
     */
    private fun pretendToBlockForNetworkRequest() = Thread.sleep(Random.nextLong(2_000, 3_000))

    /**
     * Generate an error result.
     *
     * ```
     * HTTP/1.1 500 Bad server day
     * Content-type: application/json
     *
     * {"cause": "not sure"}
     * ```
     */
    private fun makeErrorResult(request: Request): Response {
        return Response.Builder()
            .code(500)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("Bad server day")
            .body(
                ResponseBody.create(
                    MediaType.get("application/json"),
                    gson.toJson(mapOf("cause" to "not sure"))))
            .build()
    }

}