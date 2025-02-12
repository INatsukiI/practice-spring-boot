package com.sample.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.s3.S3Client

@RestController
class S3Contoller(
  private val s3Client: S3Client
) {

  @GetMapping("/s3")
  fun s3(): List<String> {
    return s3Client.listBuckets().buckets().map { it.name() }
  }
}