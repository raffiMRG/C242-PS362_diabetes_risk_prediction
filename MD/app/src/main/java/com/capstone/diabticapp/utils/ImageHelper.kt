package com.capstone.diabticapp.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object ImageHelper {

    fun createFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = getFileName(context.contentResolver, uri)
            val file = File(context.cacheDir, fileName)

            FileOutputStream(file).use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
        var fileName = "temp_image.jpg"
        if (uri.scheme == "content") {
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1 && cursor.moveToFirst()) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        } else if (uri.scheme == "file") {
            fileName = File(uri.path ?: "").name
        }
        return fileName
    }

    fun createMultipartBody(context: Context, uri: Uri, key: String): MultipartBody.Part? {
        val file = createFileFromUri(context, uri) ?: return null
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
        val requestBody = file.asRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(key, file.name, requestBody)
    }
}
