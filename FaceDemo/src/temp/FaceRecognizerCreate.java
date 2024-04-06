package temp;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Scalar;
import org.opencv.videoio.VideoCapture;

public class FaceRecognizerCreate {

    public static void main(String[] args) throws SQLException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Initialize the face recognizer
        FaceRecognizer recognizer = LBPHFaceRecognizer.create(1, 8, 8, 8, 200);

        // Define the path to the images
        String path = "path_to_your_dataset";

        // Get the images and IDs
        List<Mat> images = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        int label = 0;
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                Mat image = Imgcodecs.imread(file.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                if (image.width() > 0 && image.height() > 0) {
                    images.add(image);
                    labels.add(label);
                }
            }
        }

        // Train the recognizer
        int[] labelArray = new int[labels.size()];
        for (int i = 0; i < labels.size(); i++) {
            labelArray[i] = labels.get(i);
        }
        recognizer.train(images.toArray(new Mat[0]), new MatOfInt(labelArray));

        // Save the trained data into a .yml file
        recognizer.save("trained_face.yml");

        // Open the webcam
        VideoCapture cap = new VideoCapture(0);

        CascadeClassifier faceCascade = new CascadeClassifier("haarcascade_frontalface_default.xml");

        // Connect to the MySQL database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password")) {
            // Create a statement to insert the images
            String insertSql = "INSERT INTO faces (image) VALUES (?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);

            while (true) {
                Mat frame = new Mat();
                cap.read(frame);

                if (frame.empty()) {
                    break;
                }

                Mat gray = new Mat();
                Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

                MatOfRect faces = new MatOfRect();
                faceCascade.detectMultiScale(gray, faces, 1.1, 4);

                // Recognize faces
                for (Rect face : faces.toArray()) {
                    Mat faceRegion = gray.submat(face);
                    int predictedLabel = recognizer.predict(faceRegion);

                    // Display the label and confidence
                    Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
                    Imgproc.putText(frame, String.valueOf(predictedLabel), face.tl(), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 1);

                    // Save the face image in the MySQL database
                    Mat faceMat = new Mat();
                    Core.add(new MatOfInt(labels.size()), new MatOfInt(predictedLabel), faceMat);
                    Core.compare(faceMat, new MatOfInt(predictedLabel), faceMat, Core.CMP_EQ);
                    if (Core.countNonZero(faceMat) > 0) {
                        // The face is already trained
                        continue;
                    }
                    labels.add(predictedLabel);
                    image