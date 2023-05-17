package chaining;

import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

public class DataProviders {
    private static final Faker faker = new Faker();

    @DataProvider(parallel = true)
    public static Object[][] userDP() {
        Object[][] data = new Object[3][4];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = faker.name().fullName();
            data[i][1] = faker.internet().emailAddress();
            data[i][2] = faker.options().option("male", "female");
            data[i][3] = faker.options().option("active", "inactive");
        }
        return data;
    }
}
