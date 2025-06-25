package com.helpers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ultilities.Properties_File;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import static com.helpers.CaptureHelpers.projectPath;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class RecordVideo extends ScreenRecorder {

    public static ScreenRecorder screenRecorder;
    public String name;

    // Constructor
    public RecordVideo(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                       Format mouseFormat, Format audioFormat, File movieFolder, String name)
            throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    // Ghi đè hàm để đặt tên file video
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        return new File(movieFolder,
                name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    // Kiểm tra môi trường headless (CI/CD hoặc không có GUI)
    public static boolean isHeadlessEnvironment() {
        return GraphicsEnvironment.isHeadless() || System.getenv("GITHUB_ACTIONS") != null;
    }

    // Bắt đầu ghi video
    public static void startRecord(String methodName) throws Exception {
        if (isHeadlessEnvironment()) {
            System.out.println("Headless environment detected. Skipping video recording.");
            return;
        }

        try {
            Properties_File.setPropertiesFile();
            File file = new File(projectPath + Properties_File.getPropValue("exportVideoPath") + "/" + methodName + "/");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;
            Rectangle captureSize = new Rectangle(0, 0, width, height);

            GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();

            screenRecorder = new RecordVideo(gc, captureSize,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24,
                            FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, methodName);

            screenRecorder.start();
            System.out.println("Bắt đầu ghi video cho: " + methodName);
        } catch (Exception e) {
            System.err.println("Không thể bắt đầu ghi hình: " + e.getMessage());
        }
    }

    // Dừng ghi video
    public static void stopRecord() throws Exception {
        try {
            if (screenRecorder != null) {
                screenRecorder.stop();
                System.out.println("Dừng ghi video.");
                screenRecorder = null;
            } else {
                System.out.println("screenRecorder is null, không thể dừng ghi hình.");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi dừng ghi hình: " + e.getMessage());
        }
    }
}
