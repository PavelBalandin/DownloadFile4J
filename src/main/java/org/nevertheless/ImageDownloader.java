package org.nevertheless;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {
    public static void load(String fileUrl, String outputFile) throws IOException {
        try {
            // Create a URL object
            URL url = new URL(fileUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Enable automatic redirect following
            connection.setInstanceFollowRedirects(true);

            // Set timeouts to prevent indefinite hanging (10 seconds timeout)
            connection.setConnectTimeout(10000); // 10 seconds timeout for connection
            connection.setReadTimeout(10000);    // 10 seconds timeout for reading data

            // Add headers to simulate a browser request
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept", "image/*");

            // Get the response code and content type
            int status = connection.getResponseCode();
            String contentType = connection.getContentType();
            System.out.println("Response Code: " + status);
            System.out.println("Content Type: " + contentType);

            // If the file is an image, proceed with download
            if (status == HttpURLConnection.HTTP_OK && contentType.startsWith("image")) {
                System.out.println("File found, starting download...");

                // Get the input stream from the connection
                InputStream inputStream = connection.getInputStream();

                // Create output stream to save the file
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

                // Buffer for reading data
                byte[] buffer = new byte[4096];
                int bytesRead;

                // Read data from input stream and write to file output stream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                // Close streams
                inputStream.close();
                fileOutputStream.close();

                // Confirm file size
                File downloadedFile = new File(outputFile);
                System.out.println("Download complete. File size: " + downloadedFile.length() + " bytes.");
            } else {
                System.out.println("Failed to download file. HTTP Status Code: " + status);
                if (status != HttpURLConnection.HTTP_OK) {
                    System.out.println("HTTP Error: " + connection.getResponseMessage());
                }
                if (!contentType.startsWith("image")) {
                    System.out.println("Incorrect file type. Expected an image but received: " + contentType);
                }
            }

            // Close the HTTP connection
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
