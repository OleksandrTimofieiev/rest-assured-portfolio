package api.tests;

import api.utils.Specifications;
import api.config.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReqresTest {
    private final static String URL = "https://reqres.in";

    //We are getting a list of users and checking if each user has an avatar and a correct email domain
    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        List<UserData> users = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach((x) -> assertTrue(x.getAvatar().contains(x.getId().toString())));

        assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x -> x.getId().toString()).toList();

        for (int i=0;i<avatars.size();i+=1) {
            assertTrue(avatars.get(i).contains(ids.get(i)));
            System.out.println("getting an avatar");
        }
    }

    //A positive registration test
    @Test
    @Tag("registration")
    public void successRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");

        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assertions.assertNotNull(successReg.getId());
        Assertions.assertNotNull(successReg.getToken());

        Assertions.assertEquals(id, successReg.getId());
        Assertions.assertEquals(token, successReg.getToken());
    }

    //A negative registration test
    @Test
    @Tag("registration")
    public void unSuccessRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecFailed400());

        Register user = new Register("sydney@fife", "");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);

        Assertions.assertNotNull("Missing password", unSuccessReg.getError());
    }

    //We are checking if years are sorted in the ascending order
    @Test
    public void sortedYearsTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());

        List<ColorsData> colors = given()
                .when()
                .get("/api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colors.stream().map(ColorsData::getYear).sorted().collect(Collectors.toList());
        Stream<Integer> st1 = years.stream().sorted();
        List<Integer> years2 = st1.collect(Collectors.toList());
        Assertions.assertEquals(years, years2);
    }

    //We are deleting a user
    @Test
    public void deleteUsers(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }

    //We are checking the actual time when a user was updated and comparing the returned time by the server with our current time
    @Test
    public void timeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());
        UserTime user = new UserTime("morpheus", "zion resident");

        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String regex = "(.{5})$";
        String regexTwo = "(.{8})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regexTwo, "");
        Assertions.assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex, ""));
    }
}
