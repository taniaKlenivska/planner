package validate;


import org.hibernate.query.Query;

import java.util.List;

public class UserInformation {

    private static UserInformation instance = new UserInformation();

    private UserInformation() {
    }

    public static UserInformation getInstance() {
        return instance;
    }

    public User getInformationByNickname(org.hibernate.Session session, String login) {
        String queryString;

        if (FormValidate.isEmailValidate(login)) {
            queryString = "From User where email = :data";
        } else {
            queryString = "From User where nickname = :data";
        }

        Query query = session.createQuery(queryString);
        query.setParameter("data", login);
        List<User> list = query.list();

        User model = list.get(0);
        return model;
    }
}
