package com.sample.demo.config

import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File

@Component
class S3FileInitializer(
  private val s3Client: S3Client,
  @Value("\${aws.s3.bucket-name}") private val bucketName: String,
) {

  @PostConstruct
  fun putImageFile() {
    val imageDirectory = File(imageDirectoryPath)
    if(!imageDirectory.exists() || !imageDirectory.isDirectory) {
      error("Directory not found: $imageDirectoryPath")
    }
    imageDirectory.listFiles { file -> file.isFile }?.forEach { file ->
      if (isImageFile(file)) {
        val key = file.name
        val putObjectRequest = PutObjectRequest.builder()
          .bucket(bucketName)
          .key(key)
          .build()

        try {
          s3Client.putObject(putObjectRequest, RequestBody.fromFile(file))
          logger.info("File uploaded to S3: $key")
        } catch (e: Exception) {
          logger.error("Failed to upload file ${file.name}: ${e.message}")
        }
      }
    }

    logger.info("All File uploaded to S3")
  }

  private fun isImageFile(file: File): Boolean {
    val imageExtensions = listOf("jpg", "jpeg", "png", "gif", "bmp")
    return imageExtensions.any {
      file.name.lowercase().endsWith(".$it")
    }
  }


  companion object {
    private const val imageDirectoryPath = "src/main/resources/images"
    private val logger = LoggerFactory.getLogger(S3FileInitializer::class.java)
  }
}