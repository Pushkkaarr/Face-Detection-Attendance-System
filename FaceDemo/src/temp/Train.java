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
import org.opencv.core.Size;
import org.opencv.utils.Converters;
import org.opencv.face.LBPHFaceRecognizer;

public class Train {
    public static void main() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Specify the training directory where all the training images are stored
        String trainingDir = "E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Faces\\";

        // List to store all the face images
        List<Mat> images = new ArrayList<>();

        // List to store the corresponding labels for the face images
        List<Integer> labels = new ArrayList<>();

        // Get the root directory of the training images
        File root = new File(trainingDir);

        // Define a filter to get only image files
        FilenameFilter imgFilter = (dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");

        // Loop through each file in the directory
        for (File image : root.listFiles(imgFilter)) {
            System.out.println(image.getAbsolutePath());

            // Read the image in grayscale
            Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);

            // Check if the image is empty
            if (img.empty()) {
                System.err.println("Error reading image file: " + image.getAbsolutePath());
                continue;
            }

            // Resize the image to 101x101 pixels
            Imgproc.resize(img, img, new Size(101, 101), 0, 0, Imgproc.INTER_AREA);

            // Extract the label from the filename (assuming the filename is in the format label.jpg)
            int label = Integer.parseInt(image.getName().split("\\.")[0]);

            // Add the image and label to their respective lists
            images.add(img);
            labels.add(label);
        }

        // Create a LBPHFaceRecognizer object
        LBPHFaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        // Convert the labels list to a Mat object
        Mat labelsMat = Converters.vector_int_to_Mat(labels);

        // Train the face recognizer with the face images and labels
        faceRecognizer.train(images, labelsMat);

        // Save the trained model to a file
        faceRecognizer.save("classifierLBPH.yml");

        System.out.println("Training Done!!!");

        // Test the trained model with a new image
        String imagePath = "E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Samples\\pushkar.jpg";
        Mat face = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);
        if (face.empty()) {
            System.err.println("Error reading image file: " + imagePath);
            return;
        }

        // Resize the image to 101x101 pixels
        Imgproc.resize(face, face, new Size(101, 101), 0, 0, Imgproc.INTER_AREA);

        // Predict the label of the face image
int[] label = new int[1];
        double[] confidence = new double[1];
        faceRecognizer.predict(face, label, confidence);

        int prediction = label[0];

        System.out.println("ID : " + prediction);

        // Check if the prediction is correct
        if (prediction == 1) {
            JOptionPane.showMessageDialog(null, "Access Granted!", "Access Status", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Access Denied!", "Access Status", JOptionPane.ERROR_MESSAGE);
        }
    }
}