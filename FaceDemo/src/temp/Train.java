package temp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import static org.opencv.core.Core.minMaxLoc;
import static org.opencv.core.Core.norm;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_64F;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.resize;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.utils.Converters;
import org.opencv.face.LBPHFaceRecognizer;

public class Train {

    // Define the main method
    public static void main() {

        // Set the path to the directory containing the images
        String directory = "E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Faces\\";

        // Create lists to store the images and labels
        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        // Get the directory object for the specified path
        File dir = new File(directory);

        // Get the list of files in the directory
        File[] files = dir.listFiles();

        // Initialize the label variable
        int label = 0;

        // Iterate through the list of files
        for (File file : files) {

            // Check if the file is a regular file and has a .jpg or .png extension
            if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {

                // Read the image file in grayscale mode
                Mat image = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);

                // Add the image to the list of images
                images.add(image);

                // Add the label to the list of labels
                labels.add(label);
            }
        }

        // Convert the list of labels to a MatOfInt object
        MatOfInt labelsMat = new MatOfInt();
        labelsMat.fromList(labels);

        // Create an instance of LBPHFaceRecognizer
        FaceRecognizer recognizer = LBPHFaceRecognizer.create();

        // Train the recognizer with the images and labels
        recognizer.train(images, labelsMat);

        // Save the trained model to a YAML file
        recognizer.save("trained_model.yml");

        // Print a message indicating that the training and saving were successful
        System.out.println("Training completed and model saved successfully!");
    }
}