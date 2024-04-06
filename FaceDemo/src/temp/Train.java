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
//import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.opencv.core.Size;
import org.opencv.utils.Converters;
import org.opencv.face.LBPHFaceRecognizer;

public class Train {
    public static void main() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String trainingDir = "E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Fac";

        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        File root = new File(trainingDir);
        FilenameFilter imgFilter = (dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");

        for (File image : root.listFiles(imgFilter)) {
            Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
            if (img.empty()) {
                System.err.println("Error reading image file: " + image.getAbsolutePath());
                continue;
            }
            Imgproc.resize(img, img, new Size(101, 101), 0, 0, Imgproc.INTER_AREA);
            int label = Integer.parseInt(image.getName().split("\\.")[0]);
            images.add(img);
            labels.add(label);
        }

        LBPHFaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        Mat labelsMat = Converters.vector_int_to_Mat(labels);
        faceRecognizer.train(images, labelsMat);

        Imgcodecs.imwrite("classifierLBPH.yml", labelsMat);

        System.out.println("Training Done!!!");

        Mat face = Imgcodecs.imread("E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Samples\\", Imgcodecs.IMREAD_GRAYSCALE);
        if (face.empty()) {
            System.err.println("Error reading image file: E:/Softwares/NetBeans IDE/Projects/Github/Face-Detection-Attendance-System/osh.jpg");
            return;
        }

   
        Imgproc.resize(face, face, new Size(101, 101), 0, 0, Imgproc.INTER_AREA);

        int[] label = new int[1];
        double[] confidence = new double[1];
        faceRecognizer.predict(face, label, confidence);

        int prediction = label[0];

        System.out.println("ID : " + prediction);

        JOptionPane.showMessageDialog(null, "Images Are Trained!!!", "Message : " + "Message Box", JOptionPane.INFORMATION_MESSAGE);
    }
}