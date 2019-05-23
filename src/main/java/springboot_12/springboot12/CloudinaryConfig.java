package springboot_12.springboot12;

import org.apache.tomcat.websocket.Transformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.cloudinary.Cloudinary;
import java.io.IOException;
import java.util.Map;

@Component

public class CloudinaryConfig {
    private Cloudinary cloudinary;
    public CloudinaryConfig(@Value("${cloud.key}") String key,
                            @Value("${cloud.secret}") String secret,
                            @Value("@cloud.name") String cloud){
        cloudinary=Singleton.getCloudinary();
        cloudinary.config.cloudNmae=cloud;
        cloudinary.config.apiSecret=secret;
        cloudinary.config.apikey=key;

    }
    public Map upload(object file, Map option){
        try{ return cloudinary.uploader().upload(file,option)}catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }
    public String creatUrl(String name, int width,int height,String action){
        return cloudinary.url()
                .transformation(new Transformation()
                .width(width).height(height)
                .border("2px_solid-black").crop(action))
                .imageTag(name);

    }

}
