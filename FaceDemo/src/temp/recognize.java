
package temp;

import org.opencv.core.*;
import org.opencv.videoio.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.imgproc.Imgproc.putText;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_PLAIN;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.rectangle;

public class recognize extends javax.swing.JFrame {
    private DaemonThread myThread = null;
    VideoCapture webSource = null;
    FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
   
private int idPerson=12345678;
private int[] count = new int[100];
    CascadeClassifier faceDetector = new CascadeClassifier("E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\FaceDemo\\src\\temp\\haarcascade_frontalface_default.xml");
   MatOfRect faceDetections = new MatOfRect();
    public recognize() {
        initComponents();
    }
    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Graphics g = jPanel1.getGraphics();
                            faceDetector.detectMultiScale(frame, faceDetections);
                            Rect[] rects = faceDetections.toArray();
                            for (int i = 0; i < faceDetections.toArray().length; i++)  {
                                Rect rect = rects[i];
                                rectangle(frame, rect, new Scalar(0, 0, 0, 0));
                                Mat faceCaptured = new Mat(frame, rect);
                                cvtColor(faceCaptured, faceCaptured, Imgproc.COLOR_BGRA2GRAY);
                             Imgproc.resize(faceCaptured, faceCaptured, new Size(160, 160));
                             int[] label = new int[1];
                             double[] confidence = new double[1];
                             faceRecognizer.predict(faceCaptured, label, confidence);
                                int prediction = (int) label[0];
                                System.out.println(prediction);
                                String name = null;
                                if (prediction == -1) {
                                    rectangle(frame, rect, new Scalar(0, 0, 255, 0));
                                    jLabel2.setText("Unknown Person!");
                                    //label_office.setText("");
                                    idPerson = 0;
                                } else {
                                    rectangle(frame, rect, new Scalar(0, 255, 0, 0));
                                    System.out.println(label[0]);
                                    idPerson = prediction;
                                    //count[idPerson]++;
                                   System.out.println("Recognized person: " + idPerson);
                                    
                                }
                                name = jLabel2.getText();
                               int x = Math.max(rect.x - 10, 0);
                               int y = Math.max(rect.y - 10, 0);
                                putText(frame, name, new Point(x, y), FONT_HERSHEY_PLAIN, 1.7, new Scalar(0, 255, 0, 2));
                            }
                          Imgcodecs.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Error!!");
                            ex.printStackTrace();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Attendance Taken...", "Notification", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Recognize User");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Book Antiqua", 1, 18)); // NOI18N
        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Book Antiqua", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("YOUR NAME");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(262, 262, 262))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(jButton1)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    public void rec(int idPerson) {
//        System.out.println("rec!!! gone.");
//        //Recognizing Face From Database
//        TakeAttendance take = new TakeAttendance();
//        Lecture="Lecture" + LectureNo;
//        if(count[idPerson] == 1){
//            take.take(faculty_id, idPerson, Lecture, CourseId);
//            s.studentList(idPerson);
//        }
//        
//        SwingWorker worker = new SwingWorker() {
//            @Override
//            protected Object doInBackground() throws Exception {
//                connect.connect();
//                try {
//                    System.out.println("ID: " + String.valueOf(idPerson));
//                    String SQL = "SELECT * FROM student WHERE id = " + String.valueOf(idPerson);
//                    connect.executeSQL(SQL);
//                    while (connect.rs.next()) {
//                        label_name.setText(connect.rs.getString("first_name") + " " + connect.rs.getString("last_name"));
//                        label_office.setText(connect.rs.getString("office"));
//                        
//                        System.out.println(Lecture);
//                        
//                    }
//                } catch (SQLException e) {
//                    System.out.println("Eroor... " + e);
//                    e.printStackTrace();
//                }
//                
//                connect.disconnect();
//                return null;
//            }
//        };
//        worker.execute();
//    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    this.setLocationRelativeTo(null);
        webSource = new VideoCapture(0); // video capture from default cam
        myThread = new recognize.DaemonThread(); //create object of Dameonthread class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        faceRecognizer.read("E:\\Softwares\\NetBeans IDE\\Projects\\Github\\Face-Detection-Attendance-System\\Faces\\classifierLBPH.yml");
        JOptionPane.showMessageDialog(null, "Reading Of 'YML' File is Done.", "Message : " + "Message Box", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Finished Reading!!!");
        //faceRecognizer.setThreshold(80.0);
        t.start();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(recognize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new recognize().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
