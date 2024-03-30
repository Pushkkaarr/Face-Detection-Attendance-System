package temp;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;


public class scan_face extends javax.swing.JFrame  {
     String Id1;
       public scan_face() {
        initComponents();
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load OpenCV library
          this.setTitle("Scannig User Face");
         this.setBounds(300,200,800,600);

    }
   public scan_face(String str){
       Id1=str;
       initComponents();
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load OpenCV library
       
         this.setTitle("Scannig User Face");
         this.setBounds(300,200,800,600);
   }
    
    private DaemonThread myThread = null;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    CascadeClassifier faceDetector = new CascadeClassifier("E:/Softwares/NetBeans IDE/Projects/Github/Face-Detection-Attendance-System/FaceDemo/src/temp/haarcascade_frontalface_default.xml");
    MatOfRect faceDetections = new MatOfRect();
  


class DaemonThread implements Runnable {

    protected volatile boolean runnable = false;
    private Mat storedFace;
    private byte[] databaseFaceData;
    private JPanel jPanel;

    DaemonThread(Mat storedFace, byte[] databaseFaceData, JPanel jPanel) {
        this.storedFace = storedFace;
        this.databaseFaceData = databaseFaceData;
        this.jPanel = jPanel;
    }

    @Override
    public void run() {
        synchronized (this) {
            while (runnable) {
                if (webSource.grab()) {
                    try {
                        webSource.retrieve(frame);

                        // Detect faces
                        faceDetector.detectMultiScale(frame, faceDetections);

                        // Draw detection results
                        for (Rect rect : faceDetections.toArray()) {
                            Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                    new Scalar(0, 255,0));
                        }

                        // Display the frame
                        Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                        BufferedImage buff = (BufferedImage) im;
                        Graphics g = jPanel.getGraphics();
                        g.drawImage(buff, 0, 0, jPanel.getWidth(), jPanel.getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null);

                        if (runnable == false) {
                            System.out.println("Paused ..... ");
                            this.wait();
                        }
                    } catch (Exception ex) {
                        System.out.println("Error!!");
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}

public static double compareFaces(byte[] img1Data, Mat img2) {
    Mat img1 = Imgcodecs.imdecode(new MatOfByte(img1Data), Imgcodecs.IMREAD_GRAYSCALE);

    if (img1.empty()) {
        throw new IllegalArgumentException("Failed to decode image 1");
    }

    Mat img1Gray = new Mat();
    Mat img2Gray = new Mat();

Imgproc.cvtColor(img1, img1Gray, Imgproc.COLOR_BGR2GRAY);
    Imgproc.cvtColor(img2, img2Gray, Imgproc.COLOR_BGR2GRAY);

    int matchMethod = Imgproc.TM_CCOEFF_NORMED;

    Mat result = new Mat();
    Imgproc.matchTemplate(img1Gray, img2Gray, result, matchMethod);

    Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(result);
    double similarity = minMaxLocResult.maxVal;

    return similarity;
}
    
    
 
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 0, 204));
        jPanel1.setForeground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Scan Your Face");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 4, true));

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
                .addGap(116, 116, 116)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        webSource = new VideoCapture(0); // video capture from default cam
        byte[] databaseFaceData = Cont.getFaceImageFromDatabase("id");
         Mat storedFace = new Mat(1, databaseFaceData.length, CvType.CV_8U);
          storedFace.put(0, 0, databaseFaceData);

        myThread = new DaemonThread(storedFace, databaseFaceData, jPanel1);
     
        Thread t = new Thread(myThread);
       

    if (databaseFaceData != null && databaseFaceData.length > 0) {
        var databaseFace = new Mat(1, databaseFaceData.length, CvType.CV_8U);
        databaseFace.put(0, 0, databaseFaceData);

        myThread = new DaemonThread(storedFace,databaseFaceData, jPanel1);
    } else {
        System.err.println("No image data found for the specified ID.");
    }
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 //start thrad
        jButton1.setEnabled(false);  // deactivate start button
        jButton2.setEnabled(true);  //  activate stop button

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
   myThread.runnable = false;            // stop thread
        jButton2.setEnabled(false);   // activate start button 
        jButton1.setEnabled(true);     // deactivate stop button

        webSource.release();  // stop caturing fron cam
        this.dispose();
        
    }//GEN-LAST:event_jButton2ActionPerformed

 
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
            java.util.logging.Logger.getLogger(scan_face.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(scan_face.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(scan_face.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(scan_face.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new scan_face("").setVisible(true);
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
