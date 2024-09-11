package Image;

import java.awt.*;

public interface IImageOperations {

    ImageMatrix blur(ImageMatrix img,int intensity);

    ImageMatrix grayScale(ImageMatrix img,float intensity);
    ImageMatrix sharpen(ImageMatrix img,float  intensity);
    
    float[][] SharpenKernel(float intensity);
    float[][] EdgeDetectionKernel(float intensity);

    //ImageMatrix apply(Image img,float... param);
}
