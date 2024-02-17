package temp;
import org.opencv.core.Core;

public class face {
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Load Success");
    }
}
