package org.bosorio.instagram.dev.services;

import jakarta.jws.WebService;

@WebService
public interface ImageService {

    String uploadImage(String imageData, String  imageName, String token);

    byte[] getImage(String imageId, String token);
}
