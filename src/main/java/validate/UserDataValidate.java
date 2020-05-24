package validate;

import org.hibernate.Query;
import org.hibernate.Session;

public class UserDataValidate {



    /**
     * Checking if email already exists in database
     *
     * @param email
     * @return
     */
    public static boolean isEmailExists(Session session, String email) {
        if (session != null) {
            Query query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            return query.list().isEmpty() ? false : true;
        } else return true;
    }

    public static boolean isNicknameExists(Session session, String nickname) {
        if (session != null) {
            Query query = session.createQuery("from User where nickname = :nickname");
            query.setParameter("nickname", nickname);
            return query.list().isEmpty() ? false : true;
        } else return true;
    }

    public static boolean isPasswordExists(Session session, String password){
        if (session != null) {
            Query query = session.createQuery("from User where password = :password");
            query.setParameter("password", password);
            return query.list().isEmpty() ? false : true;
        } else return true;
    }


}
