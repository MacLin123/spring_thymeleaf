package com.mycompany.form.handlers;

import com.mycompany.form.config.UserData;
import com.mycompany.form.model.UserForm;
import com.mycompany.form.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class provides useful methods to handle data from user form.
 */
@Component
public class UserHandler {

    private Logger logger = LoggerFactory.getLogger(UserHandler.class);

    /**
     * This method writes data from userForm to file.
     * @param userForm - data to write
     * @throws IOException
     */
    public void writeDataToFile(UserForm userForm) throws IOException {
        String userStr = getUserRowString(userForm);

        FileWriter fw = new FileWriter(UserData.FILE_DATA, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.append(userStr);
        bw.flush();
        bw.close();
    }

    /**
     * This method converts UserForm to String
     * @param userForm data to convert to String
     * @return comma separated string that represents userForm
     * @throws IOException
     */
    public String getUserRowString(UserForm userForm) throws IOException {
        StringBuilder userStrBuild = new StringBuilder();
        userStrBuild.append(userForm.getFirstName() + ",");
        userStrBuild.append(userForm.getLastName() + ",");
        userStrBuild.append(userForm.getMiddleName() + ",");
        userStrBuild.append(userForm.getAge() + ",");
        userStrBuild.append(userForm.getSalary() + ",");
        userStrBuild.append(userForm.getEmail() + ",");
        userStrBuild.append(userForm.getCompany());
        if (userForm.getFile().isEmpty()) {
            userStrBuild.append("\r\n");
        } else {
            userStrBuild.append("," + new String(userForm.getFile().getBytes(), StandardCharsets.UTF_8) + "\r\n");
        }

        return userStrBuild.toString();
    }

    /**
     * This method looks for user in the file.
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @return UserView obj with data of user.
     * @throws IOException
     */
    public UserView getUserFromFile(String firstName, String lastName) throws IOException {
        UserView userView = null;
        FileReader fr = new FileReader("data.csv");
        BufferedReader br = new BufferedReader(fr);
        String userStr = br.lines().filter(s -> {
            String[] row = s.split(",");
            return firstName.equals(row[UserData.FIRST_NAME.getValue()])
                    && lastName.equals(row[UserData.LAST_NAME.getValue()]);
        }).findAny().orElse(null);
        if (userStr != null) {
            String[] row = userStr.split(",");

            if (row.length == UserData.values().length) {
                logger.warn("edqd2");
            }
            String aboutMe = (row.length == UserData.NUM_FIELDS.getValue()) ? row[UserData.ABOUT_ME.getValue()] : null;
            userView = new UserView(row[UserData.FIRST_NAME.getValue()],
                    row[UserData.LAST_NAME.getValue()],
                    row[UserData.MIDDLE_NAME.getValue()],
                    Integer.valueOf(row[UserData.AGE.getValue()]),
                    Double.valueOf(row[UserData.SALARY.getValue()]),
                    row[UserData.EMAIL.getValue()],
                    row[UserData.COMPANY.getValue()],
                    aboutMe);
        }
        return userView;
    }

    /**
     * This method converts UserForm to UserView
     * @param userForm
     * @return UserView representation of UserForm
     * @throws IOException
     */
    public UserView getUserView(UserForm userForm) throws IOException {
        return new UserView(
                userForm.getFirstName(),
                userForm.getLastName(),
                userForm.getMiddleName(),
                userForm.getAge(),
                userForm.getSalary(),
                userForm.getEmail(),
                userForm.getCompany(),
                new String(userForm.getFile().getBytes(), StandardCharsets.UTF_8));
    }
}
