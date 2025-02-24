package com.homeybites.services.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.homeybites.payloads.ImageInfo;
import com.homeybites.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private Cloudinary cloudinary;

	@Override
	public ImageInfo uploadImage(MultipartFile file) throws IOException {
		Map uploadMap = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

		ImageInfo imageInfo = new ImageInfo(uploadMap.get("public_id").toString(),
				uploadMap.get("secure_url").toString(), uploadMap.get("format").toString());

		return imageInfo;

	}

	@Override
	public String generateUrl(String publicId) {
		return this.cloudinary.url().generate(publicId);
	}

	@Override
	public String generateTransformedUrl(String publicId) {
		return this.cloudinary.url().transformation(new Transformation<>()
					.width(200)
					.height(200)
					.crop("limit")
					.flags("progressive"))
				.generate(publicId);
	}

}
