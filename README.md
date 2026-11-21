# ImageFuzzer

ImageFuzzer recolors an image by applying a randomly chosen hue shift to each
pixel. It preserves the image's shapes and shading; grayscale pixels remain
gray. Results can vary between runs.

## Requirements

- Java 25 JDK
- A JPEG image named `adventure-time-1718205825928.jpg` in the project root.
  The image is not included in the repository. To use another image, change
  the input path in `src/ImageFuzzer.java`.

## Run

From the project root:

```sh
cd src
javac ImageFuzzer.java
java ImageFuzzer
```

The program writes `output.jpg` in the project root, replacing any existing
file with that name. Paths are relative to the `src` working directory.

## Test

From the project root:

```sh
javac src/ImageFuzzer.java test/ImageFuzzerTest.java
java -cp src:test ImageFuzzerTest
```

The test exits without output when it passes.
