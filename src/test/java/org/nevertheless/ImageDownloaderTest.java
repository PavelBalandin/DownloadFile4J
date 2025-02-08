package org.nevertheless;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ImageDownloaderTest {

    @Test
    void load() throws IOException {
        String path = "test.jpeg";
        String encodedUrl = "https://t3.ftcdn.net/jpg/02/36/99/22/360_F_236992283_sNOxCVQeFLd5pdqaKGh8DRGMZy7P4XKm.jpg";
        ImageDownloader.load(encodedUrl, path);
    }
}