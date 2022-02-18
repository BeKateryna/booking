package app.user.Dao;
import app.user.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.List;

public class UserDao {
    private File userDataBase;

    public UserDao() throws IOException {;
//        userDataBase = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("./userDataBase.json")).toString());
        userDataBase = new File("src/main/resources/userDataBase.json");
        userDataBase.createNewFile();
    }

    public UserModel createUser(UserModel userModel) throws IOException {
//    public UserModel createUserDB (UserModel userModel) throws IOException {
        List<UserModel> userDataBaseList = findAll();
        userDataBaseList.add(userModel);
        updateUserDataBase(userDataBaseList);
        return userModel;
    }
    public UserModel findUserByFullName(String firstname, String lastname) throws IOException {
        List<UserModel> userDataBaseList = findAll();
        return userDataBaseList.stream()
                .filter(userModel -> userModel.getFirstName().equals(firstname) && userModel.getLastName().equals(lastname))
                .findFirst()
                .orElse(null);
    }

    private void updateUserDataBase(List<UserModel> userModels) throws IOException {
        try (OutputStream fileOutputStream  = new FileOutputStream(userDataBase)) {
            ObjectMapper objectMapper  = new ObjectMapper();
            String newDataBase = objectMapper.writeValueAsString(userModels);
            OutputStreamWriter writer  = new OutputStreamWriter(fileOutputStream);
            writer.write(newDataBase);
            writer.flush();
        }
    }



    public List<UserModel> findAll() throws IOException {
        ObjectMapper objectMapper  = new ObjectMapper();
        return objectMapper.readValue(userDataBase, new TypeReference<List<UserModel>>() {});
    }

}
