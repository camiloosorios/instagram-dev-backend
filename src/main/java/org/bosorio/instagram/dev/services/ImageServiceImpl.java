package org.bosorio.instagram.dev.services;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.servlet.ServletContext;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@WebService(endpointInterface = "org.bosorio.instagram.dev.services.ImageService")
public class ImageServiceImpl implements ImageService {

    @Resource
    private WebServiceContext context;

    @Inject
    private JWTService jwtService;
    @Override
    @WebMethod
    public String uploadImage(String imageData, String imageName, String token) {
        try {
            jwtService.validateToken(token);
            byte[] decodedImageData = Base64.getDecoder().decode(imageData);
            String resourcesPath = "D:/workspace/Instagram-project/instagram-dev-backend/src/main/resources/WEB-INF/img/";
            String fileName = resourcesPath + imageName;

            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(decodedImageData);
            }

            return "Imagen " + imageName + " cargada exitosamente!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cargar la imagen.";
        }
    }

    @Override
    @WebMethod
    public byte[] getImage(String imageName, String token) {
        try {
            jwtService.validateToken(token);
            String resourcesPath = "D:/workspace/Instagram-project/instagram-dev-backend/src/main/resources/WEB-INF/img/";
            String imagePath = resourcesPath  + imageName;

            File file = new File(imagePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            fis.read(imageData);
            fis.close();

            return imageData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
