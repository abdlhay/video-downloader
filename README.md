# Facebook Video Downloader API with Ktor

This repository contains an API built with Ktor that allows users to download Facebook videos. The API provides a simple and efficient way to retrieve video URLs and download them locally. Additionally, it provides various metadata about the video, including view count, title, post date, and audio URL.

## Features

- Fetches Facebook video URLs by providing the video URL.
- Downloads the video file from the retrieved URL.
- Retrieves metadata about the video, including:
  - View count
  - Title
  - Post date
  - Audio URL
  - Duration
  - Thumbnail
  - Likes count
- Supports video quality selection (e.g., HD, SD).

## Requirements

- Java 11 or higher
- Gradle 7.x

## Installation

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/abdlhay/video-downloader.git
   ```

2. Change into the project directory:

   ```shell
   cd video-downloader
   ```

3. Build the project using Gradle:

   ```shell
   ./gradlew build
   ```

4. Run the application:

   ```shell
   ./gradlew run
   ```

5. The API will be accessible at `http://localhost:8080`.

## Usage

To use the API, you can send HTTP requests to the following endpoints:

- `GET /download/facebook/video?u=<videoUrl>` -  Retrieves the video URL and associated metadata for the provided videoUrl.

### Example Usage

#### Fetching video URL and metadata by video ID

```shell
GET /download/facebook/video?u=<videoUrl>
```

### Output

The API response will include the following information:

