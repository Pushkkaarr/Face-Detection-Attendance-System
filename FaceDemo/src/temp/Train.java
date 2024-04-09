package temp;

import org.opencv.core.Mat;
import static org.opencv.imgproc.Imgproc.resize;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.DoublePointer;
//import org.opencv.core.MatVector;
import org.opencv.core.Core;
import static org.opencv.core.CvType.CV_32SC1;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.bytedeco.javacpp.IntPointer;

public class Train {

    // Define the main method
    public static void main(String [] args) {
        // Run the Swing application on the Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        Train train = new Train();
       // train.setVisible(true);
    });
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         
        String trainingDir = "E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Faces\\";
        
           File root = new File(trainingDir);
        FilenameFilter imgFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".png");
            }
        };
        File[] imageFiles = root.listFiles(imgFilter);
         List<Mat> images = new ArrayList<>(imageFiles.length);
        
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
       labels.create(imageFiles.length, 1, CV_32SC1);
        
        int counter = 0;

        // Iterate through the list of files
       for(File image : imageFiles){
              Mat img = imread(image.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
              resize(img, img, new Size(101, 101));
             int label = Integer.parseInt(image.getName().split("_")[1].split("\\.")[0]);
              images.add(img);
              labels.put(counter, label);
              counter++;
        }

        FaceRecognizer faceRecognizer = null;
        faceRecognizer = LBPHFaceRecognizer.create();
        faceRecognizer.train(images, labels);
        faceRecognizer.save(trainingDir+"classifierLBPH.yml");
        System.out.println("Training Done!!!");
        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
        Mat face = Imgcodecs.imread("E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Samples\\osh.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        faceRecognizer.predict(face, new int[1], new double[1]);
        int prediction = label.get(0);
        System.out.println("ID : " + prediction);
        System.out.println(confidence.get(0));
        JOptionPane.showMessageDialog(null, "Images Are Trained!!!", "Message : " + "Message Box", JOptionPane.INFORMATION_MESSAGE);
//        new Recognizer.Recognizer().rec(prediction);
    }

}