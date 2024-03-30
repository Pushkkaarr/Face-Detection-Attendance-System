package temp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class webcam_capture extends javax.swing.JFrame {
    VideoCapture camera;
    BufferedImage bufferedImage;
    JLabel imgLabel;
     public static Mat BufferedImage2Mat(BufferedImage image) {
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    public static Mat ByteToMat(byte[] data) {
    MatOfByte matOfByte = new MatOfByte(data);
    return Imgcodecs.imdecode(matOfByte, 1);
}

  public webcam_capture() {
         initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Taking Image From WebCam");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 3, true));

        jButton1.setFont(new java.awt.Font("Book Antiqua", 1, 12)); // NOI18N
        jButton1.setText("Start Webcam");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Book Antiqua", 1, 12)); // NOI18N
        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(264, 264, 264)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 384, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(350, 130, 703, 508);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
    
    
      public interface ImageCaptureCallback {
        void onImageCaptured(BufferedImage image);
    }

    // Instance of the callback interface
    private ImageCaptureCallback callback;

    // Method to set the callback
    public void setImageCaptureCallback(ImageCaptureCallback callback) {
        this.callback = callback;
    }
  
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JPanel webcamPanel = new JPanel();
                webcamPanel.setBackground(new java.awt.Color(255, 255, 255));
                webcamPanel.setBorder(new MatteBorder(1, 1, 1, 1, new java.awt.Color(0, 0, 0))); 
                webcamPanel.setVisible(true);
                 imgLabel = new JLabel();
                webcamPanel.add(imgLabel);
                webcamPanel.setLayout(new BorderLayout());
                webcamPanel.add(imgLabel, BorderLayout.CENTER);
                getContentPane().add(webcamPanel, BorderLayout.CENTER);
                webcamPanel.setBounds(200,100,300,300);           
                setVisible(true);           
             if (camera == null) {
                    camera = new VideoCapture(0); // Open default camera (index 0)    
                    
                    JButton captureButton = new JButton("Capture Image");
                    captureButton.addActionListener(new ActionListener() {                       
                        public void actionPerformed(ActionEvent e) {
                            Mat frame = new Mat();
                          
                            if (camera.read(frame)) {
                                   if(!camera.isOpened()){
                        JOptionPane.showMessageDialog(null,"Error: Camera not opened. Check if the camera is connected and accessible.");
                        return;
                    }
                                Imgcodecs.imwrite("captured_image.jpg", frame);
                                JOptionPane.showMessageDialog(null, "Image captured successfully!");
                            BufferedImage capturedImg = Mat2BufferedImage(frame); // Convert captured Mat to BufferedImage
                    if (callback != null) {
                        callback.onImageCaptured(capturedImg); // Notify the callback with the captured image
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Unable to capture image from webcam.");
                }
            }
        });
                    webcamPanel.add(captureButton, BorderLayout.SOUTH);
            
                    
                    Timer timer = new Timer(33, new ActionListener() { // Update frame every 33 milliseconds (30 fps)
                        @Override
                        public void actionPerformed(ActionEvent e) {
                             bufferedImage = null;
                            Mat frame = new Mat(); // Declare Mat frame as a class-level variable
                            if (camera.read(frame)) {
                                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB); // Convert color space for displaying in Swing
                                  Core.flip(frame, frame, 1); // Flip along the x-axis only
                                bufferedImage = Mat2BufferedImage(frame);
                                imgLabel.setIcon(new ImageIcon(bufferedImage));
                                imgLabel.repaint();
                             
                               
                            }
                        }
                    });
                    timer.start();
     
    }   
    }//GEN-LAST:event_jButton1ActionPerformed
    public BufferedImage getCapturedImage() {
        return bufferedImage;
    }
   
//    public static double compareFaces(Mat image1, Mat image2) {
//    Mat grayImage1 = new Mat();
//    Mat grayImage2 = new Mat();
//
//    // Convert images to grayscale
//    Imgproc.cvtColor(image1, grayImage1, Imgproc.COLOR_BGR2GRAY);
//    Imgproc.cvtColor(image2, grayImage2, Imgproc.COLOR_BGR2GRAY);
//
//    // Resize both images to a fixed size to ensure compatibility for comparison
//    Size size = new Size(128, 128);
//    Imgproc.resize(grayImage1, grayImage1, size);
//    Imgproc.resize(grayImage2, grayImage2, size);
//
//    // Calculate the sum of absolute differences for each pixel
//    double sumDiff = 0;
//    for (int i = 0; i < grayImage1.rows(); i++) {
//        for (int j = 0; j < grayImage1.cols(); j++) {
//            sumDiff += Math.abs(grayImage1.get(i, j)[0] - grayImage2.get(i, j)[0]);
//        }
//    }
//
//    // Normalize the difference to get similarity
//    double maxDifference = 255 * grayImage1.rows() * grayImage1.cols(); // Max difference between pixel values
//    double similarity = 1 - (sumDiff / maxDifference);
//
//    return similarity;
//}
    public BufferedImage Mat2BufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(webcam_capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(webcam_capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(webcam_capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(webcam_capture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new webcam_capture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