[Output](src/main/kotlin/com/abmo/output.json)
```json
{
  {
  "status": "ok",
  "result": [
    {
      "title": "We're open sourcing another new AI model today that we call Massively Multilingual Speech. It can identify more than 4,000 spoken languages and will make it easier for people to connect and access information in their language.",
      "views": 48282,
      "likes": 7462,
      "publish_date": 1684770664,
      "duration_in_ms": 58154,
      "thumbnail": "https://scontent.frak3-1.fna.fbcdn.net/v/t15.5256-10/340142770_1263322771221341_7861172868479016979_n.jpg?stp=dst-jpg_p960x960&_nc_cat=1&ccb=1-7&_nc_sid=df419e&_nc_ohc=Jq_S4H-rtlAAX_P5BNa&_nc_ht=scontent.frak3-1.fna&oh=00_AfD6Y2LnyQXXGfLSaaUAoamjoM6P2WJK2MM_zlK45ACsiQ&oe=6471297E",
      "audio": "https://video.frak3-1.fna.fbcdn.net/v/t39.25447-2/348911136_897860641509145_7314686505767176801_n.mp4?_nc_cat=103&ccb=1-7&_nc_sid=9c5c06&efg=eyJ2ZW5jb2RlX3RhZyI6ImRhc2hfYXVkaW9fYWFjcF82NF9mbm9ybTE0X2ZyYWdfMl9hdWRpbyJ9&_nc_ohc=lfkp55GQAo0AX8Spzeh&_nc_ht=video.frak3-1.fna&oh=00_AfBVR18EFk14370xhnsUpVS_Z8p9c2VBiICs7VzZpb5fQQ&oe=647150EC",
      "video_urls": [
        {
          "url": "https://video.frak3-1.fna.fbcdn.net/v/t42.1790-2/348859830_1339150619965849_2476857047427774389_n.mp4?_nc_cat=109&ccb=1-7&_nc_sid=985c63&efg=eyJybHIiOjM5MiwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoic3ZlX3NkIn0%3D&_nc_ohc=H2lFrlj-9_cAX_BTd1B&rl=392&vabr=218&_nc_ht=video.frak3-1.fna&oh=00_AfA8MlAAs9CGTFyNP-HjcgZZFvrgL_k1Cxg2nAs32KepsQ&oe=64715936",
          "resolution": "SD",
          "isMute": false,
          "type": "video"
        },
        {
          "url": "https://video.frak3-1.fna.fbcdn.net/v/t39.25447-2/348848217_632310015425410_5862017784719061757_n.mp4?_nc_cat=106&vs=aa69b6b9e09ef1c0&_nc_vs=HBksFQAYJEdGa0F5eFNDZTlzbEZUOENBUDJ5TV8wZEVscFJibWRqQUFBRhUAAsgBABUAGCRHTFhDdkJUNzFmb0owWUVXQUw4ak5wSzNVNVI0YnJGcUFBQUYVAgLIAQBLB4gScHJvZ3Jlc3NpdmVfcmVjaXBlATENc3Vic2FtcGxlX2ZwcwAQdm1hZl9lbmFibGVfbnN1YgAgbWVhc3VyZV9vcmlnaW5hbF9yZXNvbHV0aW9uX3NzaW0AKGNvbXB1dGVfc3NpbV9vbmx5X2F0X29yaWdpbmFsX3Jlc29sdXRpb24AHXVzZV9sYW5jem9zX2Zvcl92cW1fdXBzY2FsaW5nABFkaXNhYmxlX3Bvc3RfcHZxcwAVACUAHIwXQAAAAAAAAAAREQAAACbY97%2BMsu6wAxUCKAJDMxgLdnRzX3ByZXZpZXccF0BND987ZFodGCBkYXNoX3Y0XzVzZWNnb3BfaHExX2ZyYWdfMl92aWRlbxIAGBh2aWRlb3MudnRzLmNhbGxiYWNrLnByb2Q4ElZJREVPX1ZJRVdfUkVRVUVTVBsKiBVvZW1fdGFyZ2V0X2VuY29kZV90YWcGb2VwX2hkE29lbV9yZXF1ZXN0X3RpbWVfbXMBMAxvZW1fY2ZnX3J1bGUHdW5tdXRlZBNvZW1fcm9pX3JlYWNoX2NvdW50CTExOTYxNzcwMhFvZW1faXNfZXhwZXJpbWVudAAMb2VtX3ZpZGVvX2lkEDI1MjAxNDkzNDgxNDY3MzESb2VtX3ZpZGVvX2Fzc2V0X2lkEDEyOTQyODU3Mjc5NjQ4MTAVb2VtX3ZpZGVvX3Jlc291cmNlX2lkDzk1MTg3NDU1NjAwMTc3MhxvZW1fc291cmNlX3ZpZGVvX2VuY29kaW5nX2lkDzE2NjA4MzMyOTc0NTYwNA52dHNfcmVxdWVzdF9pZAAlAhwAJb4BGweIAXMEOTc1NAJjZAoyMDIzLTA1LTIyA3JjYgkxMTk2MTc3MDADYXBwB1ZpZMOpb3MCY3QZQ09OVEFJTkVEX1BPU1RfQVRUQUNITUVOVBNvcmlnaW5hbF9kdXJhdGlvbl9zCTU4LjE1NDY2NwJ0cxVwcm9ncmVzc2l2ZV9lbmNvZGluZ3MA&ccb=1-7&_nc_sid=189a0e&efg=eyJ2ZW5jb2RlX3RhZyI6Im9lcF9oZCJ9&_nc_ohc=iISibIlELcsAX_cBm6X&_nc_ht=video.frak3-1.fna&oh=00_AfAz_7JmNrSepSCBgbkqviJb9x3qtjCG5t3XIrcQ0pbtdA&oe=647047B6&_nc_rid=731927284445584",
          "resolution": "HD",
          "isMute": false,
          "type": "video"
        }, 
        ....
      ]
    }
  ]
}
}
```

- `status` (string): Indicates the status of the API request. Possible values: "ok" or "error".

- `result` (array): An array of video objects containing the following information:

  - `title` (string): The title or description of the video.
  - `views` (integer): The number of views the video has received.
  - `likes` (integer): The number of likes the video has received.
  - `publish_date` (integer): The date when the video was published (in UNIX timestamp format).
  - `duration_in_ms` (integer): The duration of the video in milliseconds.
  - `thumbnail` (string): The URL of the thumbnail image associated with the video.
  - `audio` (string): The URL of the audio file associated with the video (if available).
  - `video_urls` (array): An array of video URLs with additional information for each video.
    - `url` (string): The URL of the video file.
    - `resolution` (string): The resolution of the video.
    - `isMute` (boolean): Indicates whether the video is muted or not.
    - `type` (string): The type of video.

## Contributing

Contributions are welcome! If you find any issues or want to enhance the functionality of this API, feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- This API is built using the Ktor framework (https://ktor.io).
- Special thanks to the contributors of the open-source libraries used in this project.

## Contact

For any questions or suggestions, please feel free to reach out to [dragonboy2061@example.com](mailto:dragonboy2061@example.com).

---
