import org.example.Main;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    @Test
    public void verifyInput_WhenResourceContainsSingleAsterisk_ReturnsFalse() {
        // Given
        String jsonContent = "{\"PolicyDocument\":{\"Statement\":[{\"Resource\":\"*\"}]}}";

        // When
        boolean isValid = Main.verifyInput(jsonContent);

        //Then
        assertFalse(isValid);
    }

    @Test
    public void verifyInput_WhenResourceNotContainsSingleAsterisk_ReturnsTrue() {
        // Given
        String jsonContent = "{\"PolicyDocument\":{\"Statement\":[{\"Resource\":\"*abc\"}]}}";

        // When
        boolean isValid = Main.verifyInput(jsonContent);

        // Then
        assertTrue(isValid);
    }

    @Test
    public void verifyInput_WhenResourceFieldIsMissing_ReturnsTrue() {
        //Given
        String jsonContent = "{\"PolicyDocument\":{\"Statement\":[{}]}}";

        // When
        boolean isValid = Main.verifyInput(jsonContent);

        // Then
        assertTrue(isValid);
    }

    @Test
    public void verifyInput_WhenInvalidJson_ThrowsJSONException() {
        String jsonContent = "{\"PolicyDocument\":{}}";
        assertThrows(RuntimeException.class, () -> Main.verifyInput(jsonContent));
    }

    @Test
    public void readJsonFile__WhenInvalidFilePathFromEnv_ThrowsIOException(){
        String jsonFilePath = "C:\file.txt";
        assertThrows(IOException.class, () -> Main.readJsonFile(jsonFilePath));
    }
}
