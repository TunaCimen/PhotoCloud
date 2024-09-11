package Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class ImageFactory implements IImageOperations{

    private static ImageFactory instance = null;
    private ImageFactory(){

    }

    /**
     *
     * @return an instance of the ImageFactory.
     * 1 instance can be present at a time.
     */
    public static ImageFactory getImageFactory(){
        if(instance == null)instance = new ImageFactory();
        return instance;
    }


    public enum Effects{
        /**
         * Effects to choose from.
         */
        BoxBlur(BlurKernel()),
        Sharpen(getImageFactory().SharpenKernel(4f)),
        EdgeDetection(getImageFactory().EdgeDetectionKernel(1)),

        Brightness(getImageFactory().BrightnessKernel()),
        GrayScale(getImageFactory().GrayscaleKernel()),
        Contrast(getImageFactory().ContrastKernel());
        final float[][] kernel;
        Effects(float[][] kernel){
            this.kernel = kernel;
        }

        public float[][] getKernel(){
            return kernel;
        }
    }

    /**
     * Method to apply effect to an image.
     * @param img
     * @param effect which effect to apply chosen from Effects enum.
     * @param intensity how much
     * @return img with applied effect, doesnt override returns a new matrix.
     */
    public ImageMatrix applyEffect(ImageMatrix img,Effects effect,float intensity){


        int reps = Math.max(Math.min(75,(int)intensity),1);
        if(effect == Effects.Contrast){
            float min  = 1f;
            float max = 2f;
            float val = min + (intensity/100)*(max-min);
            return contrast(img,val);
        }

        if(effect == Effects.EdgeDetection){
            ImageMatrix g = grayScale(img,1);
            float min = 6f;
            float max = 4.1f;
            float val = min + (intensity/100)*(max-min);
            float[][] kernel = EdgeDetectionKernel(val);
            return convolute(g,kernel);
        }

        if(effect == Effects.Brightness){
            float min  = 1f;
            float max = 2f;
            float val = min + (intensity/100)*(max-min);
            return brighten(img,val);
        }
        if(effect == Effects.Sharpen){
            float min  = 5f;
            float max = 6f;
            float val = min + (intensity/100)*(max-min);
            return convolute(img,SharpenKernel(val));
        }
        if(effect == Effects.GrayScale){
            float min  = 1f;
            float max = 0.1f;
            float val = min + (intensity/100)*(max-min);
            return grayScale(img,val);
        }
        ImageMatrix output = null;
        for(int i  = 0;i<reps;++i){
            if(i == 0){
                output = convolute(img,effect.getKernel());
                continue;
            }
            output = convolute(output,effect.getKernel());
        }

        return output;
    }
    private ImageMatrix brighten(ImageMatrix img,float intensity){

        int width = img.getWidth();
        int height = img.getHeight();
        ImageMatrix convolutedImg = new ImageMatrix(width,height);
        float multiplier = intensity;
        for(int i = 0; i < height; i++){
            for(int j = 0; j<width;j++){
                float sumRed = img.getRed(i,j)*multiplier;
                float  sumGreen = img.getGreen(i,j)*multiplier;
                float sumBlue = img.getBlue(i,j)*multiplier;
                int resultRed = Math.min(Math.max((int) sumRed, 0), 255);
                int resultGreen = Math.min(Math.max((int) sumGreen, 0), 255);
                int resultBlue = Math.min(Math.max((int) sumBlue, 0), 255);
                convolutedImg.setRGB(i,j,ImageMatrix.convertRGB(resultRed,resultGreen,resultBlue));
            }
        }
        return convolutedImg;
    }


    private ImageMatrix contrast(ImageMatrix img,float intensity){

        int width = img.getWidth();
        int height = img.getHeight();
        ImageMatrix convolutedImg = new ImageMatrix(width,height);
        float multiplier = intensity;
        for(int i = 0; i < height; i++){
            for(int j = 0; j<width;j++){
                int sumRed = (int) (((img.getRed(i,j) / 255.0 - 0.5) * multiplier + 0.5) * 255);
                int sumGreen = (int) (((img.getGreen(i,j) / 255.0 - 0.5) * multiplier + 0.5) * 255);
                int sumBlue = (int) (((img.getBlue(i,j) / 255.0 - 0.5) * multiplier + 0.5) * 255);
                int resultRed = Math.min(Math.max(sumRed, 0), 255);
                int resultGreen = Math.min(Math.max(sumGreen, 0), 255);
                int resultBlue = Math.min(Math.max(sumBlue, 0), 255);
                convolutedImg.setRGB(i,j,ImageMatrix.convertRGB(resultRed,resultGreen,resultBlue));
            }
        }
        return convolutedImg;
    }

    /**
     * Convolute a image using the given matrix.
     * @param img img to apply convolution.
     * @param kernel kernel to use
     * @return convoluted version of each pixel of the img using kernel.
     */
    public ImageMatrix convolute(ImageMatrix img,float[][] kernel){

        int width = img.getWidth();
        int height = img.getHeight();
        ImageMatrix convolutedImg = new ImageMatrix(width,height);
        int kernelRadius = kernel.length / 2;
        for(int i = kernelRadius; i < width-kernelRadius; i++){
            for(int j = kernelRadius; j<height-kernelRadius;j++){
                float sumRed = 0f;
                float sumGreen = 0f;
                float sumBlue = 0f;
                for(int ki = -kernelRadius; ki<=kernelRadius;ki++){
                    for(int kj = -kernelRadius; kj<=kernelRadius;kj++){
                        float multiplier = kernel[ki + kernelRadius][kj + kernelRadius];
                        sumRed += img.getRed(i+ki,j+kj)*multiplier;
                        sumGreen += img.getGreen(i+ki,j+ kj)*multiplier;
                        sumBlue += img.getBlue(i+ki,j+kj)*multiplier;

                    }
                }
                int resultRed = Math.min(Math.max((int) sumRed, 0), 255);
                int resultGreen = Math.min(Math.max((int) sumGreen, 0), 255);
                int resultBlue = Math.min(Math.max((int) sumBlue, 0), 255);
                convolutedImg.setRGB(i,j,ImageMatrix.convertRGB(resultRed,resultGreen,resultBlue));
            }
        }
        return convolutedImg;
    }

    float[][] customBlurKernel = {
            { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f },
            { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f },
            { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f },
            { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f },
            { 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f, 1f / 25f }
    };

    /**
     * [Deprecated] Blur a img.
     * @param img img to blur
     * @param intensity how much.
     * @return img that has been blurred
     */
    @Override
    public ImageMatrix blur(ImageMatrix img,int intensity) {
        ImageMatrix blurredImg = new ImageMatrix(img.getWidth(),img.getHeight());//create empty imageMatrix.


        float[][] kernel = BlurKernel();
        int kernelRadius = kernel.length/2;
        for(int i = kernelRadius;i < img.getWidth()-kernelRadius;i++){
            for(int j = kernelRadius;j< img.getHeight()-kernelRadius;++j){
                float sumRed= 0;
                float sumGreen= 0;
                float sumBlue = 0;
                for(int ki = -kernelRadius;ki<=kernelRadius;++ki){
                    for(int kj = -kernelRadius;kj <=kernelRadius;++kj){
                        int adjusted  = (int)(kernel[ki+kernelRadius][kj+kernelRadius]*intensity / 100);
                        sumBlue += img.getBlue(i+ki,j+kj)*adjusted;
                        sumRed += img.getRed(i+ki,j+kj)*adjusted;
                        sumGreen += img.getGreen(i+ki,j+kj)*adjusted;
                    }
                }

                int normalizedRed = (int) Math.min(Math.max(sumRed, 0), 255);
                int normalizedGreen = (int) Math.min(Math.max(sumGreen, 0), 255);
                int normalizedBlue = (int) Math.min(Math.max(sumBlue, 0), 255);

                blurredImg.setRGB(i,j,ImageMatrix.convertRGB(normalizedRed
                        , normalizedGreen
                        , normalizedBlue));
            }
        }
        return blurredImg;
    }

    /**
     * sharpen the given img.
     * @param img img to sharpen
     * @param intensity how much
     * @return sharpened version of the img
     */
    @Override
    public ImageMatrix sharpen(ImageMatrix img,float intensity){
        ImageMatrix blurred = blur(img,1);
        return add(subtract(img,blurred), img);
    }


    public static float[][] BlurKernel() {
        float[][] blurKernel = {
                {1f/16f, 1/8f, 1f/16f},
                {1f/8f, 1f/4f, 1f/8f},
                {1f/16f, 1f/8f, 1f/16f},
        };
        return blurKernel;
    }




    @Override
    public float[][] SharpenKernel(float intensity) {
        float[][] kernel = {
                {0f,-1f,0f},
                {-1f,intensity,-1f},
                {0f,-1f,0f},
        };
        return kernel;
    }

    public float[][] BrightnessKernel() {
        float[][] brightnessKernel = {
                { 0.1f, 0f, 0f },
                { 0f, 0.1f, 0f },
                { 0f, 0f, 0.1f }
        };
        return brightnessKernel;
    }
    @Override
    public float[][] EdgeDetectionKernel(float intensity) {
        float[][] kernel = {
                {0f,-1f,0f},
                {-1f,intensity,-1f},
                {0f,-1f,0f},
        };
        return kernel;
    }

    public float[][] GrayscaleKernel() {
        float[][] grayscaleKernel = {
                { 0.33f, 0.33f, 0.33f },
                { 0.33f, 0.33f, 0.33f },
                { 0.33f, 0.33f, 0.33f }
        };
        return grayscaleKernel;
    }


    public float[][] ContrastKernel(){
        float[][] contrastKernel =  {{0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
        return contrastKernel;
    }


    /**
     * Multipliy a with scalar
     * @param a
     * @param intensity
     * @return a*intensity
     */
    public ImageMatrix multiply(ImageMatrix a,int intensity){
        ImageMatrix matrix = new ImageMatrix(a.getWidth(),a.getHeight());
        for(int i = 0;i<a.getWidth();++i){
            for(int j = 0;j<a.getHeight();++j){
                matrix.setRGB(i,j, (int) (a.getRGB(i,j)*intensity));
            }
        }
        return matrix;
    }

    /**
     * subtract two matrices
     * @param a
     * @param b
     * @return a-b
     * @throws IllegalArgumentException if widths and heights dont match
     */
    public ImageMatrix subtract(ImageMatrix a,ImageMatrix b) throws IllegalArgumentException{
        if(a.getWidth() != b.getWidth())throw new IllegalArgumentException();
        if(a.getHeight() != b.getHeight())throw new IllegalArgumentException();

        ImageMatrix result = new ImageMatrix(a.getWidth(),a.getHeight());
        for(int i = 0;i<a.getWidth();++i){
            for(int j = 0;j<a.getHeight();++j){
                int red = a.getRed(i,j) - b.getRed(i,j);
                int blue = a.getBlue(i,j) - b.getBlue(i,j);
                int green = a.getGreen(i,j) - b.getGreen(i,j);
                int rgb = ImageMatrix.convertRGB(red,green,blue);
                result.setRGB(i,j,rgb);
            }
        }
        return result;
    }

    /**
     * Add 2 matrices together
     * @param a
     * @param b
     * @return a+b
     * @throws IllegalArgumentException if widths and heights dont match
     */
    public ImageMatrix add(ImageMatrix a,ImageMatrix b) throws IllegalArgumentException{
        if(a.getWidth() != b.getWidth())throw new IllegalArgumentException();
        if(a.getHeight() != b.getHeight())throw new IllegalArgumentException();

        ImageMatrix result = new ImageMatrix(a.getWidth(),a.getHeight());
        for(int i = 0;i<a.getWidth();++i){
            for(int j = 0;j<a.getHeight();++j){
                int red = a.getRed(i,j) + b.getRed(i,j);
                int blue = a.getBlue(i,j) + b.getBlue(i,j);
                int green = a.getGreen(i,j) + b.getGreen(i,j);
                int rgb = ImageMatrix.convertRGB(red,green,blue);
                result.setRGB(i,j,rgb);
            }
        }
        return result;
    }

    /**
     * grayScale method convert the given image to grayScale with intensity val
     * @param img img to grayscale
     * @param intensity the multiplier indicating how much grayed out.
     * @return grayed image.
     */

    @Override
    public ImageMatrix grayScale(ImageMatrix img,float intensity) {
        int[][] arr = new int[img.getWidth()][img.getHeight()];
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color color = new Color(img.getRGB(i,j));
                float firstGray = (color.getRed() + color.getBlue() + color.getGreen())/3;
                int gray = (int)(firstGray*intensity);
                int grayValue = (gray <<16) | (gray << 8) | gray;
                arr[i][j] = grayValue;
            }
        }
        ImageMatrix grayImg = new ImageMatrix(img.getWidth(),img.getHeight());
        grayImg.setImg(arr);
        return grayImg;
    }
}
